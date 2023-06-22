package za.co.momentum.controller;

import dto.InvestorInfoResponse;
import dto.ProductDto;
import dto.WithdrawalRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import util.WithdrawalEngineMapperUtils;
import za.co.momentum.service.impl.WithdrawalEngineServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@MockitoSettings
class InvestmentsControllerTest {
    @Mock
    WithdrawalEngineServiceImpl withdrawalEngineService;
    @InjectMocks
    private InvestmentsController investmentsController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(investmentsController).build();
    }

    @Test
    void testGetInvestorInfo() throws Exception {
        InvestorInfoResponse actual = InvestorInfoResponse
                .builder()
                .email("jozi@example.com")
                .build();

        when(withdrawalEngineService.getInvestorInfo(anyString())).thenReturn(actual);

        mockMvc.perform(get("/investments/josi@example.com"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.email").value("jozi@example.com"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(withdrawalEngineService).getInvestorInfo(anyString());
    }

    @Test
    void testListInvestorProducts() throws Exception {
        List<ProductDto> productDtos = Arrays.asList(new ProductDto(), new ProductDto());

        when(withdrawalEngineService.listInvestorProducts(anyString())).thenReturn(productDtos);

        mockMvc.perform(get("/investments/josi@example.com/products"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(withdrawalEngineService).listInvestorProducts(anyString());
    }

    @Test
    void testWithdraw() throws Exception {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest("234235", BigDecimal.ONE);

        when(withdrawalEngineService.withdraw(anyString(), any(BigDecimal.class))).thenReturn(true);
        mockMvc.perform(post("/investments/withdraw")
                        .content(WithdrawalEngineMapperUtils.objectToJsonString(withdrawalRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(withdrawalEngineService).withdraw(anyString(), any(BigDecimal.class));
    }
}