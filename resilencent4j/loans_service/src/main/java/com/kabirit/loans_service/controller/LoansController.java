package com.kabirit.loans_service.controller;

import com.kabirit.loans_service.dto.LoansContactInfoDto;
import com.kabirit.loans_service.dto.LoansDto;
import com.kabirit.loans_service.exceptions.Response;
import com.kabirit.loans_service.exceptions.ResponseBuilder;
import com.kabirit.loans_service.service.ILoansService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eazy Bytes
 */
@Slf4j
@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoansController {

    private final ILoansService iLoansService;

    @Value("${build.version:2.0}")
    private String buildVersion;

    private final Environment environment;

    private final LoansContactInfoDto loansContactInfoDto;

    public LoansController(ILoansService iLoansService, Environment environment, LoansContactInfoDto loansContactInfoDto) {
        this.iLoansService = iLoansService;
        this.environment = environment;
        this.loansContactInfoDto = loansContactInfoDto;
    }


    @PostMapping("/create")
    public Response createLoan(@RequestParam
                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                               String mobileNumber) {
        return iLoansService.createLoan(mobileNumber);
    }


    @GetMapping("/fetch")
    public Response fetchLoanDetails(@RequestParam
                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                     String mobileNumber) {
        return iLoansService.fetchLoan(mobileNumber);
    }

    @PutMapping("/update")
    public Response updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        return iLoansService.updateLoan(loansDto);
    }

    @DeleteMapping("/delete")
    public Response deleteLoanDetails(@RequestParam
                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                      String mobileNumber) {
        return iLoansService.deleteLoan(mobileNumber);
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
        log.debug("Invoked loans contact info REST API");
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Loans Contact Info ", loansContactInfoDto);
    }

}
