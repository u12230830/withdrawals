package za.co.momentum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.random.RandomGenerator;

@Entity
@Table(name = "WITHDRAWAL_STATUS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WithdrawalStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NAME")
    private Integer status;

    @Column(name = "description")
    private String description;

    @Column(name = "TRX_ID")
    private Long withdrawalTransactionId;
}
