package com.example.mobile_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.mobile_app.databinding.ActivityTransactionBinding;
import com.example.mobile_app.responses.LoginResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaction extends AppCompatActivity {
    ActivityTransactionBinding binding;
    private EditText amountField;
    private ApiInterface apiInterface;
    private TextView cryptoName;
    private TextView errorMessage;
    private Button buyButton;
    private Button sellButton;
    private String accessToken;
    private String cryptoNameValue;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        amountField = findViewById(R.id.amountField);
        cryptoName = findViewById(R.id.cryptoName);
        buyButton = findViewById(R.id.buyButton);
        sellButton = findViewById(R.id.sellButton);
        errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setText("");


        Intent intent = getIntent();
        cryptoNameValue = intent.getStringExtra("cryptoName");
        cryptoName.setText(cryptoNameValue);

        binding.backButton.setOnClickListener(view -> {
            finish();
        });
        sharedPreferences = getApplicationContext().getSharedPreferences("SecretsPref", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");
        if(accessToken.isEmpty()){
            buyButton.setEnabled(false);
            sellButton.setEnabled(false);
            errorMessage.setText("You are not logged in");
        }
        binding.buyButton.setOnClickListener(view -> {
            postData("buy");
        });
        binding.sellButton.setOnClickListener(view -> {
            postData("sell");
        });

    }
    private void postData(String transaction_type) {
            String amount = amountField.getText().toString();
            Call<Void> call = apiInterface.transaction(accessToken, cryptoNameValue, amount, transaction_type);
            call.enqueue(new Callback<Void>(){
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.i("ADebugTag", "response: " + response);

                    if (response.isSuccessful()) {
                        finish();

                    }
                    else{
                        try {
                            Toast.makeText(Transaction.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.i("ADebugTag", "response: " + response);
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    Toast.makeText(Transaction.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });
    }
}