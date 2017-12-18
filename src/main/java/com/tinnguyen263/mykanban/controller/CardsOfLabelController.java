package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.CardDto;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.model.Card;
import com.tinnguyen263.mykanban.model.Label;
import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.service.AuthorizationService;
import com.tinnguyen263.mykanban.service.CardService;
import com.tinnguyen263.mykanban.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/labels/{labelId}/cards")
public class CardsOfLabelController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private CardService cardService;
    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<CardDto> getCardsOfLabel(@PathVariable int labelId,
                                               Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        Label label = labelService.findByKey(labelId);
        Project project = label.getProject();

        if (!project.getPublic() && !authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        Collection<Card> cards = label.getCards();
        Collection<CardDto> cardDtos = new ArrayList<>();
        for (Card card : cards)
            cardDtos.add(new CardDto(card));
        return cardDtos;
    }
}
