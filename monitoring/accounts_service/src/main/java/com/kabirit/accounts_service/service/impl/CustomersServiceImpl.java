package com.kabirit.accounts_service.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabirit.accounts_service.dto.AccountsDto;
import com.kabirit.accounts_service.dto.CardsDto;
import com.kabirit.accounts_service.dto.CustomerDetailsDto;
import com.kabirit.accounts_service.dto.LoansDto;
import com.kabirit.accounts_service.entity.Accounts;
import com.kabirit.accounts_service.entity.Customer;
import com.kabirit.accounts_service.exceptions.CustomResponseStatusException;
import com.kabirit.accounts_service.exceptions.Response;
import com.kabirit.accounts_service.mapper.AccountsMapper;
import com.kabirit.accounts_service.mapper.CustomerMapper;
import com.kabirit.accounts_service.repository.AccountsRepository;
import com.kabirit.accounts_service.repository.CustomerRepository;
import com.kabirit.accounts_service.service.ICustomersService;
import com.kabirit.accounts_service.service.clients.CardsFeignClients;
import com.kabirit.accounts_service.service.clients.LoansFeignClients;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClients cardsFeignClient;
    private LoansFeignClients loansFeignClient;
    private ObjectMapper mapper;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomResponseStatusException("Customer mobileNumber" + mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new CustomResponseStatusException("Account customerId" + customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        Response loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);

        LoansDto loansDto = mapper.convertValue(loansDtoResponseEntity.getContent(), LoansDto.class);
        if (loansDto != null) {
            customerDetailsDto.setLoansDto(loansDto);
        }


        Response cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        CardsDto cardsDto = mapper.convertValue(cardsDtoResponseEntity.getContent(), CardsDto.class);
        if (cardsDto != null) {
            customerDetailsDto.setCardsDto(cardsDto);
        }

        return customerDetailsDto;

    }
}
