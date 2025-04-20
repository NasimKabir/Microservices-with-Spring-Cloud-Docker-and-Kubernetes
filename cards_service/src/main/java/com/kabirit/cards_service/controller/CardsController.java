package com.kabirit.cards_service.controller;


import com.kabirit.cards_service.dto.CardsDto;
import com.kabirit.cards_service.exceptions.Response;
import com.kabirit.cards_service.service.ICardsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eazy Bytes
 */

@Tag(
        name = "CRUD REST APIs for Cards in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {

    private ICardsService iCardsService;


    @PostMapping("/create")
    public Response createCard(@Valid @RequestParam
                                                      @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
       return iCardsService.createCard(mobileNumber);
    }


    @GetMapping("/fetch")
    public Response fetchCardDetails(@RequestParam
                                                               @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        return iCardsService.fetchCard(mobileNumber);
    }

    @PutMapping("/update")
    public Response updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        return iCardsService.updateCard(cardsDto);
    }

    @DeleteMapping("/delete")
    public Response deleteCardDetails(@RequestParam
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        return iCardsService.deleteCard(mobileNumber);
    }

}
