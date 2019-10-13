package com.company.GameStore.service;

import com.company.GameStore.dao.*;
import com.company.GameStore.exception.NotFoundException;
import com.company.GameStore.model.*;
import com.company.GameStore.viewmodel.ConsoleViewModel;
import com.company.GameStore.viewmodel.GameViewModel;
import com.company.GameStore.viewmodel.InvoiceViewModel;
import com.company.GameStore.viewmodel.TShirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceInventoryService {

    ConsoleDao consoleDao;
    GameDao gameDao;
    TShirtDao tShirtDao;
    InvoiceDao invoiceDao;
    ProcessingFeeDao processingFeeDao;
    SalesTaxRateDao salesTaxRateDao;

    @Autowired
    public InvoiceInventoryService(ConsoleDao consoleDao, GameDao gameDao, TShirtDao tShirtDao, InvoiceDao invoiceDao, ProcessingFeeDao processingFeeDao, SalesTaxRateDao salesTaxRateDao) {
        this.consoleDao = consoleDao;
        this.gameDao = gameDao;
        this.tShirtDao = tShirtDao;
        this.invoiceDao = invoiceDao;
        this.processingFeeDao = processingFeeDao;
        this.salesTaxRateDao = salesTaxRateDao;
    }

    // *** GAME ***
    @Transactional
    public GameViewModel saveGame(GameViewModel gameViewModel) {

        Games game = new Games();
        game.setTitle(gameViewModel.getTitle());
        game.setESRBRating(gameViewModel.getEsrbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setPrice(gameViewModel.getPrice());
        game.setStudio(gameViewModel.getStudio());
        game.setQuantity(gameViewModel.getQuantity());
        game = gameDao.addGame(game);

        gameViewModel.setGameId(game.getGameId());

        return gameViewModel;
    }

    private GameViewModel buildGameViewModel(Games game) {

        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setGameId(game.getGameId());
        gameViewModel.setTitle(game.getTitle());
        gameViewModel.setEsrbRating(game.getESRBRating());
        gameViewModel.setDescription(game.getDescription());
        gameViewModel.setPrice(game.getPrice());
        gameViewModel.setStudio(game.getStudio());
        gameViewModel.setQuantity(game.getQuantity());

        return gameViewModel;
    }

    public List<GameViewModel> findAllGames() {

        List<Games> gameList = gameDao.getAllGames();

        List<GameViewModel> gameViewModelList = new ArrayList<>();

        for (Games game : gameList) {
            GameViewModel gvm = buildGameViewModel(game);
            gameViewModelList.add(gvm);
        }
        return gameViewModelList;
    }

    public GameViewModel findGameById(int id) {

        Games game = gameDao.getGame(id);

        if (game == null)
            throw new NotFoundException(String.format("Game could not be retrieved for id %s", id));
        else
            return buildGameViewModel(game);
    }

    public void removeGame(int id) {

        Games games = gameDao.getGame(id);

        if(games == null)
            throw new NotFoundException(String.format("Game could not be retrieved for id %s", id));

        gameDao.deleteGame(id);
    }

    @Transactional
    public void updateGame(GameViewModel gameViewModel, int id) {

        if (id != gameViewModel.getGameId()){
            throw new IllegalArgumentException("Id in path must match the id in the Game object");
        }

        Games game = new Games();
        game.setGameId(gameViewModel.getGameId());
        game.setTitle(gameViewModel.getTitle());
        game.setESRBRating(gameViewModel.getEsrbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setPrice(gameViewModel.getPrice());
        game.setStudio(gameViewModel.getStudio());
        game.setQuantity(gameViewModel.getQuantity());

        gameDao.updateGame(game);
    }

    // custom methods
    public List<GameViewModel> findGamesByStudio(String studio) {

        List<Games> gameList = gameDao.getGamesByStudio(studio);

        if (gameList != null && gameList.size() == 0)
            throw new NotFoundException(String.format("No games in the inventory found by %s studio", studio));
        else {
            List<GameViewModel> gvmList = new ArrayList<>();

            for (Games g : gameList) {
                GameViewModel gvm = buildGameViewModel(g);
                gvmList.add(gvm);
            }
            return gvmList;
        }
    }

    public List<GameViewModel> findGamesByRating(String rating) {

        List<Games> gameList = gameDao.getGamesByESRBRating(rating);

        if(gameList != null && gameList.size() == 0)
            throw new NotFoundException(String.format("No games in the inventory found with %s rating", rating));

        List<GameViewModel> gvmList = new ArrayList<>();

        for (Games g : gameList) {
            GameViewModel gvm = buildGameViewModel(g);
            gvmList.add(gvm);
        }
        return gvmList;
    }

    public GameViewModel getGameByTitle(String title) {

        Games game = gameDao.getGameByTitle(title);

        if(game != null && !game.getTitle().equals(title))
            throw new NotFoundException(String.format("No game in the inventory found with %s title", title));
        else
            return buildGameViewModel(game);
    }

    // *** Console ***
    @Transactional
    public ConsoleViewModel saveConsole(ConsoleViewModel consoleViewModel) {

        Consoles consoles = new Consoles();
        consoles.setModel(consoleViewModel.getModel());
        consoles.setManufacturer(consoleViewModel.getManufacturer());
        consoles.setMemoryAmount(consoleViewModel.getMemoryAmount());
        consoles.setProcessor(consoleViewModel.getProcessor());
        consoles.setPrice(consoleViewModel.getPrice());
        consoles.setQuantity(consoleViewModel.getQuantity());
        consoles = consoleDao.addConsole(consoles);

        consoleViewModel.setConsoleId(consoles.getConsoleId());

        return consoleViewModel;
    }

    private ConsoleViewModel buildConsoleViewModel(Consoles consoles) {

        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        consoleViewModel.setConsoleId(consoles.getConsoleId());
        consoleViewModel.setModel(consoles.getModel());
        consoleViewModel.setManufacturer(consoles.getManufacturer());
        consoleViewModel.setMemoryAmount(consoles.getMemoryAmount());
        consoleViewModel.setProcessor(consoles.getProcessor());
        consoleViewModel.setPrice(consoles.getPrice());
        consoleViewModel.setQuantity(consoles.getQuantity());

        return consoleViewModel;
    }

    public List<ConsoleViewModel> findAllConsoles() {

        List<Consoles> consolesList = consoleDao.getAllConsoles();

        List<ConsoleViewModel> consoleViewModelList = new ArrayList<>();

        for (Consoles consoles : consolesList) {
            ConsoleViewModel cvm = buildConsoleViewModel(consoles);
            consoleViewModelList.add(cvm);
        }
        return consoleViewModelList;
    }

    public ConsoleViewModel findConsoleById(int id) {

        Consoles consoles = consoleDao.getConsole(id);

        if (consoles == null)
            throw new NotFoundException(String.format("Console could not be retrieved for id %s", id));
        else
            return buildConsoleViewModel(consoles);
    }

    public void removeConsole(int id) {

        Consoles consoles = consoleDao.getConsole(id);

        if(consoles == null)
            throw new NotFoundException(String.format("Console could not be retrieved for id %s", id));

        consoleDao.deleteConsole(id);
    }
    @Transactional
    public void updateConsole(ConsoleViewModel consoleViewModel, int id) {

        if (id != consoleViewModel.getConsoleId()) {
            throw new IllegalArgumentException("Id in path must match the id in the Console object");
        } else {

            Consoles consoles = new Consoles();
            consoles.setConsoleId(consoleViewModel.getConsoleId());
            consoles.setModel(consoleViewModel.getModel());
            consoles.setManufacturer(consoleViewModel.getManufacturer());
            consoles.setMemoryAmount(consoleViewModel.getMemoryAmount());
            consoles.setProcessor(consoleViewModel.getProcessor());
            consoles.setPrice(consoleViewModel.getPrice());
            consoles.setQuantity(consoleViewModel.getQuantity());

            consoleDao.updateConsole(consoles);
        }
    }

    // custom method
   public List<ConsoleViewModel> findConsolesByManufacturer(String manufacturer) {

       List<Consoles> consolesList = consoleDao.getConsolesByManufacturer(manufacturer);

       if (consolesList != null && consolesList.size() == 0)
           throw new NotFoundException(String.format("No consoles in the inventory found by %s", manufacturer));
       else {
           List<ConsoleViewModel> cvmList = new ArrayList<>();

           for (Consoles c : consolesList) {
               ConsoleViewModel cvm = buildConsoleViewModel(c);
               cvmList.add(cvm);
           }
           return cvmList;
       }
   }

    // *** TShirt ***
    @Transactional
    public TShirtViewModel saveTShirt(TShirtViewModel tShirtViewModel) {

        TShirts tShirts = new TShirts();
        tShirts.setSize(tShirtViewModel.getSize());
        tShirts.setColor(tShirtViewModel.getColor());
        tShirts.setDescription(tShirtViewModel.getDescription());
        tShirts.setPrice(tShirtViewModel.getPrice());
        tShirts.setQuantity(tShirtViewModel.getQuantity());
        tShirts = tShirtDao.addTShirt(tShirts);

        tShirtViewModel.setTShirtId(tShirts.getTShirtId());
        return tShirtViewModel;
    }

    private TShirtViewModel buildTShirtViewModel(TShirts tShirts) {

        TShirtViewModel tShirtViewModel = new TShirtViewModel();
        tShirtViewModel.setTShirtId(tShirts.getTShirtId());
        tShirtViewModel.setSize(tShirts.getSize());
        tShirtViewModel.setColor(tShirts.getColor());
        tShirtViewModel.setDescription(tShirts.getDescription());
        tShirtViewModel.setPrice(tShirts.getPrice());
        tShirtViewModel.setQuantity(tShirts.getQuantity());

        return tShirtViewModel;
    }

    public List<TShirtViewModel> findAllTShirts() {

        List<TShirts> tShirtsList = tShirtDao.getAllTShirts();

        List<TShirtViewModel> tShirtViewModelList = new ArrayList<>();

        for (TShirts tShirts : tShirtsList) {
            TShirtViewModel tvm = buildTShirtViewModel(tShirts);
            tShirtViewModelList.add(tvm);
        }
        return tShirtViewModelList;
    }

    public TShirtViewModel findTShirtById(int id) {

        TShirts tShirts = tShirtDao.getTShirt(id);
        if (tShirts == null)
            throw new NotFoundException(String.format("T-Shirt could not be retrieved for id %s", id));
        else
            return buildTShirtViewModel(tShirts);
    }

    public void removeTShirt(int id) {

        TShirts tshirt = tShirtDao.getTShirt(id);

        if(tshirt == null)
            throw new NotFoundException(String.format("T-Shirt could not be retrieved for id %s", id));

        tShirtDao.deleteTShirt(id);
    }

    @Transactional
    public void updateTShirt(TShirtViewModel tShirtViewModel, int id) {

        if (id != tShirtViewModel.getTShirtId()) {
            throw new IllegalArgumentException("Id in path must match the id in the T-Shirt object");
        } else {

            TShirts tShirts = new TShirts();
            tShirts.setTShirtId(tShirtViewModel.getTShirtId());
            tShirts.setSize(tShirtViewModel.getSize());
            tShirts.setColor(tShirtViewModel.getColor());
            tShirts.setDescription(tShirtViewModel.getDescription());
            tShirts.setPrice(tShirtViewModel.getPrice());
            tShirts.setQuantity(tShirtViewModel.getQuantity());

            tShirtDao.updateTShirt(tShirts);
        }
    }

    // custom methods
    public List<TShirtViewModel> findTShirtByColor(String color) {

        List<TShirts> tShirtsList = tShirtDao.getTShirtsByColor(color);

        if (tShirtsList != null && tShirtsList.size() == 0)
            throw new NotFoundException(String.format("No T-Shirts in the inventory found for the color %s", color));
        else {
            List<TShirtViewModel> tvmList = new ArrayList<>();

            for (TShirts t : tShirtsList) {
                TShirtViewModel tvm = buildTShirtViewModel(t);
                tvmList.add(tvm);
            }
            return tvmList;
        }
    }

    public List<TShirtViewModel> findTShirtsBySize(String size) {

        List<TShirts> tShirtsList = tShirtDao.getTShirtsBySize(size);

        if (tShirtsList != null && tShirtsList.size() == 0)
            throw new NotFoundException(String.format("No T-Shirts in the inventory found for %s size", size));
        else {

            List<TShirtViewModel> tvmList = new ArrayList<>();

            for (TShirts t : tShirtsList) {
                TShirtViewModel tvm = buildTShirtViewModel(t);
                tvmList.add(tvm);
            }
            return tvmList;
        }
    }

    // *** Invoice ***

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel invoiceViewModel) {

        // user input
        Invoice invoice = new Invoice();
        invoice.setName(invoiceViewModel.getName());
        invoice.setStreet(invoiceViewModel.getStreet());
        invoice.setCity(invoiceViewModel.getCity());
        invoice.setState(invoiceViewModel.getState());
        invoice.setZipCode(invoiceViewModel.getZipCode());
        invoice.setItemType(invoiceViewModel.getItemType());
        invoice.setItemId(invoiceViewModel.getItemId());
        invoice.setQuantity(validateQuantityAndItem(invoiceViewModel.getQuantity(), invoiceViewModel.getItemType(), invoiceViewModel.getItemId()));
        // *** response ***
        //UNIT PRICE
        invoice.setUnitPrice(getItemTypePrice(invoiceViewModel.getItemType(), invoiceViewModel.getItemId()));
        //SUBTOTAL
        BigDecimal subtotal = getSubtotal(invoiceViewModel.getQuantity(), invoice.getUnitPrice());
        invoice.setSubtotal(subtotal);
        // TAX
        invoice.setTax(getSalesTaxRate(invoiceViewModel.getState(), invoice.getSubtotal()));
        // PROCESSING FEE
        invoice.setProcessingFee(getProcessingFee(invoiceViewModel.getItemType(), invoiceViewModel.getQuantity()));
        // TOTAL
        invoice.setTotal(getTotal(subtotal, invoice.getTax(), invoice.getProcessingFee()));

        // adding invoice to database
        invoice = invoiceDao.addInvoice(invoice);

        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setUnitPrice(invoice.getUnitPrice());
        invoiceViewModel.setSubtotal(invoice.getSubtotal());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTax(invoice.getTax());
        invoiceViewModel.setTotal(invoice.getTotal());
        return invoiceViewModel;
    }

    private int validateQuantityAndItem(int requestedQuantity, String itemType, int itemId){

        switch(itemType.toLowerCase()){

            case "t-shirts":
                TShirts tShirts = tShirtDao.getTShirt(itemId);
                if(tShirts == null){
                    throw new IllegalArgumentException("Invalid T-Shirt product");
                }
                int inStockT = tShirts.getQuantity();
                if(inStockT>= requestedQuantity){
                    tShirts.setQuantity(inStockT-requestedQuantity);
                    tShirtDao.updateTShirt(tShirts);
                    return requestedQuantity;
                } else
                    throw new IllegalArgumentException("Quantity unavailable");

            case "consoles":
                Consoles consoles = consoleDao.getConsole(itemId);
                if(consoles == null){
                    throw new IllegalArgumentException("Invalid console product");
                }
                int inStockC = consoles.getQuantity();
                if(inStockC>=requestedQuantity){
                    consoles.setQuantity(inStockC-requestedQuantity);
                    consoleDao.updateConsole(consoles);
                    return requestedQuantity;
                } else
                    throw new IllegalArgumentException("Quantity unavailable");

            case "games":
                Games game = gameDao.getGame(itemId);
                if(game == null){
                    throw new IllegalArgumentException("Invalid game product");
                }
                int inStockG = game.getQuantity();
                if(inStockG>=requestedQuantity){
                    game.setQuantity(inStockG-requestedQuantity);
                    gameDao.updateGame(game);
                    return requestedQuantity;
                } else
                    throw new IllegalArgumentException("Quantity unavailable");
            default:
                throw new IllegalArgumentException("Invalid product");
        }

    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setName(invoice.getName());
        invoiceViewModel.setStreet(invoice.getStreet());
        invoiceViewModel.setCity(invoice.getCity());
        invoiceViewModel.setState(invoice.getState());
        invoiceViewModel.setZipCode(invoice.getZipCode());
        invoiceViewModel.setItemType(invoice.getItemType());
        invoiceViewModel.setItemId(invoice.getItemId());
        invoiceViewModel.setQuantity(invoice.getQuantity());
        invoiceViewModel.setUnitPrice(invoice.getUnitPrice());
        invoiceViewModel.setSubtotal(invoice.getSubtotal());
        invoiceViewModel.setTax(invoice.getTax());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTotal(invoice.getTotal());

        return invoiceViewModel;
    }

    public InvoiceViewModel findInvoiceById(int id) {

        Invoice invoice = invoiceDao.getInvoice(id);

        if (invoice == null)
            throw new NotFoundException(String.format("Invoice could not be retrieved for id %s", id));
        else
            return buildInvoiceViewModel(invoice);
    }

    public List<InvoiceViewModel> findAllInvoices() {

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();

        for (Invoice invoice : invoiceList) {
            InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            invoiceViewModelList.add(ivm);
        }
        return invoiceViewModelList;
    }

    public void removeInvoice(int id) {

        Invoice invoice = invoiceDao.getInvoice(id);

        if(invoice == null)
            throw new NotFoundException(String.format("Invoice could not be retrieved for id %s", id));

        invoiceDao.deleteInvoice(id);
    }

    // *** Calculation methods for unit price, subtotal, tax, processing fee and total ***
    private BigDecimal getTotal(BigDecimal subtotal, BigDecimal taxRate, BigDecimal processingFee) {
        return subtotal.add(taxRate).add(processingFee);
    }

    private BigDecimal getProcessingFee(String itemType, int quantity) {

        ProcessingFee pf = processingFeeDao.getProcessingFee(itemType.toLowerCase());
        BigDecimal processingFee = pf.getFee();

        if (quantity > 10) {
            return processingFee.add(new BigDecimal("15.49"));
        } else
            return processingFee;
    }

    private BigDecimal getSubtotal(int quantity, BigDecimal unitPrice) {

        BigDecimal subtotal;
        subtotal = unitPrice.multiply(new BigDecimal(quantity));
        return subtotal;
    }

    // Item price
    private BigDecimal getItemTypePrice(String itemType, int id) {

        switch (itemType.toLowerCase()) {

            case "t-shirts":
                TShirts t = tShirtDao.getTShirt(id);

                return t.getPrice();

            case "games":
                Games g = gameDao.getGame(id);
                return g.getPrice();

            case "consoles":
                Consoles c = consoleDao.getConsole(id);
                return c.getPrice();

            default:
                return null;
        }
    }

    // Tax amount
    private BigDecimal getSalesTaxRate(String state, BigDecimal subtotal) {

        SalesTaxRate str = salesTaxRateDao.getSalesTaxRate(state);
        String stateCode = str.getState();
        BigDecimal salesTax = str.getRate();

        if (stateCode == null) {
            throw new IllegalArgumentException("Invalid state code");
        } else
            return salesTax.multiply(subtotal).setScale(2, RoundingMode.HALF_UP);
    }

}

