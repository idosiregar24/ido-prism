package com.example.ido_prism.TugasPertemuan4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ido_prism.databinding.ActivityDashboardGuruBinding

class DashboardGuruActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardGuruBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityDashboardGuruBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val judulDariMain = intent.getStringExtra("EXTRA_JUDUL")
        val deskripsiDariMain = intent.getStringExtra("EXTRA_DESKRIPSI")


        if (judulDariMain != null) {
            binding.tvGuruName.text = judulDariMain
        }
        if (deskripsiDariMain != null) {
            binding.tvGuruEmail.text = deskripsiDariMain
        }
    }
}