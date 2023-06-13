package za.co.momentum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.random.RandomGenerator;

@Entity
@Table(name = "WITHDRAWAL_STATUS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WithdrawalStatus {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "withdrawal_status_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "NAME")
    private Integer status;

    @Column(name = "TRX_ID")
    private Long withdrawalTransactionId;

    @Column(name = "TRX_TIME")
    private Timestamp transactionTime;
}
