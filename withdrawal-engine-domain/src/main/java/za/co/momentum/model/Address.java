package za.co.momentum.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Address {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "address_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "LINE_ONE")
    private String lineOne;

    @Column(name = "STREET_NAME")
    private String streetName;

    @Column(name = "TOWN")
    private String town;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "COUNTRY")
    private String country;
}
