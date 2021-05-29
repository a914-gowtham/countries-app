package com.gowtham.template.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gowtham.template.R
import com.gowtham.template.databinding.FDetailBinding
import com.gowtham.template.databinding.FListBinding
import com.gowtham.template.repo.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FList : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: FListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getCountries.setOnClickListener {
            viewModel.callListApi()
        }
    }
}