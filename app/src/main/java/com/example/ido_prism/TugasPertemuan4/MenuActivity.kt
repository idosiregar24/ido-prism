package com.example.ido_prism.TugasPertemuan4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ido_prism.TugasPertemuan2.GeometricActivity
import com.example.ido_prism.TugasPertemuan3.LoginActivity
import com.example.ido_prism.databinding.ActivityMenuBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    // Simpan data sebagai properti kelas agar mudah diakses oleh fungsi lain
    private lateinit var judulUtama: String
    private val deskripsiUtama: String = "Platform edukasi dan pemantauan perkembangan anak berkebutuhan khusus."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME") ?: "Pengguna"
        judulUtama = "Selamat Datang, $username"

        binding.tvDashboardTitle.text = judulUtama
        binding.tvDashboardDesc.text = deskripsiUtama

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnBangunRuang.setOnClickListener {
            navigateTo(GeometricActivity::class.java)
        }

        binding.btnDashboardGuru.setOnClickListener {
            navigateTo(DashboardGuruActivity::class.java)
        }

        binding.btnDashboardOrangTua.setOnClickListener {
          navigateTo(DashboardOrangTuaActivity::class.java)
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    /**
     * Fungsi reusable untuk menangani perpindahan halaman beserta datanya.
     * Menggunakan Kotlin Scope Functions (apply & also) agar penulisan lebih ringkas.
     */
    private fun navigateTo(targetActivity: Class<*>) {
        Intent(this, targetActivity).apply {
            putExtra("EXTRA_JUDUL", judulUtama)
            putExtra("EXTRA_DESKRIPSI", deskripsiUtama)
        }.also { intent ->
            startActivity(intent)
        }
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin ingin keluar akun?")
            .setPositiveButton("Ya") { _, _ ->
                Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }.also { intent ->
                    startActivity(intent)
                    finish()
                }
            }
            .setNegativeButton("Tidak") { _, _ ->
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT)
                    .setAction("Tutup") {
                        Log.e("Info Snackbar", "Snackbar ditutup")
                    }
                    .show()
            }
            .show()
    }
}