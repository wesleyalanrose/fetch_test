package com.rewards.fetch.service.application;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


public class FetchApplication extends Application {

    private final Set<Object> singletons = new HashSet<>();   // Set of controllers

    /**
     * Default constructor for the GeoApplication.
     */
    public FetchApplication() {

    }

    /**
     * Overridden getSingletons method to return the set of controllers.
     */
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}