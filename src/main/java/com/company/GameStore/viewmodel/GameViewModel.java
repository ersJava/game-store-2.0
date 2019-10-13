package com.company.GameStore.viewmodel;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class GameViewModel {

    private int gameId;

    @NotEmpty(message="Please supply a game title")
    private String title;

    @NotEmpty(message="Please supply an ESRB rating")
    private String esrbRating;

    @NotEmpty(message="Please supply a game description")
    private String description;

    @NotNull(message="Please supply a value for the price")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be $0.01 or greater.")
    @DecimalMax(value = "9999.99", inclusive = true)
    private BigDecimal price;

    @NotEmpty(message="Please supply a name for studio")
    private String studio;

    @Min(value = 1, message = "Quanity cannot be empty and must be greater than 0.")
    private int quantity;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameViewModel that = (GameViewModel) o;
        return getGameId() == that.getGameId() &&
                getQuantity() == that.getQuantity() &&
                getTitle().equals(that.getTitle()) &&
                getEsrbRating().equals(that.getEsrbRating()) &&
                getDescription().equals(that.getDescription()) &&
                getPrice().equals(that.getPrice()) &&
                getStudio().equals(that.getStudio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getTitle(), getEsrbRating(), getDescription(), getPrice(), getStudio(), getQuantity());
    }
}
