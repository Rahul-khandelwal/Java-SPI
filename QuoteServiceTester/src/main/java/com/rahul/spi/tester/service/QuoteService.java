/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi.tester.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author in-rahul.khandelwal
 */
@Service
public class QuoteService {
    
    private final RestTemplate restTemplate;

    public QuoteService() {
        this.restTemplate = new RestTemplate();
    }

    public String getQuotesForPartner(JSONObject uiRequest, String serviceProvider) {
        var uri = "http://localhost:4547/tata/v1/quote";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var serviceProviderInstance = QuoteServiceLoader.INSTANCE.getProvider(serviceProvider);
        var entity = new HttpEntity<String>(serviceProviderInstance.toPartnerRequest(uiRequest).toString(), headers);
        var response = new JSONObject(restTemplate.postForEntity(uri, entity, String.class).getBody());
        return serviceProviderInstance.toUiResponse(response).toString();
    }
    
    public void reload() {
        QuoteServiceLoader.INSTANCE.reloadServices();
    }
    
}
