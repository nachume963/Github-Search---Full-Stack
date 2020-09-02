package com.githubsearch.ex3.controller;

import java.util.List;

import com.githubsearch.ex3.beans.Admin;
import com.githubsearch.ex3.beans.Mode;
import com.githubsearch.ex3.beans.ModelAttributes;
import com.githubsearch.ex3.repo.User;
import com.githubsearch.ex3.repo.UserRepository;

import javax.servlet.http.HttpSession;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;


/**
 * this class controlling the user request
 * @author Nachum Ehrlich
 * @see Admin
 */
@Controller
public class UserController {

    //Saves the user's access status : SCOPE_SESSION
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Mode adminMode() {
        return new Mode();
    }
    @Resource(name = "adminMode")
    private Mode mode;

    //Saves the attributes that will be added to the thymeleaf model : SCOPE_SESSION
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ModelAttributes modelAttributes() {
        return new ModelAttributes();
    }
    @Resource(name = "modelAttributes")
    private ModelAttributes modelAttributes;

    //Holds access to DB
    @Autowired
    private UserRepository repository;

    /**
     * Getting all incoming on site for the first time.
     * Handles the main page of the site, for all requests.
     *
     * @param admin - empty, just for the "login" html/thymeleaf file.
     * @param model - thymeleaf model
     * @param session - session request to check attribute of access if true ot false
     * @return - thymeleaf html file of "login" in case of not allowed access
     *              or thymeleaf html file of "index" if access is OK
     */
    @RequestMapping("/")
    public String main(Admin admin, Model model, HttpSession session) {
        //get the access status from session attribute
        Boolean login = (Boolean) session.getAttribute("MY_SESSION_LOGIN");

        //if it's OK
        if(login!=null && login){
            //update access status = true
            mode.setMode(true);

            //add attribute from modelAttributes to thymeleaf model
            model.addAttribute("errorInput", modelAttributes.getErrorInput());
            model.addAttribute("userName", modelAttributes.getUserName());
            model.addAttribute("userFollowers", modelAttributes.getUserFollowers());
            /*
        **If there was no requirement in the exercise to use ajax, I would put history with thymeleaf like this:**
            //get the top 10 searchers
            model.addAttribute("users", repository.findFirst10ByOrderBySearchNumberDesc());
             */
            return "index";
        }
        else {
            mode.setMode(false);
            return "login";
        }
    }
    /**
     * Handles attempts to reach unwanted places
     * --Actually request ignored --
     * @return - redirect to "/"
     */
    @RequestMapping("/{try}")
    public String blocker(@PathVariable("try") String bullshit) {
        return "redirect:/";
    }

    /**
     * Handles a site search request - if the user exists in DB, increasing search number
     *                                 if the user not exists, add new user to DB
     * @param name - user name to search in github
     * @param model - thymeleaf model
     * @return - redirect to "/"
     */
    @PostMapping("/search")
    public String search(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model){
        //if access status = true
        if (mode.isMode()){
            try {
                //if the user exists in DB
                if (repository.existsByName(name)){
                    repository.getOne(repository.findByName(name).getId()).setSearchNumber();
                    //update DB
                    repository.flush();
                }
                // if the user not exists, add new user to DB
                else repository.save(new User(name));

                //set modelAttributes in user name
                modelAttributes.setUserName(repository.findByName(name).getName());
                //if user followers number greater than 0, set modelAttributes in user followers
                if(repository.findByName(name).getFollowersNumber() > 0)
                    modelAttributes.setUserFollowers(String.valueOf(repository.findByName(name).getFollowersNumber()));
            }
            //if search in github failed
            catch (Exception e){
                System.out.println(e);
                modelAttributes.setErrorInput("No user exists!!");
            }
        }
        return "redirect:/";
    }
    /**
     * Handles with a search request by method get
     * --Actually request ignored --
     * @return - redirect to "/"
     */
    @GetMapping("/search")
    public String searchGet(){
        return "redirect:/";
    }

    /**
     * Handles a clear history request
     * @param model - thymeleaf model
     * @return - redirect to "/"
     */
    @GetMapping("/clearHistory")
    public String clearHistory(Model model){
        //if access status = true
        if(mode.isMode())
        {
            //clear history
            repository.deleteAll();
        }
        return "redirect:/";
    }

    /**
     * Handles a get history request
     * @return - list of top 10 searchers, by JSOM
     */
    @GetMapping(value = "/getHistory")
    public @ResponseBody List<User> getHistory(){
        //if access status = true
        if(mode.isMode()){
            //get the top 10 searchers
            return repository.findFirst10ByOrderBySearchNumberDesc();
        }
        return null;
    }
}
