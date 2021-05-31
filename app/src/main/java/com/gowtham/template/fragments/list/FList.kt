package com.gowtham.template.fragments.list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.location.*
import com.gowtham.template.R
import com.gowtham.template.databinding.FListBinding
import com.gowtham.template.models.ScrollEvent
import com.gowtham.template.models.country.Country
import com.gowtham.template.models.weather.Weather
import com.gowtham.template.utils.*
import com.gowtham.template.utils.Constants.REQ_GPS
import com.gowtham.template.utils.Utils.LOCATION_PER
import com.gowtham.template.utils.Utils.canCallWeatherApi
import com.gowtham.template.utils.Utils.loadImage
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.EasyPermissions.somePermissionPermanentlyDenied
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class FList : Fragment(), EasyPermissions.PermissionCallbacks {

    private val viewModel: ListViewModel by viewModels()

    private lateinit var binding: FListBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mLocationRequest: LocationRequest

    private val adChat: AdCountries by lazy {
        AdCountries()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        binding.imgWeather.setOnClickListener {
            if (Utils.isNetConnected(requireContext()))
                getLocationData()
            else
                TToast.showToast(requireContext(), R.string.err_no_net)
        }
        setRecyclerView()
        initLocation()
        subscribeObservers()
    }

    private fun initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mLocationRequest = LocationRequest.create().apply {
            interval = 60000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        if(canCallWeatherApi(requireContext()))
            getLocationData()
    }

    private fun setRecyclerView() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS;
        binding.listCountry.apply {
            layoutManager = staggeredGridLayoutManager
            itemAnimator = null
            adapter = adChat
        }

        /* workaround for staggered layout item moving issue */
        binding.listCountry.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                staggeredGridLayoutManager.invalidateSpanAssignments()
            }
        })

        adChat.setOnItemClickListener {
            val action = FListDirections.actionFListToFDetail(countryDetail = it)
            findNavController().navigate(action)
        }
    }

    private fun subscribeObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.currentState = state
                if (state is LoadState.OnSuccess)
                    adChat.submitList(state.data as List<Country>)
                 else if (state is LoadState.OnFailure)
                    binding.viewNoInternet.lottieView.playAnimation()
            }
        }

        lifecycleScope.launch {
            viewModel.weatherState.collect { state ->
                LogMessage.v("Weather state $state")
                binding.currentWeatherState = state
                if (state is LoadState.OnSuccess) {
                    val weather = state.data as Weather
                    binding.weather = weather
                    binding.viewWeather.imgWeather.loadImage("https:${weather.current.condition.icon}")
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                }
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (somePermissionPermanentlyDenied(this, perms))
            SettingsDialog.Builder(requireContext()).build().show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(123)
    private fun getLocationData() {
        LogMessage.v("dsdsdsd")
        if (EasyPermissions.hasPermissions(requireContext(), *LOCATION_PER)) {
            Utils.checkLocationPermission(
                requireActivity(), fusedLocationClient,
                locationCallback, mLocationRequest
            ) {
                //gpsIsEnabled is already enabled, waiting to get latLng on locationCallback
                viewModel.setWeatherLoadState()
            }
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(), getString(R.string.location_info),
                123, *LOCATION_PER
            )
        }
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            viewModel.fetchWeatherByLocation(p0.lastLocation)
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_GPS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    Utils.checkLocationPermission(
                        requireActivity(), fusedLocationClient,
                        locationCallback, mLocationRequest
                    ) {
                        //gpsIsEnabled is just enabled, waiting to get latLng on locationCallback
                        viewModel.setWeatherLoadState()
                    }
                }
                Activity.RESULT_CANCELED ->
                    LogMessage.v("Permission cancelled")
                else -> {
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNewListFetched(event: ScrollEvent){
        Handler(Looper.getMainLooper()).postDelayed({
            binding.listCountry.scrollToPosition(
                0
            )
        }, 200)
    }
}