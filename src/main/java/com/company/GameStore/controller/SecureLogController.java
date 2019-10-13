package com.company.GameStore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SecureLogController {

    @RequestMapping(value = "/loggedIn", method = RequestMethod.GET)
    public String logIn(Principal principal) {

        return "Welcome to the Video Game Store " + principal.getName() + "! You are successfully logged in.";
    }

    @RequestMapping(value = "/allDone", method = RequestMethod.GET)
    public String logOutMessage(Principal principal) {
        return "Thank you " + principal.getName() + ", you have successfully logged out";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello, gaaaaame!!!";
    }

}
