package za.co.momentum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import dto.InvestorInfoResponse;
import za.co.momentum.model.Customer;
import za.co.momentum.repo.CustomerRepository;
import za.co.momentum.repo.ProductRepository;
import util.WithdrawalEngineServiceTestUtils;
import za.co.momentum.util.DtoMapper;
import za.co.momentum.util.DtoMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@MockitoSettings
class WithdrawalEngineServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Spy
    private DtoMapper dtoMapper;

    @InjectMocks
    WithdrawalEngineServiceImpl withdrawalEngineService;

    @BeforeEach
    void init() {
        dtoMapper = new DtoMapperImpl();
        openMocks(this);
    }

    @Test
    void getInvestorInfo() throws Exception {
        Customer customer = WithdrawalEngineServiceTestUtils.jsonFileToObject("Customer.json", Customer.class);
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);

        InvestorInfoResponse actual = withdrawalEngineService.getInvestorInfo("any@example.com");

        verify(customerRepository).findCustomerByEmail(anyString());
        verify(dtoMapper).mapCustomerToInvestorInfoResponse(any(Customer.class));
        assertEquals("josi@example.com", actual.getEmail());
        assertEquals("Joseph", actual.getFullName());
        assertEquals("+27 823 2323", actual.getPhoneNo());
        assertEquals("1993-12-31", actual.getDateOfBirth().toString());

        assertNull(actual.getAddressDto().getLineOne());
        assertEquals("123 Rd Drive", actual.getAddressDto().getStreetName());
        assertEquals("Jozi", actual.getAddressDto().getTown());
        assertEquals("Gauteng", actual.getAddressDto().getProvince());
        assertEquals("1242", actual.getAddressDto().getPostalCode());
        assertEquals("South Africa", actual.getAddressDto().getCountry());
    }
}