package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.CardDto;
import com.tinnguyen263.mykanban.controller.dtos.LabelDto;
import com.tinnguyen263.mykanban.controller.dtos.MColumnDto;
import com.tinnguyen263.mykanban.model.Card;
import com.tinnguyen263.mykanban.model.Label;
import com.tinnguyen263.mykanban.model.MColumn;
import com.tinnguyen263.mykanban.service.MColumnService;

class Converter {

    // CardDto => Card
    static Card toEntity(CardDto cardDto, MColumnService mColumnService) {
        Card card = new Card();
        card.setName(cardDto.getName());
        card.setContent(cardDto.getContent());
        card.setDueTime(cardDto.getDueTime());
        card.setDisplayOrder(cardDto.getDisplayOrder());
        card.setColumn(mColumnService.findByKey(cardDto.getColumnId()));
        return card;
    }

    // MColumnDto => MColumn
    static MColumn toEntity(MColumnDto mColumnDto) {
        return new MColumn(
                mColumnDto.getName(),
                mColumnDto.getDescription(),
                mColumnDto.getDisplayOrder(),
                mColumnDto.getCardLimit()
        );
    }

    // LabelDto => Label
    static Label toEntity(LabelDto labelDto) {
        return new Label(labelDto.getName(), labelDto.getColor());
    }
}
