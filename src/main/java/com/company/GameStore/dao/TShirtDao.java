package com.company.GameStore.dao;

import com.company.GameStore.model.TShirts;

import java.util.List;

public interface TShirtDao {

    // CRUD
    TShirts addTShirt(TShirts tShirts);

    TShirts getTShirt(int id);

    List<TShirts> getAllTShirts();

    void updateTShirt(TShirts tShirts);

    void deleteTShirt(int id);

    // Custom methods
    List<TShirts> getTShirtsByColor(String color);

    List<TShirts> getTShirtsBySize(String size);

}
