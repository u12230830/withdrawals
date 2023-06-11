package za.co.momentum.controller;

import dto.InvestorInfoResponse;
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
import za.co.momentum.service.WithdrawalEngineServiceImpl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void getInvestorInfo() throws Exception {
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
}