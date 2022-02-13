package com.appfiza.foursquare.ui.place_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.appfiza.foursquare.R
import com.appfiza.foursquare.databinding.FragmentPlaceDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlaceDetailsFragment : Fragment(R.layout.fragment_place_details) {

    private val viewModel by viewModel<PlaceDetailsViewModel> {
        parametersOf(args.frsqID)
    }

    private val args: PlaceDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentPlaceDetailsBinding
    private lateinit var placePagerAdapter: PlacePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        placePagerAdapter = PlacePagerAdapter(requireContext(), listOf())
        binding.viewPager.adapter = placePagerAdapter

        viewModel.loadPlaceAndPlacePhotos()
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}