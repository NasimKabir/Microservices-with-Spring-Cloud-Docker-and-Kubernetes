package com.kabirit.accounts_service.service.clients;

import com.kabirit.accounts_service.exceptions.Response;
import org.springframework.stereotype.Component;

/**
 * @author nasimkabir
 * @date 6/8/25
 */
@Component
public class CardsFallback implements CardsFeignClients{
    @Override
    public Response fetchCardDetails(String mobileNumber) {
        return null;
    }
}
