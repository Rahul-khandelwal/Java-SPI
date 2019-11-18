/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi.tester.service;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author in-rahul.khandelwal
 */
public class CustomUrlClassLoader extends URLClassLoader {
    
    public CustomUrlClassLoader(URL[] urls) {
        super(urls);
    }
    
    public void addNewUrl(URL url) {
        this.addURL(url);
    }
}
