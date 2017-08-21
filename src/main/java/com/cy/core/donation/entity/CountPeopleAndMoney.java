package com.cy.core.donation.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cha0res on 4/15/17.
 */
public class CountPeopleAndMoney {
    private String totalMoney;
    private String totalPeople;

    public String getTotalMoney() {
        if(StringUtils.isBlank(totalMoney)){
            totalMoney = "0.0";
        }else{
            totalMoney = String.format("%.2f", Double.parseDouble(totalMoney));
        }
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(String totalPeople) {
        this.totalPeople = totalPeople;
    }
}
