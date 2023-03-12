package com.yeyosystem.weatherforecast

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.Glide
import com.yeyosystem.weatherforecast.data.Current
import com.yeyosystem.weatherforecast.data.ForecastDay
import com.yeyosystem.weatherforecast.data.Location
import com.yeyosystem.weatherforecast.databinding.FragmentFirstBinding
import com.yeyosystem.weatherforecast.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val weatherViewModel: WeatherViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.search -> {
                        // clearCompletedTasks()
                        true
                    }
                    /*R.id.menu_refresh -> {
                        // loadTasks(true)
                        true
                    }*/
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        showCurrentHour()
        weatherViewModel.weathers.observe(viewLifecycleOwner) {
            showLocationInformation(it.location)
            showCurrentInformation(it.current)
            showNextDays(it.forecast.forecastDay)
        }
        binding.viewModel = weatherViewModel
    }

    private fun showNextDays(forecastDay: List<ForecastDay>) {

    }

    private fun showCurrentHour() {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val currentTime: String = sdf.format(Date())
        binding.textHour.text = currentTime
    }

    private fun showLocationInformation(location: Location) {
        binding.textCity.text = location.name
        binding.textDate.text = weatherViewModel.formatDateCurrent(location.localtime)
    }

    @SuppressLint("StringFormatInvalid")
    private fun showCurrentInformation(current: Current) {
        binding.textCurrentTemperature.text =
            getString(R.string.temperature, current.temp_f.toString())
        binding.textCurrentWind.text = getString(R.string.wind, current.wind_mph.toInt().toString())
        binding.textCurrentPressure.text = getString(R.string.press, current.precip_in.toInt().toString())
        binding.textCurrentCondition.text =
            getString(R.string.condition, current.condition.text.lowercase())
        Glide
            .with(this@FirstFragment)
            .load(current.condition.icon.replace("//", "https://"))
            .centerCrop()
            .into(binding.imageCurrent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}