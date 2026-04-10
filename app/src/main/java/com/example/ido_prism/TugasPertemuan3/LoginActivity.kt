package com.example.ido_prism.TugasPertemuan3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ido_prism.TugasPertemuan4.MenuActivity
import com.example.ido_prism.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("USERNAME", username)

            startActivity(intent)
        }
    }
}
