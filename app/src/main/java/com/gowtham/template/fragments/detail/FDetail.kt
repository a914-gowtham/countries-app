package com.gowtham.template.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gowtham.template.R
import com.gowtham.template.databinding.FDetailBinding
import com.gowtham.template.fragments.list.AdCountries
import com.gowtham.template.models.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FDetail : Fragment() {

    private lateinit var binding: FDetailBinding

    val args: FDetailArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var country: Country

    private val adInfo: AdInfo by lazy {
        AdInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        country = args.countryDetail!!
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            country = country
        }
        setDataInView()

    }

    private fun setDataInView() {
        val titles = resources.getStringArray(R.array.info_titles).toList()
        val icons = resources.getStringArray(R.array.info_icons).toList()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.listDetail.apply {
            layoutManager=linearLayoutManager
            adapter = adInfo
        }

        val listOfInfos = mutableListOf<Info>()
        titles.forEachIndexed { index, title ->
            when (index) {
                0 -> listOfInfos.add(Info(icons[index], title, country.region))

                1 ->
                    listOfInfos.add(Info(icons[index], title, country.population.toString()))

                2 ->
                    listOfInfos.add(Info(icons[index], title, country.languages.joinToString(",")))

                3 ->
                    listOfInfos.add(Info(icons[index], title, country.demonym))

                4 ->
                    listOfInfos.add(Info(icons[index], title, country.currencies.joinToString(",")))
            }
        }
        adInfo.submitList(listOfInfos)
    }

}