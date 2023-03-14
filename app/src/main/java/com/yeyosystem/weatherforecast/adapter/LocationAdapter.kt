package com.yeyosystem.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeyosystem.weatherforecast.R
import com.yeyosystem.weatherforecast.data.Location
import javax.inject.Inject

interface OnItemClickListener {
    fun onItemClick(item: Location)
}

class LocationAdapter @Inject constructor(): ListAdapter<Location, LocationAdapter.ViewHolder>(LocationAdapter) {

    var onItemClickListener: OnItemClickListener? = null
    private var selectedItem: Int? = null
        set(value) {
            val oldValue = field
            field = value
            arrayOf(oldValue, field).forEach {
                it?.let { notifyItemChanged(it) }
            }
        }

    private companion object : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val city: TextView
        val country: TextView

        init {
            city = view.findViewById(R.id.text_city_location)
            country = view.findViewById(R.id.text_country_location)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_location, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (currentList.isEmpty()) {
            return
        }

        with(holder) {
            getItem(position)?.let { location: Location ->
                holder.itemView.setOnClickListener {
                    selectedItem = position
                    onItemClickListener?.onItemClick(location)
                }
                city.text = location.name
                country.text = location.country

            }
        }

    }

    fun resetSelectedItem() {
        selectedItem = null
    }

}