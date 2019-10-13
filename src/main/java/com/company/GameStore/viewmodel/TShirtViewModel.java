package com.company.GameStore.viewmodel;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class TShirtViewModel {

    private int tShirtId;

    @NotEmpty(message = "Please supply a size for t-shirt")
    private String size;

    @NotEmpty(message = "Please supply a color for t-shirt")
    private String color;

    @NotEmpty(message = "Please supply a description")
    private String description;

    @NotNull(message = "Please supply a value for price")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be $0.01 or greater.")
    @DecimalMax(value = "9999.99", inclusive = true)
    private BigDecimal price;

    @Min(value = 1, message = "Quanity cannot be empty and must be greater than 0.")
    private int quantity;

    public int getTShirtId() {
        return tShirtId;
    }

    public void setTShirtId(int tShirtId) {
        this.tShirtId = tShirtId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        TShirtViewModel that = (TShirtViewModel) o;
        return getTShirtId() == that.getTShirtId() &&
                getQuantity() == that.getQuantity() &&
                getSize().equals(that.getSize()) &&
                getColor().equals(that.getColor()) &&
                getDescription().equals(that.getDescription()) &&
                getPrice().equals(that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTShirtId(), getSize(), getColor(), getDescription(), getPrice(), getQuantity());
    }
}