package com.example.ido_prism.TugasPertemuan4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ido_prism.databinding.ActivityDashboardOrangTuaBinding

class DashboardOrangTuaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardOrangTuaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardOrangTuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val judulDariMain = intent.getStringExtra("EXTRA_JUDUL")
        val deskripsiDariMain = intent.getStringExtra("EXTRA_DESKRIPSI")

        // PERBAIKAN: Arahkan ke TextView yang baru kita buat di XML
        if (judulDariMain != null) {
            binding.tvImportedTitle.text = judulDariMain
        }
        if (deskripsiDariMain != null) {
            binding.tvImportedDesc.text = deskripsiDariMain
        }
    }
}