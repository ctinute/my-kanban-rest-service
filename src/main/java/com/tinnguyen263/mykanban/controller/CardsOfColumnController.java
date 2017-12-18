package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.CardDto;
import com.tinnguyen263.mykanban.controller.dtos.CardWithLabelIdsDto;
import com.tinnguyen263.mykanban.controller.dtos.CardWithLabelsDto;
import com.tinnguyen263.mykanban.controller.dtos.LabelDto;
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
import java.util.Collection;

@RestController
@RequestMapping("/api/columns/{columnId}/cards")
public class CardsOfColumnController {

    @Autowired
    private CardService cardService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private MColumnService mColumnService;

    // get cards of column
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<CardWithLabelsDto> getCards(@PathVariable int columnId,
                                                  Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = mColumnService.findByKey(columnId);
        Project project = column.getProject();

        if (!project.getPublic() && !authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        Collection<Card> cards = column.getCards();
        Collection<CardWithLabelsDto> cardDtos = new ArrayList<>();
        for (Card card : cards) {
            Collection<Label> labels = card.getLabels();
            ArrayList<LabelDto> labelDtos = new ArrayList<>();
            for (Label label : labels) {
                LabelDto labelDto = new LabelDto(label);
                labelDtos.add(labelDto);
            }
            cardDtos.add(new CardWithLabelsDto(card, labelDtos));
        }
        return cardDtos;
    }

    // add
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CardDto addCard(@PathVariable int columnId,
                           @RequestBody CardWithLabelIdsDto cardDto,
                           Principal principal) throws Exception {
        String username = Utils.getUsernameFromPrincipal(principal);
        cardDto.setColumnId(columnId);  // override
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
}
