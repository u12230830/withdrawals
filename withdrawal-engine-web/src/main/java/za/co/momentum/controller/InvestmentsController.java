package za.co.momentum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.momentum.service.WithdrawalEngineService;

@Controller
@RequestMapping("/investments")
public class InvestmentsController {

    @Autowired
    WithdrawalEngineService withdrawalEngineService;
}
