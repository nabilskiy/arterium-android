package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.R
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.databinding.ItemChooseMpBinding

class SelectDoctorsAdapter(
        val onClickListener: SelectDoctorOnClickListener
) : RecyclerView.Adapter<SelectDoctorsAdapter.ViewHolder>() {

    private val TAG = "AddNewMpActivity_TAG"

    var doctors: List<DoctorsModel> = listOf()
        set(doctors) {
            field = doctors
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_mp, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.tvName.text = doctor.name
        holder.tvCity.text = doctor.city
        if (doctor.selected)
            holder.ivChecked.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int = doctors.size


    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val tvName = rootView.findViewById<AppCompatTextView>(R.id.tvUserName)
        val tvCity = rootView.findViewById<AppCompatTextView>(R.id.tvUserCity)
        val ivChecked = rootView.findViewById<View>(R.id.ivCheck)

        init {
            rootView.setOnClickListener {
                if (ivChecked.visibility == View.VISIBLE)
                    ivChecked.visibility = View.GONE
                else ivChecked.visibility = View.VISIBLE
                doctors[adapterPosition].selected = !doctors[adapterPosition].selected
                onClickListener.onClick()
            }
        }
    }

    interface SelectDoctorOnClickListener {
        fun onClick()
    }


}