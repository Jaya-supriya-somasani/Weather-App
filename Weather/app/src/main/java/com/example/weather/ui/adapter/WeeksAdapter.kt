package com.example.weather.ui.adapter

import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.data.WeatherDetails
import com.example.weather.databinding.ItemWeekTempBinding
import javax.inject.Inject

class WeeksAdapter @Inject constructor() : BaseAdapter<WeatherDetails>() {
    override fun submitList(data: List<WeatherDetails>?) {
        super.submitList(data?.distinctBy { it.weekDay })
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<WeatherDetails> {
        return WeeksTempHolder(parent.inflate(R.layout.item_week_temp))
    }

    private inner class WeeksTempHolder(binding: ItemWeekTempBinding) :
        BaseViewHolder<ItemWeekTempBinding, WeatherDetails>(binding) {
        override fun onBind(item: WeatherDetails) {
            try {
                binding.weekDayTv.text = item.weekDay
                binding.tempTv.text = "${item.main.celsius} C"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}