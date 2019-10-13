package com.company.GameStore.controller;

import com.company.GameStore.service.InvoiceInventoryService;
import com.company.GameStore.viewmodel.TShirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tshirt")
public class TShirtInventoryController {

    @Autowired
    InvoiceInventoryService invoiceInventoryService;

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TShirtViewModel createTShirt(@RequestBody @Valid TShirtViewModel tShirt){

        return invoiceInventoryService.saveTShirt(tShirt);
    }

    // GET ALL/ READ
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TShirtViewModel> getAllTShirts() {

        return invoiceInventoryService.findAllTShirts();
    }

    // GET BY ID/ READ
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TShirtViewModel getTShirt(@PathVariable("id") int id) {

        return invoiceInventoryService.findTShirtById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTShirt(@PathVariable("id") int id, @RequestBody @Valid TShirtViewModel tShirt) {

        invoiceInventoryService.updateTShirt(tShirt, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTShirt(@PathVariable("id") int id) {

        invoiceInventoryService.removeTShirt(id);
    }

    // CUSTOM METHOD GET BY COLOR
    @GetMapping("/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirtViewModel> getTShirtByColor(@PathVariable("color") String color) {

        return invoiceInventoryService.findTShirtByColor(color);

    }

    // CUSTOM METHOD GET BY SIZE
    @GetMapping("/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirtViewModel> getTShirtBySize(@PathVariable("size") String size) {

        return invoiceInventoryService.findTShirtsBySize(size);
    }

}
