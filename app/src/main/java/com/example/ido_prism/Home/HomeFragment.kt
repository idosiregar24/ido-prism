package com.example.ido_prism.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ido_prism.TugasPertemuan2.GeometricActivity
import com.example.ido_prism.TugasPertemuan3.LoginActivity
import com.example.ido_prism.TugasPertemuan4.DashboardGuruActivity
import com.example.ido_prism.TugasPertemuan4.DashboardOrangTuaActivity
import com.example.ido_prism.TugasPertemuan6.WebViewBinaDesaActivity
import com.example.ido_prism.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.btnWebBinaDesa.setOnClickListener {
            navigateTo(WebViewBinaDesaActivity::class.java)
        }
    }

    private fun navigateTo(targetActivity: Class<*>) {
        val intent = Intent(requireContext(), targetActivity)
        startActivity(intent)
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin ingin keluar akun?")
            .setPositiveButton("Ya") { _, _ ->
                val sharedPreferences = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLogin", false)
                editor.apply()

                Snackbar.make(binding.root, "Permintaan Anda Disetujui", Snackbar.LENGTH_SHORT).show()

                Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }.also { intent ->
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}