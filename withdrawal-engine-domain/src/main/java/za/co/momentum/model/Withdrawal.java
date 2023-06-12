package za.co.momentum.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "WITHDRAWAL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "WITHDRAWAL_DATE")
    private Date withdrawalDate;

    @PrimaryKeyJoinColumn(name = "PRODUCT_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "TRX_ID")
    private Long transactionId;
}
