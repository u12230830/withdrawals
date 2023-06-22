package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WithdrawalStatusQueueMessage {
    private WithdrawalEventStatusEnum withdrawalEventStatusEnum;
    private Long trxId;
}
