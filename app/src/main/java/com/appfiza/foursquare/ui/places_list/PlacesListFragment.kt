package com.appfiza.foursquare.ui.places_list

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.appfiza.foursquare.R
import com.appfiza.foursquare.databinding.FragmentPlaceListBinding
import com.appfiza.foursquare.util.custering.ClusterPlace
import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.util.EventObserver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
@ExperimentalCoroutinesApi
@SuppressLint("MissingPermission")
class PlacesListFragment : Fragment(R.layout.fragment_place_list), OnMapReadyCallback {

    private lateinit var binding: FragmentPlaceListBinding

    private companion object {
        const val ZOOM_STREET_LEVEL = 15F
    }

    private val viewModel: PlacesListViewModel by viewModel()

    private lateinit var clusterManager: ClusterManager<ClusterPlace>
    private lateinit var map: GoogleMap

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            // The user accepted the location permission
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                map.isMyLocationEnabled = true
                map.uiSettings.isMyLocationButtonEnabled = false
                viewModel.onUserGrantedLocationPermission()
            }
            else -> {
                // No location access granted.
            }
        }
    }

    /**
     *  Ask the user for his location permission (coarse & fine)
     */
    private fun askLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        // No need to map sync if it's already done
        if (!this::map.isInitialized) mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }

    /**
     *  Load clusters into the map
     *  @param [places] List of places
     */
    private fun loadClusters(places: List<Place>) {
        clusterManager.clearItems()
        places.forEach { place ->
            clusterManager.addItem(
                ClusterPlace(
                    place.toLatLng(),
                    place.id
                )
            )
        }
        clusterManager.cluster()
    }

    private fun initObservers() {
        viewModel.centerMapToUserLocationLiveData.observe(viewLifecycleOwner, EventObserver {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM_STREET_LEVEL))
        })

        viewModel.placesLiveData.observe(viewLifecycleOwner) { loadClusters(it) }
    }

    private fun setUpClusterer() {
        clusterManager = ClusterManager(context, map)

        clusterManager.setOnClusterItemClickListener {
            val action =
                PlacesListFragmentDirections.actionMapFragmentToPlaceDetailsFragment(frsqID = it.getFsqID())
            view?.findNavController()?.navigate(action)
            true
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map

        askLocationPermission()
        setUpClusterer()
        initObservers()

        map.setOnCameraIdleListener {
            val currentUserLatLngScreenBounds = map.projection.visibleRegion.latLngBounds
            viewModel.onMapMove(currentUserLatLngScreenBounds)
            clusterManager.onCameraIdle()
        }
    }

}