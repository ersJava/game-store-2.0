package com.company.GameStore.controller;

import com.company.GameStore.service.InvoiceInventoryService;
import com.company.GameStore.viewmodel.GameViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameInventoryController {

    @Autowired
    InvoiceInventoryService invoiceInventoryService;

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameViewModel createGame(@RequestBody @Valid GameViewModel game) {

        return invoiceInventoryService.saveGame(game);
    }

    // GET ALL/ READ
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getAllGames() {

        return invoiceInventoryService.findAllGames();
    }

    // GET BY ID/ READ
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameViewModel getGame(@PathVariable("id") int id){

        return invoiceInventoryService.findGameById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@PathVariable("id") int id, @RequestBody @Valid GameViewModel game){

        invoiceInventoryService.updateGame(game, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable("id") int id) {

        invoiceInventoryService.removeGame(id);
    }

    // CUSTOM METHOD GET BY STUDIO
    @GetMapping("/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getGamesByStudio(@PathVariable("studio") String studio) {

        return invoiceInventoryService.findGamesByStudio(studio);
    }

    // CUSTOM METHOD GET BY ESRB RATING
    @GetMapping("/rating/{rating}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getGamesByRating(@PathVariable("rating")  String rating) {

        return invoiceInventoryService.findGamesByRating(rating);
    }

    // CUSTOM METHOD GET BY TITLE
    @GetMapping("/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public GameViewModel getGameByTitle(@PathVariable("title") String title) {

        return invoiceInventoryService.getGameByTitle(title);
    }
}
