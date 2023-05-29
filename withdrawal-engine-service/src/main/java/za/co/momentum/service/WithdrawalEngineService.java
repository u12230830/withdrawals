package za.co.momentum.service;

import java.math.BigDecimal;

public interface WithdrawalEngineService {
    /**
     * This operation specifies the action of withdrawing funds from the customer's account.
     *
     * @param accountNumber Refers to the account number linked to the customer's product
     * @param amount Specifies the amount to be withdrawn from the specified account number
     * @return
     */
    Boolean withdraw(Long accountNumber, BigDecimal amount);
}
