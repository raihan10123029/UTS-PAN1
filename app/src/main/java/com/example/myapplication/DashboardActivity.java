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

        // Inisialisasi komponen UI
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

        // Mengambil nama user dari SharedPreferences untuk ucapan selamat datang
        String username = sharedPreferences.getString("username", "User");
        tvWelcome.setText("Selamat Datang, " + username);

        // Setup ListView dan Adapter
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listViewData.setAdapter(adapter);

        // Aksi tombol Tambah Data
        btnTambah.setOnClickListener(v -> {
            String nim = etNim.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String prodi = etProdi.getText().toString().trim();
            String kelas = etKelas.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            // Validasi ringkas pastikan semua form terisi
            if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || kelas.isEmpty() || alamat.isEmpty() || email.isEmpty()) {
                Toast.makeText(DashboardActivity.this, "Semua form harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                // Menggabungkan data menjadi satu teks tampilan rapi
                String mahasiswaData = "NIM: " + nim + "\nNama: " + nama + "\nProdi: " + prodi +
                        "\nKelas: " + kelas + "\nAlamat: " + alamat + "\nEmail: " + email;

                // Tambah ke list dan perbarui adapter ListView
                dataList.add(mahasiswaData);
                adapter.notifyDataSetChanged();

                // Bersihkan formulir setelah sukses input
                bersihkanForm();
                Toast.makeText(DashboardActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        // Aksi tombol Logout
        btnLogout.setOnClickListener(v -> {
            // Hapus data sesi / ubah status login
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.clear(); // Opsional: jika ingin menghapus seluruh key didalamnya
            editor.apply();

            // Kembali ke Halaman Login
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
