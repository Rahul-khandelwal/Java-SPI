/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi.tester.service;

import com.rahul.spi.QuoteServiceProvider;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 *
 * @author in-rahul.khandelwal
 */
public enum QuoteServiceLoader {

    INSTANCE;

    private ServiceLoader<QuoteServiceProvider> serviceLoader;
    private CustomUrlClassLoader classLoader;
    
    private final Map<String, QuoteServiceProvider> services;

    QuoteServiceLoader() {
        this.services = new HashMap<>();
        this.loadServices();
    }

    public void reloadServices() {
        this.serviceLoader.reload();
        
        if (this.classLoader != null) {
            try {
                this.classLoader.close();
            } catch (IOException ex) {
                // Ignore
            }
        }
        
        this.serviceLoader = null;
        this.services.clear();
        
        this.loadServices();
    }

    /**
     * This method - 
     * 1. Creates the custom class loader.  
     * 2. Adds the JAR file URLs to class loader.
     * 3. Instantiate the service loader using the class loader.
     * 4. Finds the services and caches them in map.
     */
    private void loadServices() {
        this.classLoader = new CustomUrlClassLoader(new URL[] {});
        
        try {
            File dir = new File(System.getProperty("user.dir") + "/../QuoteServiceImpls/");
            
            File[] flist = dir.listFiles((File file) -> file.getPath().toLowerCase().endsWith(".jar"));
            
            for (File file : flist) {
                this.classLoader.addNewUrl(file.toURI().toURL());
            }
        } catch (MalformedURLException ex) {
            System.out.println("Exception while loading JARs : " + ex.getMessage());
        }
        
        this.serviceLoader = ServiceLoader.load(QuoteServiceProvider.class, this.classLoader);
        
        var itr = this.serviceLoader.iterator();

        while (itr.hasNext()) {
            QuoteServiceProvider next = itr.next();
            this.services.put(next.serviceName(), next);
        }
    }

    public QuoteServiceProvider getProvider(String name) {
        return this.services.get(name);
    }

}
