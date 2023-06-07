package za.co.momentum.exception;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    private String lineOne;
    private String streetName;
    private String town;
    private String province;
    private String postalCode;
    private String country;
}
