package za.co.momentum.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import za.co.momentum.exception.AddressDto;
import za.co.momentum.exception.InvestorInfoResponse;
import za.co.momentum.model.Address;
import za.co.momentum.model.Customer;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "dateOfBirth", source = "dob")
    @Mapping(target = "phoneNo", source = "phoneNo")
    @Mapping(target = "addressDto", source = "address", qualifiedByName = "mapAddressToAddressDto")
    InvestorInfoResponse mapCustomerToInvestorInfoResponse(Customer customer);

    @Mapping(target = "lineOne", source = "lineOne")
    @Mapping(target = "streetName", source = "streetName")
    @Mapping(target = "town", source = "town")
    @Mapping(target = "province", source = "province")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    @Named("mapAddressToAddressDto")
    AddressDto mapAddressToAddressDto(Address address);
}
