package com.maritech.arterium.ui.dashboard.medicalRep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.databinding.ItemAgentsBinding

class DoctorsAdapter(
        val onClickListener: DoctorsOnClickListener
):RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    var doctors:List<DoctorsModel> = listOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    private var _binding: ItemAgentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemAgentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.nameTV.text = doctor.name
        // TODO: 11.04.2021 need to out count of sales
        holder.descrTV.text = doctor.city
    }

    override fun getItemCount(): Int = doctors.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        val nameTV = binding.tvName
        val descrTV = binding.tvDescription

        init {
            binding.root.setOnClickListener {
                onClickListener.onClick(doctors[adapterPosition])
            }
        }
    }

    interface DoctorsOnClickListener{
        fun onClick(doctor:DoctorsModel)
    }
}