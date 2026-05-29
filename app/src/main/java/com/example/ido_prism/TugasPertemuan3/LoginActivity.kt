package com.example.ido_prism.TugasPertemuan3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ido_prism.InputEmailActivity
import com.example.ido_prism.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        binding.btnRegisterGmail.setOnClickListener {
            val intent = Intent(this, InputEmailActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ambil data dari SharedPreferences yang disimpan saat registrasi
            val registeredUsername = sharedPref.getString("registered_username", null)
            val registeredPassword = sharedPref.getString("registered_password", null)

            // Kondisi Login:
            // 1. username == password
            // 2. Sesuai dengan data SharedPreferences
            val isLegacyLogin = username == password && password.isNotEmpty()
            val isRegisteredLogin = username == registeredUsername && password == registeredPassword && registeredUsername != null

            if (isLegacyLogin || isRegisteredLogin) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                // Navigasi ke BaseActivity (Home)
                val intent = Intent(this, com.example.ido_prism.BaseActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Gagal Login")
                    .setMessage("Username atau password salah. Silahkan Coba Lagi")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}
