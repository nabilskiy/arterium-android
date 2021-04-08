package com.maritech.arterium.ui.dashboard.regionalManager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maritech.arterium.R
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.AgentModel
import com.maritech.arterium.data.models.Profile
import com.maritech.arterium.databinding.FragmentDashboardRmBinding
import com.maritech.arterium.ui.base.BaseFragment
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.AddNewMpActivity
import com.maritech.arterium.ui.dashboard.regionalManager.dialog.DialogNewAccount
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel
import java.util.*

class DashboardRmFragment : BaseFragment<FragmentDashboardRmBinding?>() {

    private val TAG = DashboardRmFragment::class.java.name

    private var navigator = DashboardRmNavigator()
    private lateinit var addAgentDialog: DialogNewAccount
    private lateinit var dashboardViewModel: DashboardRmViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var agentsAdapter: AgentsAdapter


    override fun getContentView(): Int {
        return R.layout.fragment_dashboard_rm
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)
        dashboardViewModel = ViewModelProvider(this).get(DashboardRmViewModel::class.java)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        addAgentDialog = DialogNewAccount(this.context, dialogListener)
        setListeners()
        observeViewModel()
        dashboardViewModel.getAgents()
        profileViewModel.getProfile()
    }

    private fun setListeners() {
        binding!!.details.ivSearch.setOnClickListener { v: View? ->
            binding!!.details.ivSearch.visibility = View.GONE
            binding!!.details.tvDoctors.visibility = View.GONE
            binding!!.details.ivClose.visibility = View.VISIBLE
            binding!!.details.clSearch.visibility = View.VISIBLE
        }
        binding!!.details.ivClose.setOnClickListener { v: View? ->
            binding!!.details.ivSearch.visibility = View.VISIBLE
            binding!!.details.tvDoctors.visibility = View.VISIBLE
            binding!!.details.ivClose.visibility = View.GONE
            binding!!.details.clSearch.visibility = View.GONE
        }
        binding!!.clBtnAddNewAccount.setOnClickListener { v: View? -> addAgentDialog.show() }
    }

    private val dialogListener: DialogListener = object : DialogListener {
        override fun addDoctor() {
            navigator.goToAddNewDoctor(navController)
        }

        override fun addMP() {
            startActivity(Intent(requireContext(), AddNewMpActivity::class.java))
        }
    }

    private val agentsOnClickListener = object : AgentsAdapter.AgentsOnClickListener {
        override fun onClick(agent: AgentModel) {

        }
    }

    private fun observeViewModel() {
        dashboardViewModel.agentsLiveData.observe(lifecycleOwner, this::initRecycler)
        dashboardViewModel.agentsStateViewModel.observe(lifecycleOwner, this::changeUIState)
        profileViewModel.responseLiveData.observe(lifecycleOwner, this::handleProfileResponse)
    }

    private fun handleProfileResponse(profile: Profile) {
        binding?.tvUserName?.text = profile.name
    }

    private fun changeUIState(state: ContentState) {
        when (state) {
            ContentState.LOADING ->
                binding?.details?.progress?.visibility = View.VISIBLE
            ContentState.ERROR -> {
                binding?.details?.progress?.visibility = View.GONE
                Log.i(TAG, "changeUIState: ")
            }
            //show error
            ContentState.CONTENT -> {
                binding?.details?.progress?.visibility = View.GONE
            }
            else -> {
                binding?.details?.progress?.visibility = View.GONE
            }
        }
    }

    private fun initRecycler(agentModels: List<AgentModel>) {
        agentsAdapter = AgentsAdapter(agentModels, agentsOnClickListener, requireContext())
        binding?.details?.rvDoctors?.layoutManager =
                LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false)
        binding?.details?.rvDoctors?.adapter = agentsAdapter
    }
}