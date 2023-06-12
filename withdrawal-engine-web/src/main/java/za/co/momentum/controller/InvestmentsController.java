package za.co.momentum.controller;

import dto.InvestorInfoResponse;
import dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.co.momentum.service.WithdrawalEngineService;

import java.util.List;

@Controller
@RequestMapping("/investments")
public class InvestmentsController {

    @Autowired
    WithdrawalEngineService withdrawalEngineService;

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<InvestorInfoResponse> getInvestorInfo(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(withdrawalEngineService.getInvestorInfo(email));
    }

    @RequestMapping(value = "/{email}/products", method = RequestMethod.GET)
    public ResponseEntity<List<ProductDto>> listInvestorProducts(@PathVariable(name = "email") String email) {
        return ResponseEntity.ok(withdrawalEngineService.listInvestorProducts(email));
    }
}
