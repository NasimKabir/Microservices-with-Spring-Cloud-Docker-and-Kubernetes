package com.kabirit.loans_service.service.impl;


import com.kabirit.loans_service.dto.LoansDto;
import com.kabirit.loans_service.entity.Loans;
import com.kabirit.loans_service.enums.LoansType;
import com.kabirit.loans_service.exceptions.CustomResponseStatusException;
import com.kabirit.loans_service.exceptions.Response;
import com.kabirit.loans_service.exceptions.ResponseBuilder;
import com.kabirit.loans_service.mapper.LoansMapper;
import com.kabirit.loans_service.repository.LoansRepository;
import com.kabirit.loans_service.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public Response createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new CustomResponseStatusException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Loan is successfully created");

    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansType.HOME_LOAN.getType());
        newLoan.setTotalLoan(LoansType.NEW_LOAN_LIMIT.getLoanLimit());
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansType.NEW_LOAN_LIMIT.getLoanLimit());
        return newLoan;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public Response fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomResponseStatusException("mobileNumber " + mobileNumber)
        );
        LoansDto loansDto = LoansMapper.mapToLoansDto(loans, new LoansDto());
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Retrieve account details", loansDto);

    }

    /**
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public Response updateLoan(LoansDto loansDto) {
        boolean isUpdated = false;
        if (loansDto != null) {
            Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                    () -> new CustomResponseStatusException("LoanNumber " + loansDto.getLoanNumber()));
            LoansMapper.mapToLoans(loansDto, loans);
            loansRepository.save(loans);
            isUpdated = true;
        }
        if (isUpdated) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Loan updated successfully", isUpdated);
        } else {
            return ResponseBuilder.getFailureResponse(HttpStatus.EXPECTATION_FAILED, "Loan update failed");
        }
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public Response deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomResponseStatusException("MobileNumber " + mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Loan deleted successfully");
    }


}
