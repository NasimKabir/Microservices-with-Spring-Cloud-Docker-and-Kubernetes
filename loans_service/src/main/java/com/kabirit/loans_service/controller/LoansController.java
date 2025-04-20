package com.kabirit.loans_service.controller;

import com.kabirit.loans_service.dto.LoansDto;
import com.kabirit.loans_service.exceptions.Response;
import com.kabirit.loans_service.service.ILoansService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eazy Bytes
 */

@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController {

    private ILoansService iLoansService;

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

}
