package com.tinnguyen263.mykanban.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class UserCardAssignmentPK implements Serializable {
    private int userId;
    private int cardId;

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "card_id", nullable = false)
    @Id
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCardAssignmentPK that = (UserCardAssignmentPK) o;

        if (userId != that.userId) return false;
        if (cardId != that.cardId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + cardId;
        return result;
    }
}
