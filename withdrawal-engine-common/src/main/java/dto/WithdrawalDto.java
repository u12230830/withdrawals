package dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WithdrawalDto {
    private BigDecimal amount;
    private Date withdrawalDate;
    private ProductDto product;
    private Integer status;
}
