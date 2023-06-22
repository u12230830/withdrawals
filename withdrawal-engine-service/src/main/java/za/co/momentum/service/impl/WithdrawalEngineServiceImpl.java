package za.co.momentum.service.impl;

import dto.InvestorInfoResponse;
import dto.ProductDto;
import dto.ProductTypeEnum;
import dto.WithdrawalEventStatusEnum;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.momentum.exception.ValidationException;
import za.co.momentum.messaging.publisher.Publisher;
import za.co.momentum.model.Customer;
import za.co.momentum.model.Product;
import za.co.momentum.model.Withdrawal;
import za.co.momentum.model.WithdrawalStatus;
import za.co.momentum.repo.CustomerRepository;
import za.co.momentum.repo.ProductRepository;
import za.co.momentum.repo.WithdrawalRepository;
import za.co.momentum.repo.WithdrawalStatusRepository;
import za.co.momentum.service.WithdrawalEngineService;
import za.co.momentum.util.DtoMapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.random.RandomGenerator;

@Service
public class WithdrawalEngineServiceImpl implements WithdrawalEngineService {
    Logger logger = LoggerFactory.getLogger(WithdrawalEngineServiceImpl.class);

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final WithdrawalStatusRepository withdrawalStatusRepository;
    private final DtoMapper dtoMapper;
    private final Publisher publisher;

    public WithdrawalEngineServiceImpl(ProductRepository productRepository, CustomerRepository customerRepository,
                                       WithdrawalRepository withdrawalRepository, WithdrawalStatusRepository withdrawalStatusRepository, DtoMapper dtoMapper, Publisher publisher) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.withdrawalStatusRepository = withdrawalStatusRepository;
        this.dtoMapper = dtoMapper;
        this.publisher = publisher;
    }

    @Transactional
    @Override
    public Boolean withdraw(String accountNo, BigDecimal amount) throws InterruptedException {
        Product product;
        try {
            product = productRepository.findByAccountNumber(accountNo).get();
        } catch (NoSuchElementException e) {
            logger.error("No product found for the given account number", e);
            throw e;
        }

        try {
            validateWithdrawalRules(amount, product);
        } catch (ValidationException e) {
            logger.error("Validations failed", e);
            throw e;
        }

        Long trxId = RandomGenerator.getDefault().nextLong(0, Long.MAX_VALUE);
        sendToQueue(WithdrawalEventStatusEnum.STARTED, trxId);

        Withdrawal withdrawal = createNewWithdrawal(amount, product, trxId);

        sendToQueue(WithdrawalEventStatusEnum.EXECUTING, trxId);
        product.setBalance(product.getBalance().subtract(amount));

        productRepository.save(product);
        withdrawalRepository.save(withdrawal);

        sendToQueue(WithdrawalEventStatusEnum.DONE, trxId);
        return true;
    }

    @Override
    public InvestorInfoResponse getInvestorInfo(String email) throws EntityNotFoundException {
        try {
            Customer customer = customerRepository.findCustomerByEmail(email).get();
            return dtoMapper.mapCustomerToInvestorInfoResponse(customer);
        } catch (NoSuchElementException e) {
            logger.error("No customer found for the given email address", e);
            throw e;
        }
    }

    @Override
    public List<ProductDto> listInvestorProducts(String email) throws EntityNotFoundException {
        Customer customer;
        try {
            customer = customerRepository.findCustomerByEmail(email).get();
        } catch (NoSuchElementException e) {
            logger.error("No customer found for the given email address", e);
            throw e;
        }

        List<Product> products;
        List<ProductDto> productDtos = new ArrayList<>();

        try {
            products = productRepository.findAllByCustomer(customer).get();
            if (products != null || !products.isEmpty()) {
                for (Product p : products) {
                    productDtos.add(dtoMapper.mapProductToProductDto(p));
                }
            }
        } catch (NoSuchElementException e) {
            logger.error("No products found for customer", e);
            throw e;
        }

        return productDtos;
    }

    @Override
    public void createWithdrawalStatus(Long trxId, WithdrawalEventStatusEnum statusEnum) {
        WithdrawalStatus newStatus = new WithdrawalStatus();
        newStatus.setTransactionTime(Timestamp.valueOf(LocalDateTime.now()));
        newStatus.setWithdrawalTransactionId(trxId);
        newStatus.setStatus(statusEnum.ordinal());
        withdrawalStatusRepository.save(newStatus);
    }

    private void validateWithdrawalRules(BigDecimal amount, Product product) {
        if (product.getId().equals(ProductTypeEnum.RETIREMENT.getProductTypeId())
                && calculateAge(product.getCustomer().getDob()) <= 65) {
            throw new ValidationException("Customer age constraint violation");
        }

        if (amount.compareTo(product.getBalance()) > 0) {
            throw new ValidationException("Amount requested exceeds the available balance");
        }

        if (amount.compareTo(product.getBalance().multiply(BigDecimal.valueOf(0.9))) > 0) {
            throw new ValidationException("Customer cannot withdraw more than 90% of their investment");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Amount requested cannot be negative");
        }
    }

    private void sendToQueue(WithdrawalEventStatusEnum statusEnum, Long trxId) throws InterruptedException {
        try {
            publisher.convertAndSend(statusEnum, trxId);
        } catch (InterruptedException e) {
            logger.error("Failed to publish message to the queue", e);
            throw e;
        }
    }

    private Withdrawal createNewWithdrawal(BigDecimal amount, Product product, Long trx) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setProduct(product);
        withdrawal.setTransactionId(trx);
        withdrawal.setWithdrawalDate(Timestamp.valueOf(LocalDateTime.now()));
        return withdrawal;
    }

    private int calculateAge(Date dob) {
        return LocalDate.now().getYear() - dob.toLocalDate().getYear();
    }
}
