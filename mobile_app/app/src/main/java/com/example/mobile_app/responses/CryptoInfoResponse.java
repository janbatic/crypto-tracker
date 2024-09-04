package com.example.mobile_app.responses;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CryptoInfoResponse {
    @SerializedName("status")
    private String Status;
    @SerializedName("page")
    private Integer Page;
    @SerializedName("num_pages")
    private Integer NumPages;
    @SerializedName("crypto_info")
    List<Cryptocurrencies> Cryptocurrencies;

    public String getStatus() {
        return Status;
    }

    public Integer getPage() {
        return Page;
    }

    public Integer getNumPages() {
        return NumPages;
    }

    public List<com.example.mobile_app.responses.Cryptocurrencies> getCryptocurrencies() {
        return Cryptocurrencies;
    }

}