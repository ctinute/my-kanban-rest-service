package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.CardDto;
import com.tinnguyen263.mykanban.controller.dtos.CardWithLabelIdsDto;
import com.tinnguyen263.mykanban.controller.dtos.CardWithLabelsDto;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.model.Card;
import com.tinnguyen263.mykanban.model.Label;
import com.tinnguyen263.mykanban.model.MColumn;
import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.service.AuthorizationService;
import com.tinnguyen263.mykanban.service.CardService;
import com.tinnguyen263.mykanban.service.LabelService;
import com.tinnguyen263.mykanban.service.MColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private MColumnService mColumnService;

    // add
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CardDto addCard(@RequestBody CardWithLabelIdsDto cardDto,
                           Principal principal) throws Exception {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = mColumnService.findByKey(cardDto.getColumnId());
        Project project = column.getProject();
        if (!authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        Card card = Converter.toEntity(cardDto, mColumnService);

        // TODO: adjust display order

        // int[] labelIds => Card.Labels
        ArrayList<Label> labels = new ArrayList<>();
        for (int labelId : cardDto.getLabelIds()) {
            Label label = labelService.findByKey(labelId);
            if (!label.getProject().getId().equals(project.getId()))
                throw new Exception();  // label of another project
            labels.add(label);
        }
        card.setLabels(labels);
        card = cardService.saveOrUpdate(card);

        return new CardWithLabelsDto(card);
    }

    // get
    @RequestMapping(value = "/{cardId}", method = RequestMethod.GET)
    public CardDto getCard(@PathVariable int cardId,
                           Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        Card card = cardService.findByKey(cardId);
        Project project = card.getColumn().getProject();

        if (!project.getPublic() && !authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        return new CardWithLabelsDto(card);
    }

    // update
    @RequestMapping(value = "/{cardId}", method = RequestMethod.PUT)
    public CardDto editCard(@PathVariable int cardId,
                            @RequestBody CardDto cardDto,
                            Principal principal) throws Exception {
        String username = Utils.getUsernameFromPrincipal(principal);
        Card card = cardService.findByKey(cardId);
        MColumn column = card.getColumn();

        if (!authorizationService.userCanAccessProject(username, column.getProject().getId()))
            throw new NoAccessPermissionException();

        card.setName(cardDto.getName());
        card.setContent(cardDto.getContent());
        card.setDueTime(cardDto.getDueTime());

        card.setDisplayOrder(cardDto.getDisplayOrder());
        // TODO: adjust display order

        cardService.saveOrUpdate(card);

        // move card to another column
        if (!Objects.equals(column.getId(), cardDto.getColumnId())) {
            MColumn desColumn = mColumnService.findByKey(cardDto.getColumnId());
            if (desColumn == null)
                throw new Exception();    // TODO: ERROR: adding card to undefined column
            else {
                // move card to desColumn
                card.setColumn(desColumn);
                cardService.saveOrUpdate(card);

                // re-arrange cards in desColumn
                ArrayList<Card> cards = new ArrayList<>(desColumn.getCards());
                for (int i = 0; i < cards.size(); i++) {
                    cards.get(i).setDisplayOrder(i);
                    // save
                    cardService.saveOrUpdate(cards.get(i));
                }
            }
        }

        return new CardDto(cardService.saveOrUpdate(card));
    }

    // delete
    @RequestMapping(value = "/{cardId}", method = RequestMethod.DELETE)
    public void deleteCard(@PathVariable int cardId,
                           Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        Card card = cardService.findByKey(cardId);
        Project project = card.getColumn().getProject();

        if (!authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        cardService.deleteByKey(cardId);
    }

}
