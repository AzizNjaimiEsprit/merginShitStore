package Beans;

import java.util.Objects;

public class FidelityCard {
    private int id;
    private User user;
    private int points;

    public FidelityCard(int id, User user, int points) {
        this.id = id;
        this.user = user;
        this.points = points;
    }

    public FidelityCard(User user, int points) {
        this.user = user;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FidelityCard that = (FidelityCard) o;
        return id == that.id &&
                points == that.points &&
                Objects.equals(user, that.getUser());
    }

    @Override
    public String toString() {
        return "FidelityCard{" +
                "id=" + id +
                ", user=" + user +
                ", points=" + points +
                '}';
    }
}
