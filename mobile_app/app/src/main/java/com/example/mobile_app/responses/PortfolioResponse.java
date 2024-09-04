package com.example.mobile_app.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PortfolioResponse {
    @SerializedName("status")
    private String Status;
    @SerializedName("current_portfolio_value")
    private Float CurrentPortfolioValue;
    @SerializedName("cryptocurrencies")
    List<PortfolioCryptocurrencies> Cryptocurrencies;

    public String getStatus() {
        return Status;
    }


    public Float getCurrentPortfolioValue() {
        return CurrentPortfolioValue;
    }

    public List<com.example.mobile_app.responses.PortfolioCryptocurrencies> getPortfolioCryptocurrencies() {
        return Cryptocurrencies;
    }

}