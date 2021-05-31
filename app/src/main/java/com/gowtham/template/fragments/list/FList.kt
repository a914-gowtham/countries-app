package com.gowtham.template.fragments.list

import android.Manifest
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
import com.gowtham.template.databinding.FListBinding
import com.gowtham.template.models.country.Country
import com.gowtham.template.utils.Constants
import com.gowtham.template.utils.Constants.REQ_GPS
import com.gowtham.template.utils.LoadState
import com.gowtham.template.utils.LogMessage
import com.gowtham.template.utils.Utils
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.EasyPermissions.somePermissionPermanentlyDenied
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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
        initLocation()
        setRecyclerView()
        setObservers()

        binding.imgWeather.setOnClickListener {
            getLocationData()
        }
    }

    private fun initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mLocationRequest = LocationRequest.create().apply {
            interval = 60000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
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

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.currentState = state
                if (state is LoadState.OnSuccess) {
                    adChat.submitList(state.data as List<Country>)
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.listCountry.scrollToPosition(
                            0
                        )
                    }, 200)
                } else if (state is LoadState.OnFailure)
                    binding.viewNoInternet.lottieView.playAnimation()
            }
        }

        lifecycleScope.launch {
            viewModel.weatherState.collect {

              /*  if (it is LoadState.OnSuccess)
                    fusedLocationClient.removeLocationUpdates(locationCallback)*/
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

    @AfterPermissionGranted(Constants.REQ_LOCATION_PERMISSION)
    private fun getLocationData() {
        val perms =
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
//          fusedLocationClient.removeLocationUpdates(locationCallback)
            Utils.checkLocationPermission(
                requireActivity(), fusedLocationClient,
                locationCallback, mLocationRequest
            ) {
                //gpsIsEnabled is already enabled, waiting to get latLng on locationCallback
                viewModel.setWeatherLoadState()
            }
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(), "We need permissions to get your location data",
                Constants.REQ_LOCATION_PERMISSION, *perms
            )
        }
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            LogMessage.v("Locationresult ${p0.lastLocation.latitude} ${p0.lastLocation.longitude}")
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
                    ){
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
}