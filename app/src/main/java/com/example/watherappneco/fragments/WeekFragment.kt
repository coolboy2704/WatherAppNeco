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
import com.example.watherappneco.databinding.FragmentWeekBinding
import com.example.watherappneco.fragments.adapters.WeatherAdapter
import kotlinx.coroutines.flow.combine

class WeekFragment : Fragment(), WeatherAdapter.Listener {
    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: FragmentWeekBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.liveDataList.observe(viewLifecycleOwner){
            adapter.submitList(it.subList(1, it.size))
        }
    }

    private fun init() = with(binding) {
        adapter = WeatherAdapter(this@WeekFragment)
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeekFragment()
    }

    override fun onClick(item: WeatherModel) {
        model.liveDataCurrent.value = item
    }
}