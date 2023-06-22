package za.co.momentum.controller;

import dto.InvestorInfoResponse;
import dto.ProductDto;
import dto.WithdrawalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.momentum.service.WithdrawalEngineService;

import java.util.List;

@Controller
@RequestMapping("/investments")
public class InvestmentsController {

    @Autowired
    WithdrawalEngineService withdrawalEngineService;

    @GetMapping(value = "/{email}")
    public ResponseEntity<InvestorInfoResponse> getInvestorInfo(@PathVariable(name = "email") String email) throws InterruptedException {
        return ResponseEntity.ok(withdrawalEngineService.getInvestorInfo(email));
    }

    @GetMapping(value = "/{email}/products")
    public ResponseEntity<List<ProductDto>> listInvestorProducts(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(withdrawalEngineService.listInvestorProducts(email));
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<Boolean> withdraw(@RequestBody WithdrawalRequest withdrawalRequest) throws InterruptedException {
        return ResponseEntity.ok(withdrawalEngineService
                .withdraw(withdrawalRequest.getAccountNo(), withdrawalRequest.getAmount()));
    }
}
