package fr.cedriccreusot.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fr.cedriccreusot.weatherapp.adapters.CitiesAdapter
import fr.cedriccreusot.weatherapp.databinding.FragmentSearchCityBinding
import fr.cedriccreusot.weatherapp.viewmodels.SearchCityViewModel
import java.lang.ref.WeakReference

@AndroidEntryPoint
class SearchCityFragment : Fragment() {
    private lateinit var binding: WeakReference<FragmentSearchCityBinding>

    private val searchCityViewModel: SearchCityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeakReference(FragmentSearchCityBinding.inflate(inflater))

        binding.get()?.run {
            searchInput.addTextChangedListener {
                searchCityViewModel.search(it.toString())
            }

            searchCityViewModel.cities().observe(requireActivity()) {
                cityList.scrollToPosition(0)
                cityList.adapter = CitiesAdapter { selectedItem ->

                }.apply {
                    submitList(it)
                }
            }
        }

        return binding.get()?.root
    }
}