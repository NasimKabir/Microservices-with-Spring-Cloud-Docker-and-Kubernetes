package com.kabirit.loans_service.enums;

/**
 * @author nasimkabir
 * @date 17/4/25
 */

public enum LoansType {
    HOME_LOAN("Home_Loan", 0),
    NEW_LOAN_LIMIT("New_Loan", 1_00_000);

    private final String type;
    private final int loanLimit;

    LoansType(String type, int loanLimit) {
        this.type = type;
        this.loanLimit = loanLimit;
    }

    public String getType() {
        return type;
    }

    public int getLoanLimit() {
        return loanLimit;
    }
}
