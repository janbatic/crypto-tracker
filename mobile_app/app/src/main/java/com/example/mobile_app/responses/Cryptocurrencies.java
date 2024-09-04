package com.example.mobile_app.responses;

import com.google.gson.annotations.SerializedName;

public class Cryptocurrencies {
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value	;
    @SerializedName("market_cap")
    private String market_cap;


    public String getMarket_cap() {
        return market_cap;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}