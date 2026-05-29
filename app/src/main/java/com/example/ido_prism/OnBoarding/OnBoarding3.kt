package com.example.ido_prism.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ido_prism.TugasPertemuan3.LoginActivity
import com.example.ido_prism.databinding.FragmentOnBoarding3Binding

class OnBoarding3 : Fragment() {

    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            // Gunakan key 'show_onboarding_final' agar sinkron dengan SplashScreen
            val sharedPref = requireActivity().getSharedPreferences("user_pref", android.content.Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean("show_onboarding_final", false)
                apply()
            }

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
