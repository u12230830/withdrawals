package za.co.momentum.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithdrawalEngineServiceImpl implements WithdrawalEngineService {

    @Override
    public Boolean withdraw(Long accountNumber, BigDecimal amount) {
        return null;
    }
}
