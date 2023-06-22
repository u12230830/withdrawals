package za.co.momentum.service.impl;

import dto.InvestorInfoResponse;
import dto.ProductDto;
import dto.WithdrawalEventStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import util.WithdrawalEngineMapperUtils;
import za.co.momentum.exception.ValidationException;
import za.co.momentum.messaging.publisher.Publisher;
import za.co.momentum.model.Customer;
import za.co.momentum.model.Product;
import za.co.momentum.model.ProductType;
import za.co.momentum.model.Withdrawal;
import za.co.momentum.repo.CustomerRepository;
import za.co.momentum.repo.ProductRepository;
import za.co.momentum.repo.WithdrawalRepository;
import za.co.momentum.service.impl.WithdrawalEngineServiceImpl;
import za.co.momentum.util.DtoMapper;
import za.co.momentum.util.DtoMapperImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class WithdrawalEngineServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private WithdrawalRepository withdrawalRepository;

    @Mock
    private Publisher publisher;

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
    void testGetInvestorInfo() throws Exception {
        Customer customer = WithdrawalEngineMapperUtils.jsonFileToObject("Customer.json", Customer.class);
        when(customerRepository.findCustomerByEmail(any())).thenReturn(Optional.of(customer));

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

    @Test
    void testListInvestorProducts() throws Exception {
        Customer customer = WithdrawalEngineMapperUtils.jsonFileToObject("Customer.json", Customer.class);
        List<Product> products = WithdrawalEngineMapperUtils.jsonFileToObjectList("Products.json", Product.class);

        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.of(customer));
        when(productRepository.findAllByCustomer(any(Customer.class))).thenReturn(Optional.of(products));

        List<ProductDto> productDtos = withdrawalEngineService.listInvestorProducts("23414");
        assertNotNull(productDtos);
        assertEquals(2, productDtos.size());
        verify(customerRepository).findCustomerByEmail(anyString());
        verify(productRepository).findAllByCustomer(any(Customer.class));
        verify(dtoMapper, times(2)).mapProductToProductDto(any(Product.class));
    }

    @Test
    void testWithdraw_ageConstraint() throws Exception {
        Product product = WithdrawalEngineMapperUtils.jsonFileToObject("Product.json", Product.class);

        try {
            when(productRepository.findByAccountNumber(eq("2342341"))).thenReturn(Optional.of(product));
            withdrawalEngineService.withdraw("2342341", BigDecimal.valueOf(15000));
            fail();
        } catch (ValidationException e) {
            assertEquals("Customer age constraint violation", e.getMessage());
            verifyNoInteractions(withdrawalRepository);
            verify(productRepository).findByAccountNumber(anyString());
        }
    }

    @Test
    void testWithdraw_thresholdViolation() throws Exception {
        Product product = WithdrawalEngineMapperUtils.jsonFileToObject("Product.json", Product.class);
        product.getCustomer().setDob(Date.valueOf(LocalDate.of(1950, 5, 2)));
        when(productRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(product));

        try {
            withdrawalEngineService.withdraw("2342341", BigDecimal.valueOf(490000));
            fail();
        } catch (ValidationException e) {
            assertEquals("Customer cannot withdraw more than 90% of their investment", e.getMessage());
            verifyNoInteractions(withdrawalRepository);
            verify(productRepository).findByAccountNumber(anyString());
        }
    }

    @Test
    void testWithdraw_exceedingLimit() throws Exception {
        Product product = WithdrawalEngineMapperUtils.jsonFileToObject("Product.json", Product.class);
        product.getCustomer().setDob(Date.valueOf(LocalDate.of(1950, 5, 2)));
        when(productRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(product));

        try {
            withdrawalEngineService.withdraw("2342341", BigDecimal.valueOf(600000));
            fail();
        } catch (ValidationException e) {
            assertEquals("Amount requested exceeds the available balance", e.getMessage());
            verifyNoInteractions(withdrawalRepository);
            verify(productRepository).findByAccountNumber(anyString());
        }
    }

    @Test
    void testWithdraw() throws Exception {
        Product product = WithdrawalEngineMapperUtils.jsonFileToObject("Product.json", Product.class);
        product.getCustomer().setDob(Date.valueOf(LocalDate.of(1950, 5, 2)));
        when(productRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(product));
        when(withdrawalRepository.save(any(Withdrawal.class))).thenReturn(null);
        doNothing().when(publisher).convertAndSend(any(WithdrawalEventStatusEnum.class), anyLong());

        try {
            withdrawalEngineService.withdraw("2342341", BigDecimal.valueOf(10000));
            verify(productRepository).findByAccountNumber(anyString());
            verify(withdrawalRepository).save(any(Withdrawal.class));
        } catch (ValidationException e) {
            fail();
        }
    }

    @Test
    void sdfasdf() throws Exception {
        Customer customer = WithdrawalEngineMapperUtils.jsonFileToObject("Customer.json", Customer.class);

        Product p = new Product();
        p.setBalance(BigDecimal.valueOf(500000));
        p.setCustomer(customer);
        p.setProductType(new ProductType(1, "RETIREMENT", "401 K"));
        p.setId(1);

        List<Product> products = Arrays.asList(p, p);

        System.out.println(WithdrawalEngineMapperUtils.objectToJsonString(products));
    }
}