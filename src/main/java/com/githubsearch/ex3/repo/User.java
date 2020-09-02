package com.githubsearch.ex3.repo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


/**
 * Represents an Ratio in Data Base
 * @author Nachum Ehrlich
 * @see UserRepository
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is invalid")
    private String name;
    private String url;
    private int followersNumber;
    private long searchNumber;

    /**
     * Constructor.
     * @param name - user name, get from search
     * @throws JSONException if JSONObject failed
     */
    public User(String name) throws JSONException {
        //The RestTemplate offers templates for common scenarios by HTTP method
        RestTemplate restTemplate = new RestTemplate();

        //Reads data from the Github site to String and than convert to JSONObject
        String resp = restTemplate.getForObject("https://api.github.com/users/" + name, String.class);
        JSONObject response = new JSONObject(resp);

        //get the details from response
        this.name = response.getString("login");
        this.url = response.getString("html_url");
        this.followersNumber = response.getInt("followers");
        //Initial value = 1
        this.searchNumber = 1;
    }

    /**
     * Counts the number of searches, increasing by onw in each additional search
     */
    public void setSearchNumber() {
        this.searchNumber++;
    }
}

