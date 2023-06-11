package za.co.momentum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "CUSTOMER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @PrimaryKeyJoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}

