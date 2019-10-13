package com.company.GameStore.controller;

import com.company.GameStore.service.InvoiceInventoryService;
import com.company.GameStore.viewmodel.ConsoleViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/console")
public class ConsoleInventoryController {

    @Autowired
    InvoiceInventoryService invoiceInventoryService;

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsoleViewModel createConsole(@RequestBody @Valid ConsoleViewModel console) {

        return invoiceInventoryService.saveConsole(console);
    }

    // GET ALL - * OPEN *
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConsoleViewModel> getAllConsoles() {

        return invoiceInventoryService.findAllConsoles();
    }

    // GET BY ID - * OPEN *
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsoleViewModel getConsole(@PathVariable("id") int id) {

        return invoiceInventoryService.findConsoleById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@PathVariable("id") int id, @RequestBody @Valid ConsoleViewModel console) {

            invoiceInventoryService.updateConsole(console, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable("id") int id) {

        invoiceInventoryService.removeConsole(id);
    }

    // CUSTOM
    // METHOD GET BY MANUFACTURER - * OPEN *
    @GetMapping("/manufacturer/{manufacturer}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsoleViewModel> getConsolesByManufacturer(@PathVariable("manufacturer") String manufacturer) {

        return invoiceInventoryService.findConsolesByManufacturer(manufacturer);
    }

}
