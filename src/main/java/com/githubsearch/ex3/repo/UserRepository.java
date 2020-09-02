package com.githubsearch.ex3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * this interface dealing with special queries to the DB
 * @author Nachum Ehrlich
 * @see User
 */
public interface UserRepository extends JpaRepository<com.githubsearch.ex3.repo.User, Long> {

    //get user object from DB by user name
    com.githubsearch.ex3.repo.User findByName(String name);
    //check if user exists by user name
    boolean existsByName(String name);
    //find the First 10 By Search Number, By Order.
    List<com.githubsearch.ex3.repo.User> findFirst10ByOrderBySearchNumberDesc();

}

