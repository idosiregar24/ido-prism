package com.example.ido_prism

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ido_prism.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil email dari intent jika ada
        val emailFromGmail = intent.getStringExtra("EXTRA_EMAIL")
        if (emailFromGmail != null) {
            binding.etEmail.setText(emailFromGmail)
            // Field email di-disable karena sudah divalidasi dari halaman sebelumnya
            binding.etEmail.isEnabled = false
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etFullName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registrasi Berhasil untuk $name", Toast.LENGTH_SHORT).show()
                // Logika navigasi setelah registrasi bisa ditambahkan di sini
                finish()
            }
        }
    }
}
