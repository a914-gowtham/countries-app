package com.gowtham.template.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gowtham.template.R
import com.gowtham.template.databinding.FListBinding
import com.gowtham.template.models.Country
import com.gowtham.template.utils.LoadState
import com.gowtham.template.utils.LogMessage
import com.gowtham.template.utils.gone
import com.gowtham.template.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FList : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: FListBinding


    private val adChat: AdCountries by lazy {
        AdCountries()
    }

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

        setRecyclerView()
        setObservers()
    }

    private fun setRecyclerView() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.listCountry.apply {
            layoutManager = staggeredGridLayoutManager
            itemAnimator = null
            adapter = adChat
        }
        binding.listCountry.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                staggeredGridLayoutManager.invalidateSpanAssignments()
            }
        })

        adChat.setOnItemClickListener {
            val action= FListDirections.actionFListToFDetail(countryDetail = it)
            findNavController().navigate(action)
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                LogMessage.v("State $state")
                when (state) {
                    is LoadState.OnFailure -> {
                        binding.btnRetry.show()
                        binding.progressCircular.gone()
                    }
                    is LoadState.OnSuccess -> {
                        adChat.submitList(state.data as List<Country>)
                        binding.btnRetry.gone()
                        binding.progressCircular.gone()
                    }
                    else -> {
                        binding.cardView.gone()
                        binding.btnRetry.gone()
                        binding.progressCircular.show()
                    }
                }
            }
        }
    }
}