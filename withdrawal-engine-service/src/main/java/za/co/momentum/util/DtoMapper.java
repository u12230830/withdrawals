package za.co.momentum.util;

import dto.AddressDto;
import dto.InvestorInfoResponse;
import dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;
import org.springframework.stereotype.Component;
import za.co.momentum.model.Address;
import za.co.momentum.model.Customer;
import za.co.momentum.model.Product;
import za.co.momentum.model.ProductType;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface DtoMapper {
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "dateOfBirth", source = "dob")
    @Mapping(target = "addressDto", source = "address", qualifiedByName = "mapAddressToAddressDto")
    InvestorInfoResponse mapCustomerToInvestorInfoResponse(Customer customer);

    @Named("mapAddressToAddressDto")
    AddressDto mapAddressToAddressDto(Address address);

    @Mapping(target = "name", source = "productType.name")
    @Mapping(target = "description", source = "productType.description")
    ProductDto mapProductToProductDto(Product products);
}
