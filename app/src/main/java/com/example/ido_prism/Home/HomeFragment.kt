package com.example.ido_prism.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ido_prism.Home.photo.PhotoAdapter
import com.example.ido_prism.Home.photo.PopularNewsAdapter
import com.example.ido_prism.TugasPertemuan2.GeometricActivity
import com.example.ido_prism.TugasPertemuan3.LoginActivity
import com.example.ido_prism.TugasPertemuan4.DashboardGuruActivity
import com.example.ido_prism.TugasPertemuan4.DashboardOrangTuaActivity
import com.example.ido_prism.TugasPertemuan6.WebViewBinaDesaActivity
import com.example.ido_prism.data.api.PhotoApiClient
import com.example.ido_prism.data.model.NewsItem
import com.example.ido_prism.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var allNewsItems: List<NewsItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayUsername()
        setupClickListeners()
        setupChipFilter()
        loadNews()
    }

    private fun setupChipFilter() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val selectedId = checkedIds.firstOrNull()
            val category = when (selectedId) {
                binding.chipInformasi.id -> "Informasi"
                binding.chipPenting.id -> "Penting"
                binding.chipPengumuman.id -> "Pengumuman"
                else -> "Semua"
            }
            filterNews(category)
        }
    }

    private fun filterNews(category: String) {
        val filteredList = if (category == "Semua") {
            allNewsItems
        } else {
            allNewsItems.filter { it.category == category }
        }
        
        binding.rvGallery.adapter = PhotoAdapter(filteredList)
    }

    private fun loadNews() {
        lifecycleScope.launch {
            try {
                // 1. Ambil data dari API (Picsum untuk gambar, JSONPlaceholder untuk teks)
                val photos = PhotoApiClient.apiService.getPhotos(page = 1, limit = 30)
                val posts = PhotoApiClient.newsService.getNews(limit = 30)
                val users = PhotoApiClient.newsService.getUsers()

                // 2. Gabungkan data menjadi List NewsItem dengan proteksi users.size
                allNewsItems = posts.mapIndexed { index, post ->
                    val photo = photos.getOrNull(index)
                    val user = if (users.isNotEmpty()) users[index % users.size] else null

                    NewsItem(
                        id = post.id,
                        title = post.title.split(" ").take(4).joinToString(" ").replaceFirstChar { it.uppercase() },
                        body = post.body.take(100) + "...",
                        imageUrl = photo?.download_url ?: "https://picsum.photos/id/${index + 20}/800/600",
                        date = "${(index % 28) + 1} Mei 2024",
                        category = when(index % 3) {
                            0 -> "Informasi"
                            1 -> "Penting"
                            else -> "Pengumuman"
                        },
                        author = user?.name ?: "Admin Desa"
                    )
                }

                // 3. Tampilkan di RecyclerView Populer (Horizontal)
                val popularItems = allNewsItems.shuffled().take(10)
                binding.rvPopular.apply {
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    adapter = PopularNewsAdapter(popularItems)
                }

                // 4. Tampilkan di RecyclerView Terkini (Vertical)
                binding.rvGallery.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = PhotoAdapter(allNewsItems)
                    isNestedScrollingEnabled = false
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat berita: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayUsername() {
        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Warga Balam Sempurna")
        binding.tvUsername.text = username
    }

    private fun setupClickListeners() {
        binding.cardKalkulator.setOnClickListener {
            navigateTo(GeometricActivity::class.java, "Kalkulator Geometri", "Hitung Luas Segitiga dan Volume Balok dengan mudah.")
        }

        binding.cardGuru.setOnClickListener {
            navigateTo(DashboardGuruActivity::class.java, "Dashboard Tenaga Pengajar", "Manajemen data perkembangan anak dan kurikulum desa.")
        }

        binding.cardOrangTua.setOnClickListener {
            navigateTo(DashboardOrangTuaActivity::class.java, "Dashboard Orang Tua", "Pantau tumbuh kembang anak secara real-time.")
        }

        binding.cardWebsite.setOnClickListener {
            navigateTo(WebViewBinaDesaActivity::class.java)
        }

        binding.cardAspirasi.setOnClickListener {
            (activity as? com.example.ido_prism.BaseActivity)?.replaceFragment(com.example.ido_prism.Aspirasi.FragmentAspirasi())
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun navigateTo(targetActivity: Class<*>, judul: String? = null, deskripsi: String? = null) {
        val intent = Intent(requireContext(), targetActivity)
        judul?.let { intent.putExtra("EXTRA_JUDUL", it) }
        deskripsi?.let { intent.putExtra("EXTRA_DESKRIPSI", it) }
        startActivity(intent)
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin ingin keluar akun?")
            .setPositiveButton("Ya") { _, _ ->
                val sharedPreferences = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                sharedPreferences.edit {
                    putBoolean("isLogin", false)
                }

                // Menampilkan Snackbar umpan balik sebelum logout
                Snackbar.make(binding.root, "Permintaan Anda Disetujui", Snackbar.LENGTH_SHORT).show()

                // Memberikan jeda singkat (misal 1 detik) agar Snackbar sempat terbaca sebelum pindah halaman
                binding.root.postDelayed({
                    if (isAdded) {
                        Intent(requireContext(), LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }.also { intent ->
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                }, 1000)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
                // Menghapus AnchorView agar Snackbar tampil Full-Width di bagian bawah fragment
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK") { }
                    .show()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
