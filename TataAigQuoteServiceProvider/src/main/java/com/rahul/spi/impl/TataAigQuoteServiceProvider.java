/*
 * To change this license header); choose License Headers in Project Properties.
 * To change this template file); choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi.impl;

import com.rahul.spi.QuoteServiceProvider;
import com.rahul.spi.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author in-rahul.khandelwal
 */
public class TataAigQuoteServiceProvider implements QuoteServiceProvider {

    @Override
    public String serviceName() {
        return "tata-aig";
    }
    
    @Override
    public JSONObject toPartnerRequest(JSONObject uiRequest) {
        String policySdate = Utility.getFormattedCurrentDate("yyyyMMdd");
        String policyEnddate = Utility.getOneYearLaterDate("yyyyMMdd");
        var memberDobs = uiRequest.getJSONArray("members");

        var partnerRequest = new JSONObject();
        partnerRequest.put("functionality", "groupmedicarequotation");
        partnerRequest.put("emp_code", "3954557");
        partnerRequest.put("quote_type", "calc");
        partnerRequest.put("quotation_no", uiRequest.get("quotation_id"));
        partnerRequest.put("sol_id", "AXIS003");
        partnerRequest.put("branch", "MUMBAIBANDRAWEST");
        partnerRequest.put("lead_id", "1001");
        partnerRequest.put("partbrrefid", "125311RTSS");
        partnerRequest.put("partner_name", "AXIS");
        partnerRequest.put("intermediary_name", "SANDEEP");
        partnerRequest.put("intermediary_code", "0030240001");
        partnerRequest.put("group_custid", "GRP123");
        partnerRequest.put("mobile_no", uiRequest.get("mobile"));
        partnerRequest.put("telphone_no", "");
        partnerRequest.put("email_id", uiRequest.get("email"));
        partnerRequest.put("product_code", uiRequest.get("product_code"));
        partnerRequest.put("policy_tenure", "1");
        partnerRequest.put("product_name", "Group MediCare");
        partnerRequest.put("btype_name", "New");
        partnerRequest.put("policy_sdate", policySdate);
        partnerRequest.put("policy_enddate", policyEnddate);
        partnerRequest.put("policy_type", "Individual");
        partnerRequest.put("plan_type", "Individual");
        partnerRequest.put("masterpolno", "0235032828");
        partnerRequest.put("plan_code", uiRequest.get("plan_code"));
        partnerRequest.put("cust_pincode", uiRequest.get("pin_code"));
        partnerRequest.put("gstin", "");
        partnerRequest.put("floater_si", String.valueOf(uiRequest.get("floater_sum_insured")));
        partnerRequest.put("partnerappno", "1000157732");
        partnerRequest.put("partnername", "AXIS");
        partnerRequest.put("autodebitflag", "N");
        partnerRequest.put("ab_cust_id", "");
        partnerRequest.put("ab_emp_id", "");
        partnerRequest.put("cust_name", "");
        partnerRequest.put("grp_name", "AXIS");
        partnerRequest.put("source", "AXIS");
        partnerRequest.put("insuredpersons", memberDobs.length());

        JSONArray members = new JSONArray();
        for (int i = 1; i <= memberDobs.length(); i++) {
            JSONObject memberData = new JSONObject();
            memberData.put("dob", Utility.reformatDate(((JSONObject) memberDobs.get(i - 1)).getString("birth_date"), "yyyy-MM-dd", "yyyyMMdd"));
            memberData.put("age", Utility.calculateAge(((JSONObject) memberDobs.get(i - 1)).getString("birth_date"), "yyyy-MM-dd"));
            memberData.put("suminsured", memberDobs.getJSONObject(i - 1).get("suminsured"));
            memberData.put("relation", memberDobs.getJSONObject(i - 1).get("relation"));
            memberData.put("medQs1", memberDobs.getJSONObject(i - 1).get("smokerStatus"));
            memberData.put("medQs2", memberDobs.getJSONObject(i - 1).get("diebatic"));
            memberData.put("medQs3", memberDobs.getJSONObject(i - 1).get("hereditry"));

            JSONObject member = new JSONObject();
            member.put("m" + i, memberData);

            members.put(member);
        }

        partnerRequest.put("members", members);

        return partnerRequest;
    }

    @Override
    public JSONArray toUiResponse(JSONObject partnerResponse) {
        var status = partnerResponse.getString("status").equals("1") ? "accepted" : "rejected";
        var data = partnerResponse.getJSONObject("data");
        
        JSONObject uiQuote = new JSONObject();
        uiQuote.put("status", status);
        uiQuote.put("partner_code", "tata_aig");
        uiQuote.put("product_code", data.get("product_code"));
        uiQuote.put("plan_code", data.get("plan_code"));
        uiQuote.put("quotation_id", data.get("quotation_no"));
        
        JSONArray premiums = new JSONArray();
        JSONObject premium = new JSONObject();
        premium.put("total", data.getDouble("totalpremium"));
        premium.put("net", data.getDouble("netpremium"));
        
        JSONArray taxes = new JSONArray();
        JSONObject igst = new JSONObject();
        igst.put("type", "igst");
        igst.put("value", data.getDouble("igst"));
        taxes.put(igst);
        JSONObject sgst = new JSONObject();
        igst.put("type", "sgst");
        igst.put("value", data.getDouble("sgst"));
        taxes.put(sgst);
        JSONObject cess = new JSONObject();
        igst.put("type", "cess");
        igst.put("value", data.getDouble("other_cess"));
        taxes.put(cess);
        premium.put("taxes", taxes);
        
        JSONObject frequency = new JSONObject();
        frequency.put("unit", "year");
        frequency.put("value", data.getInt("policy_tenure"));
        premium.put("frequency", frequency);
        
        premiums.put(premium);
        uiQuote.put("premium", premiums);
        
        JSONArray response = new JSONArray();
        response.put(uiQuote);
        
        return response;
    }

}
