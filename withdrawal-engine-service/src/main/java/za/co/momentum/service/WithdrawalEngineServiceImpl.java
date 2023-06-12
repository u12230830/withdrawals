package za.co.momentum.service;

import dto.InvestorInfoResponse;
import dto.ProductDto;
import dto.ProductTypeEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.momentum.exception.ValidationException;
import za.co.momentum.model.Customer;
import za.co.momentum.model.Product;
import za.co.momentum.repo.CustomerRepository;
import za.co.momentum.repo.ProductRepository;
import za.co.momentum.util.DtoMapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

@Service
public class WithdrawalEngineServiceImpl implements WithdrawalEngineService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Transactional
    @Override
    public Boolean withdraw(String accountNo, BigDecimal amount) {
        Product product = productRepository.findByAccountNumber(accountNo);
        if (product == null) {
            throw new EntityNotFoundException("No product found for the given account number");
        }

        try {
            validateWithdrawalRules(amount, product);
        } catch (ValidationException e) {
            // Todo: log exception here
            // Todo: Add an exception handler
            throw e;
        }

        Long trx = RandomGenerator.getDefault().nextLong();

        return null;
    }

    @Override
    public InvestorInfoResponse getInvestorInfo(String email) throws EntityNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null) {
            throw new EntityNotFoundException("No customer found for the given email address");
        }

        return dtoMapper.mapCustomerToInvestorInfoResponse(customer);
    }

    @Override
    public List<ProductDto> listInvestorProducts(String email) throws EntityNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null) {
            throw new EntityNotFoundException("No customer found for the given email address");
        }

        List<Product> products = productRepository.findAllByCustomer(customer);
        List<ProductDto> productDtos = new ArrayList<>();
        if (products != null || !products.isEmpty()) {
            for (Product p : products) {
                productDtos.add(dtoMapper.mapProductToProductDto(p));
            }
        }

        return productDtos;
    }


    private void validateWithdrawalRules(BigDecimal amount, Product product) {
        if (product.getId().equals(ProductTypeEnum.RETIREMENT.getProductTypeId())
                && calculateAge(product.getCustomer().getDob()) <= 65) {
            throw new ValidationException("Customer age constraint violation");
        }

        if (amount.compareTo(product.getBalance()) > 0){
            throw new ValidationException("Amount requested exceeds the available balance");
        }

        if (amount.compareTo(product.getBalance().multiply(BigDecimal.valueOf(0.9))) > 0){
            throw new ValidationException("Customer cannot withdraw more than 90% of their investment");
        }
    }

    private int calculateAge(Date dob) {
        return LocalDate.now().getYear() - dob.toLocalDate().getYear();
    }
}
