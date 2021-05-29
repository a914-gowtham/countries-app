package com.gowtham.template.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gowtham.template.R
import com.gowtham.template.databinding.FDetailBinding

class FDetail : Fragment() {

    private lateinit var binding: FDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

}