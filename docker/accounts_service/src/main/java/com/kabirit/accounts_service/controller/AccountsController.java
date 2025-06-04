package com.kabirit.accounts_service.controller;

import com.kabirit.accounts_service.dto.LoansContactInfoDto;
import com.kabirit.accounts_service.dto.CustomerDto;
import com.kabirit.accounts_service.exceptions.Response;
import com.kabirit.accounts_service.exceptions.ResponseBuilder;
import com.kabirit.accounts_service.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eazy Bytes
 */

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountsController {

    private final IAccountsService accountsService;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final LoansContactInfoDto accountsContactInfoDto;

    @PostMapping("/create")
    public Response createAccount(@Valid @RequestBody CustomerDto customerDto) {
        return accountsService.createAccount(customerDto);
    }


    @GetMapping("/fetchCustomerDetails")
    public Response fetchAccountDetails(@RequestParam
                                        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                        String mobileNumber) {
        return accountsService.fetchAccount(mobileNumber);
    }


    @PutMapping("/update")
    public Response updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        return accountsService.updateAccount(customerDto);
    }


    @DeleteMapping("/delete")
    public Response deleteAccountDetails(@RequestParam
                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                         String mobileNumber) {
        return accountsService.deleteAccount(mobileNumber);
    }


    @GetMapping("/build-info")
    public Response getBuildInfo() {
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Build Version: " + buildVersion);
    }

    @GetMapping("/java-version")
    public Response getJavaVersion() {
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "JDK Version: " + environment.getProperty("JAVA_HOME"));
    }


    @GetMapping("/contact-info")
    public Response getContactInfo() {
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Accounts Contact Info ", accountsContactInfoDto);
    }


}
