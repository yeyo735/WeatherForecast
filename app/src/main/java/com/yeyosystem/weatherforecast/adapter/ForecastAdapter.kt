package com.yeyosystem.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yeyosystem.weatherforecast.R
import com.yeyosystem.weatherforecast.data.ForecastDay
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ForecastAdapter @Inject constructor() :
    ListAdapter<ForecastDay, ForecastAdapter.ViewHolder>(ForecastAdapter) {

    private companion object : DiffUtil.ItemCallback<ForecastDay>() {
        override fun areItemsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutForecast: LinearLayout
        val imageForecast: ImageView
        val textTemperature: TextView
        val textDay: TextView

        init {
            layoutForecast = view.findViewById(R.id.layout_forecast)
            imageForecast = view.findViewById(R.id.image_forecast)
            textTemperature = view.findViewById(R.id.text_forecast_temperature)
            textDay = view.findViewById(R.id.text_forecats_day)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (currentList.isEmpty()) {
            return
        }

        with(holder) {
            getItem(position)?.let { forecast: ForecastDay ->
                textTemperature.text = itemView.resources.getString(
                    R.string.third_temperature, forecast.day.mintemp_f.toInt()
                        .toString(), forecast.day.maxtemp_f.toInt().toString()
                )
                when (position) {
                    0 -> {
                        textDay.text = "Today"
                    }
                    1 -> {
                        textDay.text = "Tomorrow"
                    }
                    2 -> {
                        textDay.text = formatDateThird(forecast.date)
                    }
                }

                RequestOptions().apply {
                    Glide.with(itemView.context)
                        .load(forecast.day.condition.icon.replace("//", "https://"))
                        .apply(this)
                        .into(imageForecast)
                }
            }
        }
    }

    private fun formatDateThird(third: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormat = SimpleDateFormat("EEEE", Locale.US)
        val date = inputFormat.parse(third)
        return outputFormat.format(date)
    }

}