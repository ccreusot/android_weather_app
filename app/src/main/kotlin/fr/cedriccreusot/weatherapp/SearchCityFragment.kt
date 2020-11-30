package fr.cedriccreusot.weatherapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.cedriccreusot.weatherapp.adapters.CitiesAdapter
import fr.cedriccreusot.weatherapp.databinding.FragmentSearchCityBinding
import fr.cedriccreusot.weatherapp.viewmodels.SearchCityViewModel
import kotlinx.coroutines.FlowPreview
import java.lang.ref.WeakReference

@AndroidEntryPoint
class SearchCityFragment : Fragment() {
    private lateinit var binding: WeakReference<FragmentSearchCityBinding>

    @FlowPreview
    private val searchCityViewModel: SearchCityViewModel by viewModels()

    @FlowPreview
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

            cityList.adapter = CitiesAdapter()

            searchCityViewModel.cities.observe(requireActivity()) {
                cityList.scrollToPosition(0)
                cityList.adapter = CitiesAdapter { selectedCity ->
                    searchCityViewModel.saveCity(selectedCity)
                    findNavController().navigateUp()
                    closeKeyBoard()
                }.apply {
                    submitList(it)
                }
            }
        }

        return binding.get()?.root
    }

    private fun closeKeyBoard() {
        val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.windowToken?.let { token ->
            manager.hideSoftInputFromWindow(token, 0)
        }
    }
}