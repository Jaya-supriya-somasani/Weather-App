package com.example.weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.ForeCaseWeather

abstract class BaseAdapter<Item> : RecyclerView.Adapter<BaseHolder<Item>>() {

    var listItems: ArrayList<Item> = ArrayList()

    open fun submitList(data: List<Item>?) {
        listItems.clear()

        if (data != null) {
            listItems.addAll(data)
        }

        notifyDataSetChanged()
    }


    override fun onBindViewHolder(viewHolder: BaseHolder<Item>, position: Int) {

        val item = getItem(position)

        if (item != null) {
            viewHolder.onBind(item)
        }
    }

    fun getItem(position: Int): Item {
        return listItems[position]
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}

abstract class BaseHolder<Item>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBind(item: Item)
}

abstract class BaseViewHolder<Binding : ViewDataBinding, Item>(val binding: Binding) :
    BaseHolder<Item>(binding.root)


fun <T : ViewDataBinding> ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    attachToParent: Boolean = false
): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToParent)
}