package com.fintech.kampusku;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;import androidx.appcompat.app.AppCompatActivity;
import com.fintech.kampusku.helper.DbMahasiswaHelper;

public class DetailActivity extends AppCompatActivity {

    private DbMahasiswaHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DbMahasiswaHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            String npm = intent.getStringExtra("npm");

            // Fetch data dari db ( db_mahasiswa.db)
            Cursor cursor = dbHelper.getMahasiswaByNPM(npm);
            if (cursor.moveToFirst()) {
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String jenisKelamin = cursor.getString(cursor.getColumnIndex("jenis_kelamin"));
                String tanggalLahir = cursor.getString(cursor.getColumnIndex("tanggal_lahir"));
                String alamat = cursor.getString(cursor.getColumnIndex("alamat"));

                TextView npmTv = findViewById(R.id.detailNpmTv);
                TextView namaTv = findViewById(R.id.detailNamaTv);
                TextView jenisKelaminTv = findViewById(R.id.detailJenisKelaminTv);
                TextView tanggalLahirTv = findViewById(R.id.detailTanggalLahirTv);
                TextView alamatTv = findViewById(R.id.detailAlamatTv);


                //menampilkan data ke textview
                npmTv.setText(npm);
                namaTv.setText(nama);
                jenisKelaminTv.setText(jenisKelamin);
                tanggalLahirTv.setText(tanggalLahir);
                alamatTv.setText(alamat);
            }
        }
    }
}
