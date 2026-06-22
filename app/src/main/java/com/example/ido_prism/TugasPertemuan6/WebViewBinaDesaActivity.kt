package com.example.ido_prism.TugasPertemuan6

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ido_prism.R
import com.example.ido_prism.databinding.ActivityWebViewBinaDesaBinding

class WebViewBinaDesaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinaDesaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinaDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar Setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Web Bina Desa"
            subtitle = "Sistem Informasi Desa"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // WebView Setup
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://proyekdesa.alwaysdata.net/")
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    onBackPressedDispatcher.onBackPressed()
                }
                true
            }
            R.id.action_search -> {
                android.widget.Toast.makeText(this, "Mencari di Portal Desa...", android.widget.Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                android.widget.Toast.makeText(this, "Membuka Pengaturan Portal...", android.widget.Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}