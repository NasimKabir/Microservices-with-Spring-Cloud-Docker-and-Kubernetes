package com.kabirit.accounts_service.service.impl;

import com.kabirit.accounts_service.dto.AccountsDto;
import com.kabirit.accounts_service.dto.CustomerDto;
import com.kabirit.accounts_service.entity.Accounts;
import com.kabirit.accounts_service.entity.Customer;
import com.kabirit.accounts_service.enums.AccountsType;
import com.kabirit.accounts_service.exceptions.CustomResponseStatusException;
import com.kabirit.accounts_service.exceptions.Response;
import com.kabirit.accounts_service.exceptions.ResponseBuilder;
import com.kabirit.accounts_service.mapper.AccountsMapper;
import com.kabirit.accounts_service.mapper.CustomerMapper;
import com.kabirit.accounts_service.repository.AccountsRepository;
import com.kabirit.accounts_service.repository.CustomerRepository;
import com.kabirit.accounts_service.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public Response createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomResponseStatusException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Booking is successfully created");

    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsType.SAVINGS.getType());
        newAccount.setBranchAddress("123 Main Street, New York");
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public Response fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomResponseStatusException("MobileNumber " + mobileNumber + " not found")
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new CustomResponseStatusException("CustomerId " + customer.getCustomerId() + " not found")
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Retrieve account details", customerDto);
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public Response updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new CustomResponseStatusException("AccountNumber " + accountsDto.getAccountNumber() + " not found")
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new CustomResponseStatusException("CustomerID " + customerId + " not found")
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        if (isUpdated) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Account updated successfully", isUpdated);
        } else {
            return ResponseBuilder.getFailureResponse(HttpStatus.EXPECTATION_FAILED, "Account update failed");
        }
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public Response deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomResponseStatusException("MobileNumber " + mobileNumber + " not found")
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Account deleted successfully");
    }


}
