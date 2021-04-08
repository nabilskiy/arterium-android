package com.maritech.arterium.ui.dashboard.regionalManager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.R
import com.maritech.arterium.data.models.AgentModel
import com.maritech.arterium.databinding.ItemAgentsBinding

class AgentsAdapter(
        val agents: List<AgentModel>,
        val clickListener: AgentsOnClickListener,
        val context: Context
) : RecyclerView.Adapter<AgentsAdapter.ViewHolder>() {

    private var _binding: ItemAgentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemAgentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.descrTV.text = context.getString(R.string.mp)
        holder.nameTV.text = agents[position].name
    }


    override fun getItemCount(): Int = agents.size


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        val nameTV = binding.tvName
        val descrTV = binding.tvDescription

        init {
            binding.root.setOnClickListener {
                clickListener.onClick(agents[adapterPosition])
            }
        }
    }

    interface AgentsOnClickListener {
        fun onClick(agent: AgentModel)
    }
}