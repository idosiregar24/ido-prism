package com.example.ido_prism

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ido_prism.TugasPertemuan3.LoginActivity
import com.example.ido_prism.databinding.ActivityRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
            val name = binding.etFullName.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // 1. Semua field wajib diisi
            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showErrorDialog("Semua field wajib diisi!")
                return@setOnClickListener
            }

            // 2. Username tidak boleh mengandung spasi
            if (username.contains(" ")) {
                showErrorDialog("Username tidak boleh mengandung spasi!")
                return@setOnClickListener
            }

            // 3. Password minimal 6 karakter
            if (password.length < 6) {
                showErrorDialog("Password minimal harus 6 karakter!")
                return@setOnClickListener
            }

            // Simpan data registrasi ke SharedPreferences
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("registered_username", username)
            editor.putString("registered_password", password)
            editor.apply()

            // Jika semua valid
            Toast.makeText(this, "Registrasi Berhasil untuk $username", Toast.LENGTH_SHORT).show()
            
            // Arahkan ke halaman Login dan hapus activity sebelumnya dari stack
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Validasi Gagal")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
