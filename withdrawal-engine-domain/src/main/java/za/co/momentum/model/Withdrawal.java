package za.co.momentum.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "WITHDRAWAL")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Withdrawal {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "withdrawal_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "WITHDRAWAL_DATE")
    private Timestamp withdrawalDate;

    @PrimaryKeyJoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "TRX_ID")
    private Long transactionId;
}
