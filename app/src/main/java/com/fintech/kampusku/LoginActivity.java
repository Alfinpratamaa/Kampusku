package com.fintech.kampusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.fintech.kampusku.databinding.ActivityLoginBinding;
import com.fintech.kampusku.helper.DbLoginHelper;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    DbLoginHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        databaseHelper= new DbLoginHelper(this);
        binding.tiUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Check if the input is not empty and hide the helper text
                if (charSequence.length() > 0) {
                    binding.tiUsernameLayout.setHelperText(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.tiPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Check if the input is not empty and hide the helper text
                if (charSequence.length() > 0) {
                    binding.tiPasswordLayout.setHelperText(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.tiUsername.getText().toString();
                String password = binding.tiPassword.getText().toString();

                if (username.isEmpty()) {
                    // mengatur stroke dan helper text username ketika text username kosong
                    binding.tiUsernameLayout.setBoxStrokeColor(getResources().getColor(R.color.error_red));
                    binding.tiUsernameLayout.setHelperText("Username tidak boleh kosong !");
                    return;
                }else if(password.isEmpty()) {
                    // mengatur stroke dan helper text password ketika text password kosong
                    binding.tiPasswordLayout.setHelperText("password tidak boleh kosong !");
                    binding.tiPasswordLayout.setBoxStrokeColor(getResources().getColor(R.color.error_red));
                    return;
                }else{
                    Boolean checkCredentials = databaseHelper.checkUsernamePassword(username,password);
                    if (checkCredentials == true){
                        Toast.makeText(LoginActivity.this, "login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        binding.tiUsername.setText("");
                        binding.tiPassword.setText("");
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "username atau password salah!", Toast.LENGTH_SHORT).show();
                    }
                }
                binding.tiUsernameLayout.setBoxStrokeColor(getResources().getColor(R.color.gray));
                binding.tiPasswordLayout.setBoxStrokeColor(getResources().getColor(R.color.gray));
            }
        });


        binding.buttonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });};
    };
