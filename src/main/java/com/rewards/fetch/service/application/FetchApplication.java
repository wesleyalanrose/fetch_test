package com.rewards.fetch.service.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.rewards.fetch.service.controllers.DataController;

/**
 * The web app instantiator. 
 * 
 * @author Wesley Rose
 */
public class FetchApplication extends Application {

    private final Set<Object> singletons = new HashSet<>();   // Set of controllers

    /**
     * Default constructor for the GeoApplication.
     */
    public FetchApplication() {
        singletons.add(new DataController());
        System.out.println("Rewards system online");
    }

    /**
     * Overridden getSingletons method to return the set of controllers.
     */
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}