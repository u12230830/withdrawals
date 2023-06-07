package za.co.momentum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvestorInfoResponse {
    private String fullName;
    private Date dateOfBirth;
    private String email;
    private Address address;
}
