package com.tinnguyen263.mykanban.controller.dtos;

import com.tinnguyen263.mykanban.model.Card;
import com.tinnguyen263.mykanban.model.Label;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CardWithLabelsDto extends CardDto {
    private List<LabelDto> labels;

    public CardWithLabelsDto(Card card, List<LabelDto> labels) {
        super(card);
        this.labels = labels;
    }

    public CardWithLabelsDto(Card card) {
        super(card);
        labels = new ArrayList<>();
        Collection<Label> cardLabels = card.getLabels();
        for (Label label : cardLabels) {
            LabelDto labelDto = new LabelDto(label);
            labels.add(labelDto);
        }
    }

    public List<LabelDto> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelDto> labels) {
        this.labels = labels;
    }
}
