package com.kabirit.loans_service.service;


import com.kabirit.loans_service.dto.LoansDto;
import com.kabirit.loans_service.exceptions.Response;

public interface ILoansService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    Response createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Loan Details based on a given mobileNumber
     */
    Response fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    Response updateLoan(LoansDto loansDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    Response deleteLoan(String mobileNumber);

}
