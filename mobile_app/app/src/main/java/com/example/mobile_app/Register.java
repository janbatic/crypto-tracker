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


import com.example.mobile_app.databinding.ActivityRegisterBinding;
import com.example.mobile_app.responses.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private ApiInterface apiInterface;
    private EditText usernameField;
    private EditText passwordField;
    private EditText passwordAgainField;
    private EditText emailField;
    private Button signUpButton;
    SharedPreferences sharedPreferences;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        usernameField = findViewById(R.id.usernameField);
        emailField = findViewById(R.id.emailField);
        passwordAgainField = findViewById(R.id.passwordAgainField);
        passwordField = findViewById(R.id.passwordField);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setEnabled(false);
        usernameField.addTextChangedListener(RegisterWatcher);
        emailField.addTextChangedListener(RegisterWatcher);
        passwordAgainField.addTextChangedListener(PasswordWatcher);
        passwordField.addTextChangedListener(PasswordWatcher);

        binding.backButton.setOnClickListener(view -> {
            finish();
        });
        binding.signUpButton.setOnClickListener(view -> {
            String username = usernameField.getText().toString();
            String email = emailField.getText().toString();
            String passwordAgain = passwordAgainField.getText().toString();
            String password = passwordField.getText().toString();
            Call<LoginResponse> call = apiInterface.register(username, email, password, passwordAgain);
            call.enqueue(new Callback<LoginResponse>(){
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        accessToken = response.body().getToken();
                        sharedPreferences = getApplicationContext().getSharedPreferences("SecretsPref", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("access_token", accessToken).apply();
                        Log.e("ADebugTag", "token: " + sharedPreferences.getString("access_token", ""));
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);

                    }
                    else{
                        try {
                            Toast.makeText(Register.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.e("ADebugTag", "Error response: " + response);
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {

                }
            });
        });

    }
    TextWatcher RegisterWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            String username = usernameField.getText().toString();
            String email = emailField.getText().toString();
            String passwordAgain = passwordAgainField.getText().toString();
            String password = passwordField.getText().toString();

            signUpButton.setEnabled(!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !passwordAgain.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
        }
    };
    TextWatcher PasswordWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            String username = usernameField.getText().toString();
            String email = emailField.getText().toString();
            String passwordAgain = passwordAgainField.getText().toString();
            String password = passwordField.getText().toString();

            signUpButton.setEnabled(!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !passwordAgain.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            String passwordAgain = passwordAgainField.getText().toString();
            String password = passwordField.getText().toString();
            if(password.length() < 8){
                passwordField.setError("Passwords is to short at least 8 characters needed");
                signUpButton.setEnabled(false);
            }
            else if (!password.equals(passwordAgain)){
            passwordAgainField.setError("This fields do not match");
            passwordField.setError("This fields do not match");
            signUpButton.setEnabled(false);
        }else{
                passwordAgainField.setError(null);
                passwordField.setError(null);
                signUpButton.setEnabled(true);
            }
        }
    };

}