package com.example.mobile_app;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.mobile_app.databinding.ActivityLoginBinding;
import com.example.mobile_app.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    private ApiInterface apiInterface;
    private EditText usernameField;
    private EditText passwordField;
    private Button signInButton;
    SharedPreferences sharedPreferences;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setEnabled(false);
        usernameField.addTextChangedListener(LoginWatcher);
        passwordField.addTextChangedListener(LoginWatcher);
        binding.signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });
        binding.backButton.setOnClickListener(view -> {
            finish();
        });
        binding.signInButton.setOnClickListener(view -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            Call<LoginResponse> call = apiInterface.login(username, password);
            call.enqueue(new Callback<LoginResponse>(){
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        accessToken = response.body().getToken();
                        sharedPreferences = getApplicationContext().getSharedPreferences("SecretsPref", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("access_token", accessToken).apply();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {

                }
            });
        });

    }
    TextWatcher LoginWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            signInButton.setEnabled(!username.isEmpty() && !password.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
        }
    };

}