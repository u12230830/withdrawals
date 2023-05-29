package za.co.momentum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "PRODUCT_TYPE_ID")
    @OneToOne
    private ProductType productType;

    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;

    @Column(name = "BALANCE")
    private BigDecimal balance;
}
