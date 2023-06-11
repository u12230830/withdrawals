package za.co.momentum.service;

import za.co.momentum.exception.InvestorInfoResponse;

import java.math.BigDecimal;

public interface WithdrawalEngineService {
    /**
     * This operation specifies the action of withdrawing funds from the customer's account.
     *
     * @param accountNo Refers to the account number linked to the customer's product
     * @param amount Specifies the amount to be withdrawn from the specified account number
     * @return
     */
    Boolean withdraw(Integer accountNo, BigDecimal amount);

    /**
     * This retrieves all the investor information stored for a specific customer
     *
     * @param email
     * @return
     */
    InvestorInfoResponse getInvestorInfo(String email);
}
