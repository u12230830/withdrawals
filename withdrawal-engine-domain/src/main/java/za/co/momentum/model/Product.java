package za.co.momentum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Loader;
import org.springframework.data.repository.cdi.Eager;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "product_seq",
            allocationSize = 1
    )
    private Integer id;

    @JoinColumn(name = "PRODUCT_TYPE_ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductType productType;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @PrimaryKeyJoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Customer customer;
}
