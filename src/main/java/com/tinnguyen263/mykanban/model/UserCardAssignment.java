package com.tinnguyen263.mykanban.model;

import javax.persistence.*;

@Entity
@Table(name = "user_card_assignment", schema = "kanban", catalog = "")
@IdClass(UserCardAssignmentPK.class)
public class UserCardAssignment {
    private int userId;
    private int cardId;

    public UserCardAssignment() {
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "card_id", nullable = false)
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

        UserCardAssignment that = (UserCardAssignment) o;

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
