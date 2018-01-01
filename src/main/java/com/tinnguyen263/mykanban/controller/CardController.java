package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.CardDto;
import com.tinnguyen263.mykanban.controller.dtos.CardWithLabelIdsDto;
import com.tinnguyen263.mykanban.controller.dtos.CardWithLabelsDto;
import com.tinnguyen263.mykanban.exceptions.DataConfictException;
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
import java.util.List;

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
    private MColumnService columnService;

    // add
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CardDto addCard(@RequestBody CardWithLabelIdsDto cardDto,
                           Principal principal) throws Exception {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = columnService.findByKey(cardDto.getColumnId());
        Project project = column.getProject();
        if (!authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        Card card = Converter.toEntity(cardDto, columnService);

        // label ids => Card.Labels
        ArrayList<Label> labels = new ArrayList<>();
        for (int labelId : cardDto.getLabelIds()) {
            Label label = labelService.findByKey(labelId);
            if (!label.getProject().getId().equals(project.getId()))
                throw new DataConfictException("Can not add label from another project !");
            labels.add(label);
        }
        card.setLabels(labels);

        // adjust other cards
        List<Card> cards = new ArrayList<>(column.getCards());
        for (Card c : cards) {
            if (c.getDisplayOrder() >= card.getDisplayOrder())
                c.setDisplayOrder(c.getDisplayOrder() + 1);
            cardService.saveOrUpdate(c);
        }

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

        // move card to another column
        if (cardDto.getColumnId() != null && !column.getId().equals(cardDto.getColumnId())) {
            MColumn desColumn = columnService.findByKey(cardDto.getColumnId());

            // update old column's cards
            List<Card> cards = new ArrayList<>(column.getCards());
            for (Card c : cards) {
                if (c.getDisplayOrder() > card.getDisplayOrder()) {
                    c.setDisplayOrder(c.getDisplayOrder() - 1);
                    cardService.saveOrUpdate(c);
                }
            }

            card.setColumn(desColumn);
            card.setDisplayOrder(desColumn.getCards().size());

            column = desColumn;
        }

        // update other info
        card.setName(cardDto.getName());
        card.setContent(cardDto.getContent());
        card.setDueTime(cardDto.getDueTime());

        System.out.println(cardDto.getDueTime());
        System.out.println(card.getDueTime());

        int src = card.getDisplayOrder();
        int des = cardDto.getDisplayOrder();

        if (des != src) {
            List<Card> cards = new ArrayList<>(column.getCards());
            if (des < 0) {
                des = 0;
            } else if (des > cards.size() - 1) {
                des = cards.size() - 1;
            }

            // update other cards
            for (Card c : cards) {
                int index = c.getDisplayOrder();
                if (index < src && index >= des) { // move card back, move others forward
                    c.setDisplayOrder(index + 1);
                    cardService.saveOrUpdate(c);
                }
                if (index > src && index <= des) { // // move card forwaard, move others back
                    c.setDisplayOrder(index - 1);
                    cardService.saveOrUpdate(c);
                }
            }
        }

        card.setDisplayOrder(des);
        cardService.saveOrUpdate(card);


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

        // update order
        List<Card> cards = new ArrayList<>(card.getColumn().getCards());
        for (Card c : cards) {
            if (c.getDisplayOrder() > card.getDisplayOrder()) {
                c.setDisplayOrder(c.getDisplayOrder() - 1);
                cardService.saveOrUpdate(c);
            }
        }

        cardService.deleteByKey(cardId);
    }

}
