package dto;

public enum WithdrawalEventStatusEnum {
    STARTED,
    EXECUTING,
    DONE;

    public WithdrawalEventStatusEnum next(){
        return WithdrawalEventStatusEnum.values()[this.ordinal() + 1];
    }
}
