package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.databinding.ItemAgentsBinding
import com.maritech.arterium.databinding.ItemChooseMpBinding

class SelectDoctorsAdapter(
        val onClickListener: SelectDoctorOnClickListener,
        val doctors: List<DoctorsModel>
) : RecyclerView.Adapter<SelectDoctorsAdapter.ViewHolder>() {

    private var _binding: ItemChooseMpBinding? = null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemChooseMpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.tvName.text = doctor.name
        holder.tvCity.text = doctor.city
        if (doctor.selected)
            holder.ivChecked.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int = doctors.size


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvUserName
        val ivChecked = binding.ivCheck
        val tvCity = binding.tvUserCity

        init {
            binding.root.setOnClickListener {
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