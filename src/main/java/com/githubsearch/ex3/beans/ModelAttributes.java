package com.githubsearch.ex3.beans;

import org.springframework.beans.factory.annotation.Value;

/**
 * Contains attributes that will be added to the thymeleaf model
 * the scope of this Bean will be "SESSION"
 * @author Nachum Ehrlich
 */
public class ModelAttributes {

    //Initial values
    @Value("false")
    private String errorInput;
    @Value("false")
    private String userName;
    @Value("Has no followers")
    private String userFollowers;

    //getter's and setter's
    public String getUserName() {
        //In case of a recurring request, the default value will be entered
        String temp = userName;
        userName = "false";
        return temp;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserFollowers() {
        //In case of a recurring request, the default value will be entered
        String temp = userFollowers;
        userFollowers = "Has no followers";
        return temp;
    }
    public void setUserFollowers(String userFollowers) {
        this.userFollowers = userFollowers;
    }
    public String getErrorInput() {
        //In case of a recurring request, the default value will be entered
        String temp = errorInput;
        errorInput = "false";
        return temp;
    }
    public void setErrorInput(String errorInput) {
        this.errorInput = errorInput;
    }
}
