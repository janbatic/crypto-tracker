package com.example.mobile_app.responses;

import com.google.gson.annotations.SerializedName;

public class PortfolioCryptocurrencies {
    @SerializedName("cryptocurrency")
    private String cryptocurrency;
    @SerializedName("amount")
    private String amount	;


    public String getAmount() {
        return amount;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

}