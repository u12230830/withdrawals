package za.co.momentum.service;

import dto.InvestorInfoResponse;
import dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface WithdrawalEngineService {
    /**
     * This operation specifies the action of withdrawing funds from the customer's account.
     *
     * @param accountNo Refers to the account number linked to the customer's product
     * @param amount    Specifies the amount to be withdrawn from the specified account number
     * @return True if successful and false if not
     */
    Boolean withdraw(String accountNo, BigDecimal amount) throws InterruptedException;

    /**
     * This retrieves all the investor information stored for a specific customer
     *
     * @param email
     * @return
     */
    InvestorInfoResponse getInvestorInfo(String email) throws InterruptedException;

    /**
     * Retrieves a list of all the products belonging to a given investor
     *
     * @param email
     * @return
     */
    List<ProductDto> listInvestorProducts(String email);
}
