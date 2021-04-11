package com.maritech.arterium.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maritech.arterium.ui.dashboard.medicalRep.DashboardMpFragment
import com.maritech.arterium.ui.dashboard.regionalManager.DashboardRmFragment
import com.maritech.arterium.ui.my_profile.MyProfileFragment

class ViewOnlyMPRoleAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        val bundle: Bundle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return DashboardMpFragment().also { it.arguments = bundle }
    }
}