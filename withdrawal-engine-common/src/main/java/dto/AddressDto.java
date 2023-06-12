package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddressDto {
    private String lineOne;
    private String streetName;
    private String town;
    private String province;
    private String postalCode;
    private String country;
}
