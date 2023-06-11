package za.co.momentum.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.momentum.exception.InvestorInfoResponse;
import za.co.momentum.model.Customer;
import za.co.momentum.repo.CustomerRepository;
import za.co.momentum.repo.ProductRepository;
import za.co.momentum.util.DtoMapper;

import java.math.BigDecimal;

@Service
public class WithdrawalEngineServiceImpl implements WithdrawalEngineService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public Boolean withdraw(Integer accountNo, BigDecimal amount) {
        return null;
    }

    @Override
    public InvestorInfoResponse getInvestorInfo(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null) {
            throw new EntityNotFoundException("No customer found for the given email address");
        }

        return dtoMapper.mapCustomerToInvestorInfoResponse(customer);
    }
}
