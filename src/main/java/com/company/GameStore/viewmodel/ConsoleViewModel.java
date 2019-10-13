package com.company.GameStore.viewmodel;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class ConsoleViewModel{

    private int consoleId;

    @NotEmpty(message="Please supply a model")
    private String model;

    @NotEmpty(message="Please supply a manufacturer")
    private String manufacturer;

    // database indicates it can be null
    private String memoryAmount;

    // database indicates it can be null
    private String processor;

    @NotNull(message = "Please supply a value for the price")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be $0.01 or greater.")
    @DecimalMax(value = "9999.99", inclusive = true)
    private BigDecimal price;

    @Min(value = 1, message = "Quanity cannot be empty and must be greater than 0.")
    private int quantity;

    public int getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(int consoleId) {
        this.consoleId = consoleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMemoryAmount() {
        return memoryAmount;
    }

    public void setMemoryAmount(String memoryAmount) {
        this.memoryAmount = memoryAmount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
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
        ConsoleViewModel that = (ConsoleViewModel) o;
        return getConsoleId() == that.getConsoleId() &&
                getQuantity() == that.getQuantity() &&
                getModel().equals(that.getModel()) &&
                getManufacturer().equals(that.getManufacturer()) &&
                getMemoryAmount().equals(that.getMemoryAmount()) &&
                getProcessor().equals(that.getProcessor()) &&
                getPrice().equals(that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConsoleId(), getModel(), getManufacturer(), getMemoryAmount(), getProcessor(), getPrice(), getQuantity());
    }
}
