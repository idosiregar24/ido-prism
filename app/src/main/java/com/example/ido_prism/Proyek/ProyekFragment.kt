package com.example.ido_prism.Proyek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ido_prism.databinding.FragmentProyekBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProyekFragment : Fragment() {

    private var _binding: FragmentProyekBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProyekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProyekPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Proyek"
                    tab.setIcon(com.example.ido_prism.R.drawable.ic_notes)
                }
                1 -> {
                    tab.text = "Kontraktor"
                    tab.setIcon(com.example.ido_prism.R.drawable.ic_person)
                    val badge = tab.orCreateBadge
                    badge.isVisible = true
                    badge.number = 7
                }
            }
        }.attach()
    }

    private class ProyekPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    // Ambil ID dari arguments ProyekFragment dan teruskan ke ListProyekFragment
                    val proyekId = fragment.arguments?.getString("TARGET_PROYEK_ID")
                    ListProyekFragment().apply {
                        arguments = Bundle().apply {
                            putString("OPEN_DETAIL_ID", proyekId)
                        }
                    }
                }
                1 -> ListKontraktorFragment()
                else -> ListProyekFragment()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}