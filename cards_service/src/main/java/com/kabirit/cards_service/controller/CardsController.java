package com.kabirit.cards_service.controller;


import com.kabirit.cards_service.dto.CardsContactInfoDto;
import com.kabirit.cards_service.dto.CardsDto;
import com.kabirit.cards_service.exceptions.Response;
import com.kabirit.cards_service.exceptions.ResponseBuilder;
import com.kabirit.cards_service.service.ICardsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
@Validated
public class CardsController {

    private final ICardsService iCardsService;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final CardsContactInfoDto cardsContactInfoDto;



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

    @GetMapping("/build-info")
    public Response getBuildInfo() {
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Build Version: " + buildVersion);
    }

    @GetMapping("/java-version")
    public Response getJavaVersion() {
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "JDK Version: " + environment.getProperty("JAVA_HOME"));
    }


    @GetMapping("/contact-info")
    public Response getContactInfo() {
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Cards Contact Info ", cardsContactInfoDto);
    }

}
