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
import com.google.android.flexbox.FlexboxLayoutManager
import com.gowtham.template.R
import com.gowtham.template.databinding.FDetailBinding
import com.gowtham.template.models.Country
import com.gowtham.template.utils.Utils.loadSvg
import com.gowtham.template.utils.Utils.loadSvgWithPlaceholder
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
        binding.lifecycleOwner=viewLifecycleOwner
        binding.country=country

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        setDataInView()
    }

    private fun setDataInView() {
        binding.imageViewCollapsing.loadSvgWithPlaceholder(country.flag)
        val linearLayoutManager = FlexboxLayoutManager(requireContext())
//        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.listDetail.apply {
            layoutManager = linearLayoutManager
            adapter = adInfo
        }
        adInfo.submitList(getDetailList())
    }

    private fun getDetailList(): List<Info> {
        val listOfInfos = mutableListOf<Info>()
        val titles = resources.getStringArray(R.array.info_titles).toList()
        val icons = resources.getStringArray(R.array.info_icons).toList()
        titles.forEachIndexed { index, title ->
            val info = when (index) {
                0 -> Info(icons[index], title, country.region)
                1 -> Info(icons[index], title, country.population.toString())
                2 -> Info(
                    icons[index],
                    title,
                    country.languages.map { it.name }.joinToString(",")
                )

                3 -> Info(icons[index], title, country.demonym)

                else -> Info(
                    icons[index],
                    title,
                    country.currencies.map { it.name }.joinToString(",")
                )
            }
            listOfInfos.add(info)
        }
        return listOfInfos
    }

}