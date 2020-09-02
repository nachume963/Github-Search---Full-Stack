package com.githubsearch.ex3.controller;


import com.githubsearch.ex3.beans.Admin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * this class controlling the login/logout request
 * @author Nachum Ehrlich
 * @see Admin
 */
@Controller
public class LoginController {

    //Name and password accepted - get values from application.properties
    @Value("${admin.user}")
    private String nameAccepted;
    @Value("${admin.password}")
    private String passwordAccepted;

    /**
     * Handles a site login request
     * @param admin - pair of name and password.
     * @param result - applied validator and adds to model building.
     * @param model - thymeleaf model
     * @param session - session request to update attribute to true
     * @return - thymeleaf html file of "login"(+ error message) in case of invalid name or password
     *              or redirect to "/"
     */
    @PostMapping("/login")
    public String login(@Valid Admin admin, BindingResult result, Model model, HttpSession session){
        //Checking name validity - Adds a new FieldError if invalid
        if (!admin.nameValidate(nameAccepted))
            result.addError(new FieldError("authenticationError", "adminName",
                    "invalid name"));
        //Checking password validity - Adds a new FieldError if invalid
        if (!admin.passwordValidate(passwordAccepted))
            result.addError(new FieldError("authenticationError", "password",
                    "invalid password"));

        if (result.hasErrors()) {
            return "login";
        }
        else {
            session.setAttribute("MY_SESSION_LOGIN", true);
            return "redirect:/";
        }
    }
    /**
     * Handles with a login request by method get
     * --Actually request ignored --
     * @return - redirect to "/"
     */
    @GetMapping("/login")
    public String loginGet(){
        return "redirect:/";
    }

    /**
     * Handles a site logout request
     * @param session - session request to update attribute to false
     * @return - redirect to "/"
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){

        session.setAttribute("MY_SESSION_LOGIN", false);
        return "redirect:/";
    }
}
