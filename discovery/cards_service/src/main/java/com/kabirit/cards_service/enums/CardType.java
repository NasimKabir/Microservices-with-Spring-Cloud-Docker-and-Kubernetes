package com.kabirit.cards_service.enums;

/**
 * @author nasimkabir
 * @date 17/4/25
 */

public enum CardType {
    CREDIT_CARD("CREDIT_CARD", 0),
    NEW_CARD_LIMIT("NEW_CARD_LIMIT", 1_00_000);

    private final String type;
    private final int cardLoanLimit;

    CardType(String type, int cardLoanLimit) {
        this.type = type;
        this.cardLoanLimit = cardLoanLimit;
    }

    public String getType() {
        return type;
    }

    public int getCardLoanLimit() {
        return cardLoanLimit;
    }
}
