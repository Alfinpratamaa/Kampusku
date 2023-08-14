package com.fintech.kampusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.fintech.kampusku.databinding.ActivityRegisterBinding;
import com.fintech.kampusku.helper.DbLoginHelper;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    DbLoginHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize helper
        databaseHelper = new DbLoginHelper(this);
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
        binding.tiConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Check if the input is not empty and hide the helper text
                if (charSequence.length() > 0) {
                    binding.tiConfirmPasswordLayout.setHelperText(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.tiUsername.getText().toString();
                String password = binding.tiPassword.getText().toString();
                String confirmPassword = binding.tiConfirmPassword.getText().toString();

                if (username.isEmpty()) {

                    binding.tiUsernameLayout.setBoxStrokeColor(getResources().getColor(R.color.error_red));
                    binding.tiUsernameLayout.setHelperText("Username tidak boleh kosong !");
                    return;
                }else if(password.isEmpty()) {
                    binding.tiPasswordLayout.setHelperText("password tidak boleh kosong !");
                    binding.tiPasswordLayout.setBoxStrokeColor(getResources().getColor(R.color.error_red));
                    return;
                }else if(confirmPassword.isEmpty()){
                    binding.tiConfirmPasswordLayout.setBoxStrokeColor(getResources().getColor(R.color.error_red));
                    binding.tiConfirmPasswordLayout.setHelperText("password tidak boleh kosong !");
                    return;
                }else {
                    if (password.equals(confirmPassword)){
                        Boolean checkUser = databaseHelper.checkUser(username);
                        binding.tiPasswordLayout.setHelperText(null);
                        binding.tiConfirmPasswordLayout.setHelperText(null);
                        if(checkUser == false){
                            Boolean insert = databaseHelper.insertDataUsers(username,password);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "Register berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Register gagal", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "username sudah dipakai", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        binding.tiPasswordLayout.setHelperText("pasword tidak cocok");
                        binding.tiConfirmPasswordLayout.setHelperText("pasword tidak cocok");
                    }
                }
                binding.tiUsernameLayout.setBoxStrokeColor(getResources().getColor(R.color.gray));
                binding.tiPasswordLayout.setBoxStrokeColor(getResources().getColor(R.color.gray));
                binding.tiConfirmPasswordLayout.setBoxStrokeColor(getResources().getColor(R.color.gray));
            }
        });

        binding.buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}