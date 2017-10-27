package com.tinnguyen263.mykanban.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class UserCardSubscriptionPK implements Serializable {
    private int cardId;
    private int userId;

    @Column(name = "card_id", nullable = false)
    @Id
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCardSubscriptionPK that = (UserCardSubscriptionPK) o;

        if (cardId != that.cardId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardId;
        result = 31 * result + userId;
        return result;
    }
}
