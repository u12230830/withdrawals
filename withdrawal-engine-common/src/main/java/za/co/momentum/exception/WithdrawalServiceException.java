package za.co.momentum.exception;

public class WithdrawalServiceException extends RuntimeException {
    public WithdrawalServiceException(String message, Throwable e) {
        super(message, e);
    }
}
