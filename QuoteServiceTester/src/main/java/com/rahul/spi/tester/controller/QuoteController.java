/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi.tester.controller;

import com.rahul.spi.tester.service.QuoteService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author in-rahul.khandelwal
 */
@RestController
public class QuoteController {
    
    @Autowired
    public QuoteService service;
    
    @PostMapping(path = "/quotes/{partner}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String returnQuotesForTataAig(@PathVariable("partner") String partner, @RequestBody String request) {
        return service.getQuotesForPartner(new JSONObject(request), partner);
    }
    
    @PostMapping(path = "/quotes/reload", produces = MediaType.APPLICATION_JSON_VALUE)
    public String reloadQuoteServices() {
        service.reload();
        return "\"status\" : \"success\"";
    }
}
