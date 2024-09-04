package com.example.mobile_app.responses;

import com.google.gson.annotations.SerializedName;



public class LoginResponse {
    @SerializedName("status")
    private String Status;
    @SerializedName("token")
    private String Token;

    public String getStatus() {
        return Status;
    }

    public String getToken() {
        return Token;
    }

}
