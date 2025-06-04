package com.kabirit.accounts_service.service;


import com.kabirit.accounts_service.dto.CustomerDto;
import com.kabirit.accounts_service.exceptions.Response;

public interface IAccountsService {
    Response createAccount(CustomerDto customerDto);

    Response fetchAccount(String mobileNumber);

    Response updateAccount(CustomerDto customerDto);

    Response deleteAccount(String mobileNumber);
}
