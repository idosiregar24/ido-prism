package com.example.ido_prism.Note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ido_prism.R
import com.example.ido_prism.databinding.FragmentHomeBinding
import com.example.ido_prism.databinding.FragmentNoteBinding


class FragmentNote : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_note, container, false)
    }
}