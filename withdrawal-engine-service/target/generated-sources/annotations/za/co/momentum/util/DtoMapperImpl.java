package za.co.momentum.util;

import org.springframework.stereotype.Component;
import za.co.momentum.exception.AddressDto;
import za.co.momentum.exception.InvestorInfoResponse;
import za.co.momentum.model.Address;
import za.co.momentum.model.Customer;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-11T12:51:16+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
*/
@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public InvestorInfoResponse mapCustomerToInvestorInfoResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        InvestorInfoResponse investorInfoResponse = new InvestorInfoResponse();

        investorInfoResponse.setFullName( customer.getName() );
        investorInfoResponse.setEmail( customer.getEmail() );
        investorInfoResponse.setDateOfBirth( customer.getDob() );
        investorInfoResponse.setPhoneNo( customer.getPhoneNo() );
        investorInfoResponse.setAddressDto( mapAddressToAddressDto( customer.getAddress() ) );

        return investorInfoResponse;
    }

    @Override
    public AddressDto mapAddressToAddressDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDto addressDto = new AddressDto();

        addressDto.setLineOne( address.getLineOne() );
        addressDto.setStreetName( address.getStreetName() );
        addressDto.setTown( address.getTown() );
        addressDto.setProvince( address.getProvince() );
        addressDto.setPostalCode( address.getPostalCode() );
        addressDto.setCountry( address.getCountry() );

        return addressDto;
    }
}
