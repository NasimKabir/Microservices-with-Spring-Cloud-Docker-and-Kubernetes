package com.kabirit.cards_service.service.impl;


import com.kabirit.cards_service.dto.CardsDto;
import com.kabirit.cards_service.entity.Cards;
import com.kabirit.cards_service.enums.CardType;
import com.kabirit.cards_service.exceptions.CustomResponseStatusException;
import com.kabirit.cards_service.exceptions.Response;
import com.kabirit.cards_service.exceptions.ResponseBuilder;
import com.kabirit.cards_service.mapper.CardsMapper;
import com.kabirit.cards_service.repository.CardsRepository;
import com.kabirit.cards_service.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public Response createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CustomResponseStatusException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Cards is successfully created");
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardType.CREDIT_CARD.getType());
        newCard.setTotalLimit(CardType.NEW_CARD_LIMIT.getCardLoanLimit());
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardType.NEW_CARD_LIMIT.getCardLoanLimit());
        return newCard;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public Response fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomResponseStatusException("MobileNumber " + mobileNumber)
        );
        CardsDto cardsDto = CardsMapper.mapToCardsDto(cards, new CardsDto());
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Cards details", cardsDto);

    }

    /**
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public Response updateCard(CardsDto cardsDto) {
        boolean isUpdated = false;
        if (cardsDto != null) {
            Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                    () -> new CustomResponseStatusException("CardNumber " + cardsDto.getCardNumber()));
            CardsMapper.mapToCards(cardsDto, cards);
            cardsRepository.save(cards);
            isUpdated= true;
        }

        if (isUpdated) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Loan updated successfully", isUpdated);
        } else {
            return ResponseBuilder.getFailureResponse(HttpStatus.EXPECTATION_FAILED, "Loan update failed");
        }
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public Response deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomResponseStatusException("MobileNumber " + mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Loan deleted successfully");
    }


}
