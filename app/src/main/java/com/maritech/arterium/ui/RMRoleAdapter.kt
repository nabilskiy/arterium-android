package com.maritech.arterium.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maritech.arterium.ui.dashboard.regionalManager.DashboardRmFragment
import com.maritech.arterium.ui.my_profile.MyProfileFragment

class RMRoleAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            0-> DashboardRmFragment()
           else -> MyProfileFragment()
        }
    }
}