package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.databinding.ItemChooseMpBinding

class SelectedDoctorsAdapter(
        val onClickListener: RemoveDoctorsOnClickListener,
        val doctors: List<DoctorsModel>
):RecyclerView.Adapter<SelectedDoctorsAdapter.ViewHolder>() {


    private var _binding: ItemChooseMpBinding? = null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemChooseMpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.tvCity.text = doctor.city
        holder.tvName.text = doctor.name
    }

    override fun getItemCount(): Int = doctors.size


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvUserName
        val tvCity = binding.tvUserCity
        val ivRemove = binding.ivRemove

        init {
            ivRemove.visibility = View.VISIBLE
            ivRemove.setOnClickListener {
                doctors[adapterPosition].selected = !doctors[adapterPosition].selected
                onClickListener.onClick()
            }
        }
    }

    interface RemoveDoctorsOnClickListener {
        fun onClick()
    }


}