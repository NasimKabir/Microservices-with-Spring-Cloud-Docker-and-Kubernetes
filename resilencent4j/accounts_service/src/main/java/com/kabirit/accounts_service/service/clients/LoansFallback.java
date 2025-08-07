package com.kabirit.accounts_service.service.clients;

import com.kabirit.accounts_service.exceptions.Response;
import org.springframework.stereotype.Component;

/**
 * @author nasimkabir
 * @date 6/8/25
 */
@Component
public class LoansFallback implements LoansFeignClients{
    @Override
    public Response fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
