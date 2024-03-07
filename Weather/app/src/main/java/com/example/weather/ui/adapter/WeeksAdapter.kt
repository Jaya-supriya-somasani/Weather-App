package com.example.weather.ui.adapter

import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.data.WeatherDetails
import com.example.weather.databinding.ItemWeekTempBinding
import javax.inject.Inject

class WeeksAdapter @Inject constructor() : BaseAdapter<WeatherDetails>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<WeatherDetails> {
        return WeeksTempHolder(parent.inflate(R.layout.item_week_temp))
    }

    private inner class WeeksTempHolder(binding: ItemWeekTempBinding) :
        BaseViewHolder<ItemWeekTempBinding, WeatherDetails>(binding) {

        override fun onBind(item: WeatherDetails) {
            binding.weekDayTv.text = item.dateText
        }
    }
}