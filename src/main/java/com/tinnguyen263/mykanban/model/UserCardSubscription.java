package com.tinnguyen263.mykanban.model;

import javax.persistence.*;

@Entity
@Table(name = "user_card_subscription", schema = "kanban", catalog = "")
@IdClass(UserCardSubscriptionPK.class)
public class UserCardSubscription {
    private int cardId;
    private int userId;

    public UserCardSubscription() {
    }

    @Id
    @Column(name = "card_id", nullable = false)
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
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

        UserCardSubscription that = (UserCardSubscription) o;

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
