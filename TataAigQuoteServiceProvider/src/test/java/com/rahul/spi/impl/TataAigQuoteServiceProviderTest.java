/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author in-rahul.khandelwal
 */
public class TataAigQuoteServiceProviderTest {

    @Test
    public void benchmark() {
        JSONObject input = new JSONObject();
        input.put("name", "John Doe");
        input.put("gender", "Male");
        input.put("dob", Date.from(LocalDate.of(1988, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        input.put("phone", "1234566541");
        input.put("isSmoker", false);
        input.put("salary", 600000);
        input.put("products", new JSONArray(new String[]{"LI", "GI"}));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        var renderedValues = new ArrayList<String>();
        var startTime = System.currentTimeMillis();

        for (int i = 0 ; i < 100000; i++) {
            renderedValues.add(this.sampleBenchmarkTask(input, simpleDateFormat));
        }

        var endTime = System.currentTimeMillis();
        System.out.println("Milliseconds taken to run the test -> " +  (endTime - startTime));
        Assertions.assertTrue(renderedValues.size() >= 100000);
    }

    private String sampleBenchmarkTask(JSONObject input, DateFormat dateFormat) {
        JSONObject output = new JSONObject();

        output.put("name", input.get("name"));
        output.put("ph", input.get("phone"));
        output.put("dob", dateFormat.format((Date) input.get("dob")));
        output.put("gender", input.get("gender"));
        output.put("isSmoker", input.get("isSmoker"));
        output.put("yearlyIncome", input.get("salary"));
        output.put("products", input.get("products"));

        return output.toString();
    }

}
