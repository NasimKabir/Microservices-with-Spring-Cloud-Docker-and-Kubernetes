package com.kabirit.accounts_service.enums;

/**
 * @author nasimkabir
 * @date 17/4/25
 */

public enum AccountsType {
    SAVINGS("SAVINGS"),
    CURRENT("CURRENT"),
    FIXED("FIXED");

    private final String type;

    AccountsType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static AccountsType fromString(String type) {
        for (AccountsType accountType : AccountsType.values()) {
            if (accountType.getType().equalsIgnoreCase(type)) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + AccountsType.class.getCanonicalName() + "." + type);
    }
}
