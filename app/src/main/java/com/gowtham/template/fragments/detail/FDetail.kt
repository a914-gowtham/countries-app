package com.gowtham.template.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.gowtham.template.R
import com.gowtham.template.databinding.FDetailBinding
import com.gowtham.template.models.Info
import com.gowtham.template.models.country.Country
import com.gowtham.template.models.weather.Weather
import com.gowtham.template.utils.LoadState
import com.gowtham.template.utils.LogMessage
import com.gowtham.template.utils.Utils.loadImage
import com.gowtham.template.utils.Utils.loadSvgWithPlaceholder
import com.gowtham.template.utils.assistedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FDetail : Fragment() {

    private lateinit var binding: FDetailBinding

    val args: FDetailArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: DetailViewModel.Factory

    private val viewModel by assistedViewModel { viewModelFactory.create(args.countryDetail, it) }

    private lateinit var country: Country

    private val adInfo: AdInfo by lazy {
        AdInfo()
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
        LogMessage.v("Country $country")
        binding.lifecycleOwner = viewLifecycleOwner
        binding.country = country
        viewModel //making a fake call to initialize the viewmodel
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        setFlows()
        setDataInView()
    }

    private fun setFlows() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state is LoadState.OnSuccess) {
                    val weatherData = state.data as Weather
                    binding.apply {
                        weather = weatherData
                        viewWeather.imgWeather.loadImage("https:${weatherData.current.condition.icon}")
                    }
                }
                binding.currentState = state
            }
        }
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