package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private EditText etNim, etNama, etProdi, etKelas, etAlamat, etEmail;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etProdi = findViewById(R.id.etProdi);
        etKelas = findViewById(R.id.etKelas);
        etAlamat = findViewById(R.id.etAlamat);
        etEmail = findViewById(R.id.etEmail);
        Button btnTambah = findViewById(R.id.btnTambah);
        Button btnLogout = findViewById(R.id.btnLogout);
        ListView listViewData = findViewById(R.id.listViewData);

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "raihan");
        tvWelcome.setText("Selamat Datang, " + username);

        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listViewData.setAdapter(adapter);

        btnTambah.setOnClickListener(v -> {
            String nim = etNim.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String prodi = etProdi.getText().toString().trim();
            String kelas = etKelas.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || kelas.isEmpty() || alamat.isEmpty() || email.isEmpty()) {
                Toast.makeText(DashboardActivity.this, "Semua form harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                String mahasiswaData = "NIM: " + nim + "\nNama: " + nama + "\nProdi: " + prodi +
                        "\nKelas: " + kelas + "\nAlamat: " + alamat + "\nEmail: " + email;

                dataList.add(mahasiswaData);
                adapter.notifyDataSetChanged();

                bersihkanForm();
                Toast.makeText(DashboardActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.clear();
            editor.apply();

            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void bersihkanForm() {
        etNim.setText("");
        etNama.setText("");
        etProdi.setText("");
        etKelas.setText("");
        etAlamat.setText("");
        etEmail.setText("");
    }
}
