package com.tinnguyen263.mykanban.controller.dtos;

public class CardWithLabelIdsDto extends CardDto {
    private int[] labelIds = {};

    public CardWithLabelIdsDto() {
    }

    public int[] getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(int[] labelIds) {
        this.labelIds = labelIds;
    }
}
