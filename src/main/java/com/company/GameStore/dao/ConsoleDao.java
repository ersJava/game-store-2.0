package com.company.GameStore.dao;

import com.company.GameStore.model.Consoles;

import java.util.List;

public interface ConsoleDao {

    // CRUD
    Consoles addConsole(Consoles consoles);

    Consoles getConsole(int id);

    List<Consoles> getAllConsoles();

    void updateConsole(Consoles consoles);

    void deleteConsole(int id);

    // Custom methods
    List<Consoles> getConsolesByManufacturer(String manufacturer);
}
