package za.co.momentum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.momentum.repo.ProductRepository;

import java.math.BigDecimal;

@Service
public class WithdrawalEngineServiceImpl implements WithdrawalEngineService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Boolean withdraw(Long accountNumber, BigDecimal amount) {
        return null;
    }
}
