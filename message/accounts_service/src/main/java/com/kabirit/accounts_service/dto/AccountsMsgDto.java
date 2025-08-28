package com.kabirit.accounts_service.dto;

/**
 * @author nasimkabir
 * @date 17/8/25
 */

public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}
