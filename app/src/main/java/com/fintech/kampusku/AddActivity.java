package com.fintech.kampusku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fintech.kampusku.databinding.ActivityAddBinding;
import com.fintech.kampusku.helper.DbMahasiswaHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;
    private SimpleDateFormat dateFormatter;
    DbMahasiswaHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DbMahasiswaHelper(this);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        // Other initialization code
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }


    private void insertData() {
        try {
            String npm = binding.tiNpm.getText().toString();
            String nama = binding.itNama.getText().toString();
            String jenisKelamin = binding.rbL.isChecked() ? "Laki-laki" : "Perempuan";
            String tanggalLahir = binding.tiTanggalLahir.getText().toString();
            String alamat = binding.tiAlamat.getText().toString();

            // check inputan jika ada text kosong
            if (npm.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || tanggalLahir.isEmpty() || alamat.isEmpty()) {
                Toast.makeText(this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
                return; // mengembalikan tanpa melakukakan apapun
            }

            long insertResult = dbHelper.insertMahasiswaData(npm, nama, jenisKelamin, tanggalLahir, alamat);

            if (insertResult != -1) {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();

                // hapus inputan text ketika berhasil

                binding.tiNpm.getText().clear();
                binding.itNama.getText().clear();
                binding.rbGroup.clearCheck();
                binding.tiTanggalLahir.getText().clear();
                binding.tiAlamat.getText().clear();
            } else {
                Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print exception details to log
            Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void showDatePicker(View view) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datepicker, year, monthOfYear, dayOfMonth) -> {
                    newCalendar.set(year, monthOfYear, dayOfMonth);
                    binding.tiTanggalLahir.setText(dateFormatter.format(newCalendar.getTime()));
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }


    // Add other methods and logic as needed
}
