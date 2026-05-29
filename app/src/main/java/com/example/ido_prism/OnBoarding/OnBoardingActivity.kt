package com.example.ido_prism.OnBoarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ido_prism.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentsList = listOf(
            OnBoarding1(),
            OnBoarding2(),
            OnBoarding3()
        )

        val adapter = OnBoardingFragmentAdapter(this, fragmentsList)
        binding.onBoardingViewPager.adapter = adapter
        
        binding.dotsIndicator.attachTo(binding.onBoardingViewPager)
    }
}
