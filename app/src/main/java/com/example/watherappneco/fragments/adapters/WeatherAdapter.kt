package com.example.watherappneco.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.watherappneco.R
import com.example.watherappneco.WeatherModel
import com.example.watherappneco.databinding.ItemWeekBinding
import com.squareup.picasso.Picasso

class WeatherAdapter(val listener: Listener?) : androidx.recyclerview.widget.ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View, val listener: Listener?): RecyclerView.ViewHolder(view){
        val binding = ItemWeekBinding.bind(view)
        var itemTemp: WeatherModel? = null

        init {
            itemView.setOnClickListener{
                itemTemp?.let { it1 -> listener?.onClick(it1) }
            }
        }

        fun bind (item: WeatherModel) = with(binding){
            itemTemp = item
            dataWeek.text = item.time
            tempWeek.text = item.currentTemp.ifEmpty { "${item.maxTemp} / ${item.minTemp}" }
            Picasso.get().load("https:" + item.imageUrl).into(ivWeek)
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel>(){
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_week, parent, false)
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    interface Listener {
        fun onClick(item: WeatherModel)
    }
}