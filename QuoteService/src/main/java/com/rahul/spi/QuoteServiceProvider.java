/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author in-rahul.khandelwal
 */
public interface QuoteServiceProvider {
    
    public String serviceName();
    
    public JSONObject toPartnerRequest(JSONObject uiRequest);
    
    public JSONArray toUiResponse(JSONObject partnerResponse);
}
