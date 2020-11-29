package fr.cedriccreusot.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.cedriccreusot.weatherapp.databinding.FragmentWeatherListBinding
import java.lang.ref.WeakReference

@AndroidEntryPoint
class WeatherListFragment : Fragment() {
    private lateinit var binding: WeakReference<FragmentWeatherListBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeakReference(FragmentWeatherListBinding.inflate(inflater))

        binding.get()?.openAddCityButton?.setOnClickListener {
            val action = WeatherListFragmentDirections.actionWeatherListFragmentToSearchCityFragment()
            findNavController().navigate(action)
        }
        return binding.get()?.root
    }
}