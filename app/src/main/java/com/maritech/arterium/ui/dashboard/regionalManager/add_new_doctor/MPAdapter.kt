package com.maritech.arterium.ui.dashboard.regionalManager.add_new_doctor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maritech.arterium.R
import com.maritech.arterium.data.models.AgentModel

class MPAdapter(
        val onClickListener: MPOnClickListener,

        ) : RecyclerView.Adapter<MPAdapter.ViewHolder>() {

    var agents: List<AgentModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var selected: Int? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_mp, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agent = agents[position]
        holder.tvInfo.text = agent.login
        holder.tvName.text = agent.name
        if (position == selected)
            holder.ivCheck.visibility = View.VISIBLE
        else holder.ivCheck.visibility = View.GONE
    }

    override fun getItemCount(): Int = agents.size

    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val tvInfo: TextView = rootView.findViewById(R.id.tvUserCity)
        val tvName: TextView = rootView.findViewById(R.id.tvUserName)
        val ivCheck: View = rootView.findViewById(R.id.ivCheck)

        init {
            rootView.setOnClickListener {
//                selected = adapterPosition
                Log.i("ADD_NEW_DOCTOR_TAG", "onClick: $adapterPosition")
                onClickListener.onClick(adapterPosition)
            }
        }
    }

    interface MPOnClickListener {
        fun onClick(position: Int)
    }
}