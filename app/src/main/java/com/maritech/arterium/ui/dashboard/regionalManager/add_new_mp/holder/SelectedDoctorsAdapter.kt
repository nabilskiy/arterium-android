package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.R
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.databinding.ItemChooseMpBinding

class SelectedDoctorsAdapter(
        val onClickListener: RemoveDoctorsOnClickListener
) : RecyclerView.Adapter<SelectedDoctorsAdapter.ViewHolder>() {

    var doctors: List<DoctorsModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_mp, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.tvCity.text = doctor.city
        holder.tvName.text = doctor.name
    }

    override fun getItemCount(): Int = doctors.size


    inner class ViewHolder(rootView:View) : RecyclerView.ViewHolder(rootView) {
        val tvName = rootView.findViewById<AppCompatTextView>(R.id.tvUserName)
        val tvCity = rootView.findViewById<AppCompatTextView>(R.id.tvUserCity)
        val ivRemove =  rootView.findViewById<View>(R.id.ivRemove)
        init {
            ivRemove.visibility = View.VISIBLE
            ivRemove.setOnClickListener {
                doctors[adapterPosition].selected = !doctors[adapterPosition].selected
                onClickListener.onClick()
            }
            rootView.findViewById<View>(R.id.ivArrow).visibility = View.GONE
        }
    }

    interface RemoveDoctorsOnClickListener {
        fun onClick()
    }


}