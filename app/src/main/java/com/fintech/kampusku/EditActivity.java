package com.fintech.kampusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fintech.kampusku.helper.DbMahasiswaHelper;
import com.google.android.material.textfield.TextInputEditText;

public class EditActivity extends AppCompatActivity {

    DbMahasiswaHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DbMahasiswaHelper(this);

        TextInputEditText tiNpm = findViewById(R.id.tiNpm);
        TextInputEditText tiNama = findViewById(R.id.itNama);
        RadioGroup rbGroup = findViewById(R.id.rbGroup);
        TextInputEditText tiTanggalLahir = findViewById(R.id.tiTanggalLahir);
        TextInputEditText tiAlamat = findViewById(R.id.tiAlamat);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);
        ImageButton btnBack = findViewById(R.id.backBtn);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String npm = intent.getStringExtra("npm");

            // fetch data dari db
            Cursor cursor = dbHelper.getMahasiswaByNPM(npm);
            if (cursor != null && cursor.moveToFirst()) {
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String jenisKelamin = cursor.getString(cursor.getColumnIndex("jenis_kelamin"));
                String tanggalLahir = cursor.getString(cursor.getColumnIndex("tanggal_lahir"));
                String alamat = cursor.getString(cursor.getColumnIndex("alamat"));

                tiNpm.setText(npm);
                tiNama.setText(nama);

                if ("laki-laki".equals(jenisKelamin)) {
                    rbGroup.check(R.id.rbL);
                } else {
                    rbGroup.check(R.id.rbP);
                }

                tiTanggalLahir.setText(tanggalLahir);
                tiAlamat.setText(alamat);

                cursor.close();
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil input dari komponen UI
                String updatedNpm = tiNpm.getText().toString();
                String updatedNama = tiNama.getText().toString();
                String updatedJenisKelamin = rbGroup.getCheckedRadioButtonId() == R.id.rbL ? "laki-laki" : "Perempuan";
                String updatedTanggalLahir = tiTanggalLahir.getText().toString();
                String updatedAlamat = tiAlamat.getText().toString();

                // check inputan jika ada yg kosong
                if (updatedNpm.isEmpty() || updatedNama.isEmpty() || updatedTanggalLahir.isEmpty() || updatedAlamat.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    int updateData = dbHelper.updateMahasiswaData(updatedNpm, updatedNama, updatedJenisKelamin, updatedTanggalLahir, updatedAlamat);

                    if (updateData > 0) {
                        // Data berhasil diubah
                        Toast.makeText(EditActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();

                        // mengirimkan data yg dibuah ke dashboardActivity
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("npm", updatedNpm);
                        resultIntent.putExtra("nama", updatedNama);
                        resultIntent.putExtra("jenisKelamin", updatedJenisKelamin);
                        resultIntent.putExtra("tanggalLahir", updatedTanggalLahir);
                        resultIntent.putExtra("alamat", updatedAlamat);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        // kondisi jika tidak ada data yang diubah
                        Toast.makeText(EditActivity.this, "No data updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(EditActivity.this, "An error occurred while updating data", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tidak jadi edit
                finish();
            }
        });
    }

}
