package com.kabirit.accounts_service.service.clients;

import com.kabirit.accounts_service.exceptions.Response;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author nasimkabir
 * @date 16/7/25
 */
@FeignClient(name = "LOANS-SERVICE",fallback = LoansFallback.class)
public interface LoansFeignClients {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    Response fetchLoanDetails(@RequestParam
                              @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber);
}
