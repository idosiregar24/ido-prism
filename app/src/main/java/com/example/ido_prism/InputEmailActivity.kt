package com.example.ido_prism

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ido_prism.databinding.ActivityInputEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InputEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                showErrorDialog("Email tidak boleh kosong")
                return@setOnClickListener
            }

            if (!email.endsWith("@gmail.com")) {
                showErrorDialog("Email harus menggunakan domain @gmail.com")
                return@setOnClickListener
            }

            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("EXTRA_EMAIL", email)
            startActivity(intent)
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
