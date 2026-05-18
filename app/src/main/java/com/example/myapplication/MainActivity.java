package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCancel = findViewById(R.id.btnCancel);

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        // Cek apakah user sudah login sebelumnya
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            pindahKeDashboard();
        }

        // Tombol Login
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validasi hardcode dan data kosong
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else if (username.equals("admin") && password.equals("admin123")) {
                // Simpan status login ke SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("username", username);
                editor.apply();

                Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                pindahKeDashboard();
            } else {
                Toast.makeText(MainActivity.this, "Username atau Password Salah!", Toast.LENGTH_SHORT).show();
            }
        });

        // Tombol Cancel (Hapus input teks)
        btnCancel.setOnClickListener(v -> {
            etUsername.setText("");
            etPassword.setText("");
        });
    }

    private void pindahKeDashboard() {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish(); // Menutup MainActivity agar tidak bisa kembali dengan tombol back
    }
}
