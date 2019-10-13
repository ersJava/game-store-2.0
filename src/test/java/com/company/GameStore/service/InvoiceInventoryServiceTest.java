package com.company.GameStore.service;

import com.company.GameStore.dao.*;
import com.company.GameStore.exception.NotFoundException;
import com.company.GameStore.model.*;
import com.company.GameStore.viewmodel.ConsoleViewModel;
import com.company.GameStore.viewmodel.GameViewModel;
import com.company.GameStore.viewmodel.InvoiceViewModel;
import com.company.GameStore.viewmodel.TShirtViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class InvoiceInventoryServiceTest {

    InvoiceInventoryService invoiceInventoryService;

    ConsoleDao consoleDao;
    GameDao gameDao;
    TShirtDao tShirtDao;
    InvoiceDao invoiceDao;
    ProcessingFeeDao processingFeeDao;
    SalesTaxRateDao salesTaxRateDao;


    @Before
    public void setUp() throws Exception {

        setUpConsoleDaoMock();
        setUpGameDaoMock();
        setUpTShirtDaoMock();
        setUpInvoiceDaoMock();

        invoiceInventoryService = new InvoiceInventoryService(consoleDao, gameDao, tShirtDao, invoiceDao, processingFeeDao, salesTaxRateDao);
    }

    private void setUpGameDaoMock() {

        gameDao = mock(GameDaoJdbcTemplateImpl.class);

        Games game = new Games();
        game.setGameId(12);
        game.setTitle("Final Fantasy VI");
        game.setESRBRating("Everyone 10+");
        game.setDescription("RPG about a conflict between the Gestahlian Empire conquering the world and a rebel faction opposed to them known as the Returners, taking place in a fantasy steampunk-style world.");
        game.setPrice(new BigDecimal("8.99"));
        game.setStudio("Square Enix");
        game.setQuantity(18);

        Games game1 = new Games();
        game1.setTitle("Final Fantasy VI");
        game1.setESRBRating("Everyone 10+");
        game1.setDescription("RPG about a conflict between the Gestahlian Empire conquering the world and a rebel faction opposed to them known as the Returners, taking place in a fantasy steampunk-style world.");
        game1.setPrice(new BigDecimal("8.99"));
        game1.setStudio("Square Enix");
        game1.setQuantity(18);

        // Second mock Game Object
        Games game2 = new Games();
        game2.setGameId(26);
        game2.setTitle("Chrono Trigger");
        game2.setESRBRating("Everyone 10+");
        game2.setDescription("RPG that takes place in a fictitious alternate timeline of Earth where the characters travel through different time period from 65,000,000 B.C. at the dawn of civilization to 2300 A.D., a post-apocalyptic time period. .");
        game2.setPrice(new BigDecimal("7.99"));
        game2.setStudio("Square Enix");
        game2.setQuantity(21);

        // Mock addGame
        doReturn(game).when(gameDao).addGame(game1);

        // Mock getAllGames
        List<Games> gameList = new ArrayList<>();
        gameList.add(game);
        doReturn(gameList).when(gameDao).getAllGames();

        // Mock getGame
        doReturn(game).when(gameDao).getGame(12);

        // Mock data for update
        Games gameUpdate = new Games();
        gameUpdate.setGameId(11);
        gameUpdate.setTitle("Secret of Mana");
        gameUpdate.setESRBRating("Everyone 10+");
        gameUpdate.setDescription("RPG that place in a fictional world, during an unspecified period following a war between a civilization and gods concerning the use of mana to fuel the Mana Fortress, a flying warship. Using the power of the Mana Sword, a hero destroyed the fortress and returned peace to the world.");
        gameUpdate.setPrice(new BigDecimal("7.99"));
        gameUpdate.setStudio("Square");
        gameUpdate.setQuantity(10);

        // Mock updateGame
        doNothing().when(gameDao).updateGame(gameUpdate);
        doReturn(gameUpdate).when(gameDao).getGame(11);

        // Mock removeGame
        doNothing().when(gameDao).deleteGame(21);
        doReturn(null).when(gameDao).getGame(21);

        // *** CUSTOM METHODS ***
        // Mock getGamesByESRBRating
        List<Games> gamesByRatingList = new ArrayList<>();
        gamesByRatingList.add(game2);
        gamesByRatingList.add(game);
         doReturn(gamesByRatingList).when(gameDao).getGamesByESRBRating("Everyone 10+");

        // Mock getGamesByStudio
        List<Games> gamesByStudioList = new ArrayList<>();
        gamesByStudioList.add(game2);
        gamesByStudioList.add(game);
        doReturn(gamesByStudioList).when(gameDao).getGamesByStudio("Square Enix");

        // Mock getGameByTitle
        doReturn(game).when(gameDao).getGameByTitle("Final Fantasy VI");

    }

    @Test
    public void saveFindGame() {

        GameViewModel game = new GameViewModel();

        game.setTitle("Final Fantasy VI");
        game.setEsrbRating("Everyone 10+");
        game.setDescription("RPG about a conflict between the Gestahlian Empire conquering the world and a rebel faction opposed to them known as the Returners, taking place in a fantasy steampunk-style world.");
        game.setPrice(new BigDecimal("8.99"));
        game.setStudio("Square Enix");
        game.setQuantity(18);

        game = invoiceInventoryService.saveGame(game);

        GameViewModel fromService = invoiceInventoryService.findGameById(game.getGameId());
        assertEquals(fromService, game);
    }

    @Test
    public void findAllGames() {

        List<GameViewModel> gameList = invoiceInventoryService.findAllGames();
        assertEquals(1, gameList.size());
    }

    @Test(expected = NotFoundException.class)
    public void findGamesByStudio() {

        List<GameViewModel> gamesByStudioList = invoiceInventoryService.findGamesByStudio("Square Enix");
        assertEquals(2, gamesByStudioList.size());

        gamesByStudioList = invoiceInventoryService.findGamesByStudio("Rockstar");
        assertEquals(gamesByStudioList.size(), 0);

    }

    @Test(expected = NotFoundException.class)
    public void findGamesByRating() {

        List<GameViewModel> gamesByRatingList = invoiceInventoryService.findGamesByRating("Everyone 10+");
        assertEquals(2, gamesByRatingList.size());

        gamesByRatingList = invoiceInventoryService.findGamesByRating("Teen");
        assertEquals(gamesByRatingList.size(), 0);
    }

    @Test
    public void getGameByTitle() {

        GameViewModel game = new GameViewModel();

        game.setTitle("Final Fantasy VI");
        game.setEsrbRating("Everyone 10+");
        game.setDescription("RPG about a conflict between the Gestahlian Empire conquering the world and a rebel faction opposed to them known as the Returners, taking place in a fantasy steampunk-style world.");
        game.setPrice(new BigDecimal("8.99"));
        game.setStudio("Square Enix");
        game.setQuantity(18);

        game = invoiceInventoryService.saveGame(game);

        GameViewModel fromService = invoiceInventoryService.getGameByTitle("Final Fantasy VI");
        assertEquals(game, fromService);

    }

    @Test
    public void updateGame() {

        GameViewModel gvmUpdate = new GameViewModel();

        Games gameUpdate = new Games();
        gameUpdate.setGameId(11);
        gameUpdate.setTitle("Secret of Mana");
        gameUpdate.setESRBRating("Everyone 10+");
        gameUpdate.setDescription("RPG that place in a fictional world, during an unspecified period following a war between a civilization and gods concerning the use of mana to fuel the Mana Fortress, a flying warship. Using the power of the Mana Sword, a hero destroyed the fortress and returned peace to the world.");
        gameUpdate.setPrice(new BigDecimal("7.99"));
        gameUpdate.setStudio("Square");
        gameUpdate.setQuantity(10);

        gvmUpdate.setGameId(gameUpdate.getGameId());
        gvmUpdate.setTitle(gameUpdate.getTitle());
        gvmUpdate.setEsrbRating(gameUpdate.getESRBRating());
        gvmUpdate.setDescription(gameUpdate.getDescription());
        gvmUpdate.setPrice(gameUpdate.getPrice());
        gvmUpdate.setStudio(gameUpdate.getStudio());
        gvmUpdate.setQuantity(gameUpdate.getQuantity());

        invoiceInventoryService.updateGame(gvmUpdate, 11);

        GameViewModel afterUpdate = invoiceInventoryService.findGameById(gvmUpdate.getGameId());

        assertEquals(afterUpdate, gvmUpdate);
    }

    @Test(expected = NotFoundException.class)
    public void removeGame() {

        invoiceInventoryService.removeGame(21);

        GameViewModel gvm = invoiceInventoryService.findGameById(21);

    }

    private void setUpConsoleDaoMock() {

        consoleDao = mock(ConsoleDaoJdbcTemplateImpl.class);

        Consoles consoles = new Consoles();
        consoles.setConsoleId(38);
        consoles.setModel("Playstation 4");
        consoles.setManufacturer("Sony");
        consoles.setMemoryAmount("8GB");
        consoles.setProcessor("Jaguar CPU");
        consoles.setPrice(new BigDecimal("286.99"));
        consoles.setQuantity(26);

        Consoles consoles1 = new Consoles();
        consoles1.setModel("Playstation 4");
        consoles1.setManufacturer("Sony");
        consoles1.setMemoryAmount("8GB");
        consoles1.setProcessor("Jaguar CPU");
        consoles1.setPrice(new BigDecimal("286.99"));
        consoles1.setQuantity(26);

        // Second mock Console Object
        Consoles consoles2 = new Consoles();
        consoles2.setConsoleId(3);
        consoles2.setModel("Super Nintendo");
        consoles2.setManufacturer("Nintendo");
        consoles2.setMemoryAmount("128Mbit");
        consoles2.setProcessor("Ricoh 5A22");
        consoles2.setPrice(new BigDecimal("101.12"));
        consoles2.setQuantity(12);

        // Mock addConsole
        doReturn(consoles).when(consoleDao).addConsole(consoles1);

        // Mock getAllConsoles
        List<Consoles> consolesList = new ArrayList<>();
        consolesList.add(consoles);
        consolesList.add(consoles2);
        doReturn(consolesList).when(consoleDao).getAllConsoles();

        // Mock getConsole
        doReturn(consoles).when(consoleDao).getConsole(38);

        // update Mock data
        Consoles consolesUpdate = new Consoles();
        consolesUpdate.setConsoleId(17);
        consolesUpdate.setModel("Nintendo Switch");
        consolesUpdate.setManufacturer("Nintendo");
        consolesUpdate.setMemoryAmount("4GB");
        consolesUpdate.setProcessor("Quad-Core Cortex-A57");
        consolesUpdate.setPrice(new BigDecimal("304.98"));
        consolesUpdate.setQuantity(200);

        // Mock updateConsole
        doNothing().when(consoleDao).updateConsole(consolesUpdate);
        doReturn(consolesUpdate).when(consoleDao).getConsole(17);

        // Mock removeConsole
        doNothing().when(consoleDao).deleteConsole(200);
        doReturn(null).when(consoleDao).getConsole(200);

        // *** CUSTOM METHODS ***
        // Mock getConsolesByManufacturer();
        List<Consoles> consolesByManufacturerList = new ArrayList<>();
        consolesByManufacturerList.add(consoles);
        doReturn(consolesByManufacturerList).when(consoleDao).getConsolesByManufacturer("Sony");
    }

    @Test
    public void saveFindConsole() {

        ConsoleViewModel console = new ConsoleViewModel();

        console.setModel("Playstation 4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("8GB");
        console.setProcessor("Jaguar CPU");
        console.setPrice(new BigDecimal("286.99"));
        console.setQuantity(26);

        console = invoiceInventoryService.saveConsole(console);

        ConsoleViewModel fromService = invoiceInventoryService.findConsoleById(console.getConsoleId());
        assertEquals(fromService, console);

    }

    @Test
    public void findAllConsoles() {

        List<ConsoleViewModel> consoleList = invoiceInventoryService.findAllConsoles();
        assertEquals(consoleList.size(), 2);
    }

    @Test
    public void updateConsole() {

        ConsoleViewModel cvmUpdate = new ConsoleViewModel();

        Consoles updateConsole = new Consoles();
        updateConsole.setConsoleId(17);
        updateConsole.setModel("Nintendo Switch");
        updateConsole.setManufacturer("Nintendo");
        updateConsole.setMemoryAmount("4GB");
        updateConsole.setProcessor("Quad-Core Cortex-A57");
        updateConsole.setPrice(new BigDecimal("304.98"));
        updateConsole.setQuantity(200);

        cvmUpdate.setConsoleId(updateConsole.getConsoleId());
        cvmUpdate.setModel(updateConsole.getModel());
        cvmUpdate.setManufacturer(updateConsole.getManufacturer());
        cvmUpdate.setMemoryAmount(updateConsole.getMemoryAmount());
        cvmUpdate.setProcessor(updateConsole.getProcessor());
        cvmUpdate.setPrice(updateConsole.getPrice());
        cvmUpdate.setQuantity(updateConsole.getQuantity());

        invoiceInventoryService.updateConsole(cvmUpdate, 17);

        ConsoleViewModel afterUpdate = invoiceInventoryService.findConsoleById(cvmUpdate.getConsoleId());

        assertEquals(afterUpdate, cvmUpdate);

    }

    @Test(expected = NotFoundException.class)
    public void removeConsole() {

        invoiceInventoryService.removeConsole(200);

        ConsoleViewModel cvm = invoiceInventoryService.findConsoleById(200);

    }

    @Test(expected = NotFoundException.class)
    public void findConsolesByManufacturer() {

        List<ConsoleViewModel> consolesByManufacturerList = invoiceInventoryService.findConsolesByManufacturer("Sony");
        assertEquals(1, consolesByManufacturerList.size());

        consolesByManufacturerList = invoiceInventoryService.findConsolesByManufacturer("Atari");
        assertEquals(consolesByManufacturerList.size(), 0);
    }

    private void setUpTShirtDaoMock() {

        tShirtDao = mock(TShirtDaoJdbcTemplateImpl.class);

        TShirts tShirts = new TShirts();
        tShirts.setTShirtId(50);
        tShirts.setSize("XS");
        tShirts.setColor("purple");
        tShirts.setDescription("manga Eternal Sailor Moon looking at moon");
        tShirts.setPrice(new BigDecimal("14.99"));
        tShirts.setQuantity(21);

        TShirts tShirts1 = new TShirts();
        tShirts1.setColor("purple");
        tShirts1.setSize("XS");
        tShirts1.setDescription("manga Eternal Sailor Moon looking at moon");
        tShirts1.setPrice(new BigDecimal("14.99"));
        tShirts1.setQuantity(21);

        // Second mock TShirt Object
        TShirts tShirts2 = new TShirts();
        tShirts2.setTShirtId(51);
        tShirts2.setSize("XS");
        tShirts2.setColor("green");
        tShirts2.setDescription("manga Super Sailor Moon with inner senshi");
        tShirts2.setPrice(new BigDecimal("11.99"));
        tShirts2.setQuantity(11);

        TShirts tShirts2A = new TShirts();
        tShirts2A.setSize("XS");
        tShirts2A.setColor("green");
        tShirts2A.setDescription("manga Super Sailor Moon with inner senshi");
        tShirts2A.setPrice(new BigDecimal("11.99"));
        tShirts2A.setQuantity(11);

        // Mock addTShirt
        doReturn(tShirts).when(tShirtDao).addTShirt(tShirts1);
        doReturn(tShirts2).when(tShirtDao).addTShirt(tShirts2A);

        // Mock getAllTShirts returns 1
        List<TShirts> tShirtsList = new ArrayList<>();
        tShirtsList.add(tShirts);
        doReturn(tShirtsList).when(tShirtDao).getAllTShirts();

        // Mock getGame() return game when gameDao.getGame(int id) is called
        doReturn(tShirts).when(tShirtDao).getTShirt(50);

        TShirts tShirtsUpdate = new TShirts();
        tShirtsUpdate.setTShirtId(52);
        tShirtsUpdate.setSize("S");
        tShirtsUpdate.setColor("white");
        tShirtsUpdate.setDescription("manga Mako Kino sitting on lawn");
        tShirtsUpdate.setPrice(new BigDecimal("15.99"));
        tShirtsUpdate.setQuantity(15);

        // Mock updateGame();
        doNothing().when(tShirtDao).updateTShirt(tShirtsUpdate);
        doReturn(tShirtsUpdate).when(tShirtDao).getTShirt(52);

        // Mock removeTShirt
        doNothing().when(tShirtDao).deleteTShirt(53);
        doReturn(null).when(tShirtDao).getTShirt(53);

        // *** CUSTOM METHODS ***
        // Mock getTShirtsByColor()
        List<TShirts> tShirtsByColorList = new ArrayList<>();
        tShirtsByColorList.add(tShirts);
        doReturn(tShirtsByColorList).when(tShirtDao).getTShirtsByColor("purple");

        // Mock getTShirtsBySize()
        List<TShirts> tShirtsBySizeList = new ArrayList<>();
        tShirtsBySizeList.add(tShirts);
        tShirtsBySizeList.add(tShirts2);
        doReturn(tShirtsBySizeList).when(tShirtDao).getTShirtsBySize("XS");

    }

    @Test
    public void saveFindTShirt() {

        TShirtViewModel tShirt = new TShirtViewModel();
        tShirt.setSize("XS");
        tShirt.setColor("purple");
        tShirt.setDescription("manga Eternal Sailor Moon looking at moon");
        tShirt.setPrice(new BigDecimal("14.99"));
        tShirt.setQuantity(21);

        tShirt = invoiceInventoryService.saveTShirt(tShirt);

        TShirtViewModel fromService = invoiceInventoryService.findTShirtById(tShirt.getTShirtId());
        assertEquals(fromService, tShirt);

    }

    @Test
    public  void findAllTShirts() {

        List<TShirtViewModel> tShirtList = invoiceInventoryService.findAllTShirts();
        assertEquals(1, tShirtList.size());

    }

    @Test
    public void updateTShirt() {

        TShirtViewModel tvmUpdate = new TShirtViewModel();

        TShirts tShirtsUpdate = new TShirts();
        tShirtsUpdate.setTShirtId(52);
        tShirtsUpdate.setSize("S");
        tShirtsUpdate.setColor("white");
        tShirtsUpdate.setDescription("manga Mako Kino sitting on lawn");
        tShirtsUpdate.setPrice(new BigDecimal("15.99"));
        tShirtsUpdate.setQuantity(15);

        tvmUpdate.setTShirtId(tShirtsUpdate.getTShirtId());
        tvmUpdate.setSize(tShirtsUpdate.getSize());
        tvmUpdate.setColor(tShirtsUpdate.getColor());
        tvmUpdate.setDescription(tShirtsUpdate.getDescription());
        tvmUpdate.setPrice(tShirtsUpdate.getPrice());
        tvmUpdate.setQuantity(tShirtsUpdate.getQuantity());

        invoiceInventoryService.updateTShirt(tvmUpdate, 52);

        TShirtViewModel afterUpdate = invoiceInventoryService.findTShirtById(tvmUpdate.getTShirtId());

        assertEquals(afterUpdate, tvmUpdate);

    }

    @Test(expected = NotFoundException.class)
    public void removeTShirt(){

        invoiceInventoryService.removeTShirt(53);

        TShirtViewModel tvm = invoiceInventoryService.findTShirtById(53);

    }

    @Test(expected = NotFoundException.class)
    public void findTShirtByColor() {

        List<TShirtViewModel> tShirtByColorList = invoiceInventoryService.findTShirtByColor("purple");
        assertEquals(1, tShirtByColorList.size());

        tShirtByColorList = invoiceInventoryService.findTShirtByColor("black");
        assertEquals(tShirtByColorList.size(), 0);
    }

    @Test(expected = NotFoundException.class)
    public void findTShirtsBySize() {

        List<TShirtViewModel> tShirtsBySizeList = invoiceInventoryService.findTShirtsBySize("XS");
        assertEquals(2, tShirtsBySizeList.size());

        tShirtsBySizeList = invoiceInventoryService.findTShirtsBySize("LG");
        assertEquals(tShirtsBySizeList.size(), 0);

    }

    private void setUpInvoiceDaoMock() {

        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);
        processingFeeDao = mock(ProcessingFeeDaoJdbcTemplateImpl.class);
        salesTaxRateDao = mock(SalesTaxRateDaoJdbcTemplateImpl.class);

        SalesTaxRate str = new SalesTaxRate();
        str.setState("WA");
        str.setRate(new BigDecimal("0.05"));

        ProcessingFee pf = new ProcessingFee();
        pf.setFee(new BigDecimal("1.98"));
        pf.setProductType("T-shirts");

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(70);
        invoice.setName("Bebe S. Dufresne");
        invoice.setStreet("Aloha Street");
        invoice.setCity("Seattle");
        invoice.setState("WA");
        invoice.setZipCode("98101");
        invoice.setItemType("T-shirts");
        invoice.setItemId(50);
        invoice.setUnitPrice(new BigDecimal("14.99"));
        invoice.setQuantity(2);
        invoice.setSubtotal(new BigDecimal("29.98"));
        invoice.setTax(new BigDecimal("1.50"));
        invoice.setProcessingFee(new BigDecimal("1.98"));
        invoice.setTotal(new BigDecimal("33.46"));

        Invoice invoice1 = new Invoice();
        invoice1.setName("Bebe S. Dufresne");
        invoice1.setStreet("Aloha Street");
        invoice1.setCity("Seattle");
        invoice1.setState("WA");
        invoice1.setZipCode("98101");
        invoice1.setItemType("T-shirts");
        invoice1.setItemId(50);
        invoice1.setUnitPrice(new BigDecimal("14.99"));
        invoice1.setQuantity(2);
        invoice1.setSubtotal(new BigDecimal("29.98"));
        invoice1.setTax(new BigDecimal("1.50"));
        invoice1.setProcessingFee(new BigDecimal("1.98"));
        invoice1.setTotal(new BigDecimal("33.46"));

        // Mock addInvoice
        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(str).when(salesTaxRateDao).getSalesTaxRate("WA");
        doReturn(pf).when(processingFeeDao).getProcessingFee("t-shirts");

        // Mock getAllInvoices
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        doReturn(invoiceList).when(invoiceDao).getAllInvoices();

        // Mock getInvoice
        doReturn(invoice).when(invoiceDao).getInvoice(70);

        // Mock removeInvoice
        doNothing().when(invoiceDao).deleteInvoice(71);
        doReturn(null).when(invoiceDao).getInvoice(71);

    }

    @Test
    public void saveFindInvoice() {

        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setName("Bebe S. Dufresne");
        invoice.setStreet("Aloha Street");
        invoice.setCity("Seattle");
        invoice.setState("WA");
        invoice.setZipCode("98101");
        invoice.setItemType("T-shirts");
        invoice.setItemId(50);
        invoice.setUnitPrice(new BigDecimal("14.99"));
        invoice.setQuantity(2);
        invoice.setSubtotal(new BigDecimal("29.98"));
        invoice.setTax(new BigDecimal("1.50"));
        invoice.setProcessingFee(new BigDecimal("1.98"));
        invoice.setTotal(new BigDecimal("33.46"));

        invoice = invoiceInventoryService.saveInvoice(invoice);

        InvoiceViewModel fromService = invoiceInventoryService.findInvoiceById(invoice.getInvoiceId());
        assertEquals(fromService, invoice);
    }

    @Test
    public void findAllInvoices() {

        List<InvoiceViewModel> invoiceList = invoiceInventoryService.findAllInvoices();
        assertEquals(1, invoiceList.size());

    }

    @Test(expected = NotFoundException.class)
    public void removeInvoice() {

        invoiceInventoryService.removeInvoice(71);

        InvoiceViewModel ivm = invoiceInventoryService.findInvoiceById(71);

        assertNull(ivm);
    }

}