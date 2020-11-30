package fr.cedriccreusot.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.cedriccreusot.weatherapp.adapters.WeatherListAdapter
import fr.cedriccreusot.weatherapp.databinding.FragmentWeatherListBinding
import fr.cedriccreusot.weatherapp.viewmodels.Status
import fr.cedriccreusot.weatherapp.viewmodels.WeatherViewModel
import java.lang.ref.WeakReference

@AndroidEntryPoint
class WeatherListFragment : Fragment() {
    private lateinit var binding: WeakReference<FragmentWeatherListBinding>

    private val weatherViewModel: WeatherViewModel by viewModels()

    private val weatherAdapter : WeatherListAdapter = WeatherListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeakReference(FragmentWeatherListBinding.inflate(inflater))

        binding.get()?.run {
            weatherList.adapter = weatherAdapter

            weatherViewModel.weathers().observe(requireActivity()) {
                when (it) {
                    is Status.LoadingStatus -> statusViewFlipper.displayedChild = 0
                    is Status.WeathersStatus -> {
                        statusViewFlipper.displayedChild = 1
                        weatherAdapter.submitList(it.weathers)
                    }
                }
            }

            openAddCityButton.setOnClickListener {
                val action =
                    WeatherListFragmentDirections.actionWeatherListFragmentToSearchCityFragment()
                findNavController().navigate(action)
            }
        }

        return binding.get()?.root
    }

    override fun onStart() {
        super.onStart()
        weatherViewModel.fetchWeathers()
    }
}
