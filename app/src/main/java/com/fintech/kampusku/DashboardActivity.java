package com.fintech.kampusku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fintech.kampusku.adapter.Adapter;
import com.fintech.kampusku.databinding.ActivityDashboardBinding;
import com.fintech.kampusku.helper.DbMahasiswaHelper;

public class DashboardActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT = 1;

    private ActivityDashboardBinding binding;
    private Adapter mahasiswaAdapter;
    private DbMahasiswaHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DbMahasiswaHelper(this);
        mahasiswaAdapter = new Adapter();

        binding.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, InformationActivity.class));
            }
        });

        // Menggunakan OnItemClickListener untuk RecyclerView
        mahasiswaAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(String npm) {
                Intent intent = new Intent(DashboardActivity.this, DetailActivity.class);
                intent.putExtra("npm", npm); // Pass the clicked item's npm to DetailActivity
                startActivity(intent);
            }

            @Override
            public void onEditClick(String npm) {
                Intent intent = new Intent(DashboardActivity.this, EditActivity.class);
                intent.putExtra("npm", npm); // Pass the clicked item's npm to DetailActivity
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(String npm) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int rowsDeleted = dbHelper.deleteMahasiswaData(npm);
                        if (rowsDeleted > 0) {
                            // Data berhasil dihapus, refresh tampilan RecyclerView
                            Toast.makeText(DashboardActivity.this, "berhasil menghapus data", Toast.LENGTH_SHORT).show();
                            loadMahasiswaData();
                        } else {
                            // Gagal menghapus data
                            Log.d("DashboardActivity", "Failed to delete data with npm: " + npm);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Batal menghapus data
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
            }
        });

        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleview.setAdapter(mahasiswaAdapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, AddActivity.class));
            }
        });

        loadMahasiswaData();
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadMahasiswaData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            loadMahasiswaData();
        }
    }

    private void loadMahasiswaData() {
        Cursor cursor = dbHelper.getAllMahasiswaData();
        mahasiswaAdapter.swapCursor(cursor);
        Log.d("DashboardActivity", "Load Mahasiswa Data: " + cursor.getCount() + " rows loaded");
    }
}
