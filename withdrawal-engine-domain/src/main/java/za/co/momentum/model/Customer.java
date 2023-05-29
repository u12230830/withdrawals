package za.co.momentum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "DOB")
    private Date age;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRODUCTS")
    @OneToMany
    @JoinColumn(name = "PRODUCT_ID")
    private List<Product> products;

    @Column(name = "ADDRESS_ID")
    @OneToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;
}

