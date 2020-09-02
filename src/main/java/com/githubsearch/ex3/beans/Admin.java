package com.githubsearch.ex3.beans;

/**
 * Represents an pair of name and password
 * @author Nachum Ehrlich
 */
public class Admin {

    private String adminName;
    private String password;

    //validate name and password
    public Boolean nameValidate(String nameAccepted) {
        return (adminName.equals(nameAccepted));
    }
    public Boolean passwordValidate(String passwordAccepted) {
        return (password.equals(passwordAccepted));
    }

    //getter's and setter's
    public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
