package com.example.watherappneco.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.watherappneco.MainViewModel
import com.example.watherappneco.R
import com.example.watherappneco.WeatherModel
import com.example.watherappneco.databinding.FragmentHourlyBinding
import com.example.watherappneco.databinding.FragmentWeekBinding
import com.example.watherappneco.fragments.adapters.WeatherAdapter
import org.json.JSONArray
import org.json.JSONObject

class HourlyFragment : Fragment() {

    private lateinit var binding: FragmentHourlyBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataCurrent.observe(viewLifecycleOwner){
            adapter.submitList(getHoursList(it))
        }
    }

    private fun initRcView() = with(binding){
        adapter = WeatherAdapter(null)
        recyclerView.adapter = adapter
    }

    private fun getHoursList(wItem: WeatherModel): List<WeatherModel>{
        val hoursArray = JSONArray(wItem.hours)
        val list = ArrayList<WeatherModel>()
        for (i in 0 until hoursArray.length()){
            val item = WeatherModel(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
                condition = "",
                (hoursArray[i] as JSONObject).getString("temp_c"),
                maxTemp = "",
                minTemp = "",
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("icon"),
                hours = "",
                country = ""
            )
            list.add(item)
        }
        return list
    }
}