package com.maritech.arterium.ui.dashboard.regionalManager.add_new_doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.R
import com.maritech.arterium.data.models.RegionModel

class RegionsAdapter(
        val onClickListener: RegionOnClickListener
) : RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {

    var regions: List<RegionModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_region, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = regions[position].name
    }

    override fun getItemCount(): Int =
            regions.size


    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val name: TextView = rootView.findViewById(R.id.name)

        init {
            rootView.setOnClickListener {
                onClickListener.onClick(adapterPosition)
            }
        }
    }

    interface RegionOnClickListener {
        fun onClick(position: Int)
    }
}