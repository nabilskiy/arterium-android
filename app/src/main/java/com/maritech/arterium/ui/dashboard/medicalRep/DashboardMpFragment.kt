package com.maritech.arterium.ui.dashboard.medicalRep

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maritech.arterium.R
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.data.models.Profile
import com.maritech.arterium.databinding.FragmentDashboardMpBinding
import com.maritech.arterium.ui.MainActivity
import com.maritech.arterium.ui.base.BaseFragment
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel
import com.maritech.arterium.ui.dashboard.medicalRep.MPViewModel.Companion.TAG

class DashboardMpFragment : BaseFragment<FragmentDashboardMpBinding?>() {

    private var isFromRM = false
    private var agentId: Int = -1
    private var name: String? = null


    private var navigator = DashboardMpNavigator()

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var viewModel: MPViewModel

    private var adapter: DoctorsAdapter

    override fun getContentView(): Int {
        return R.layout.fragment_dashboard_mp
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MPViewModel::class.java)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        init()
        initListeners()
    }


    private fun init() {
        if (arguments != null && arguments?.containsKey(ID_KEY_BUNDLE) == true) {
            agentId = arguments?.getInt(ID_KEY_BUNDLE) ?: -1
            Log.i(TAG, "init: $agentId")
            if (agentId > 0) {
                isFromRM = true
                name = arguments?.getString(NAME_KEY_BUNDLE)
            }
        }
        if (name != null) {
            binding!!.tvUserName.text = name
            disableListeners()
            viewModel.getDoctorsById(agentId)
        } else {
            profileViewModel.getProfile()
            viewModel.getDoctors()
        }

        binding?.details?.rvDoctors?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.details?.rvDoctors?.adapter = adapter
    }

    private fun initListeners() {
        viewModel.doctorsViewStateLiveData.observe(lifecycleOwner, this::observeContentState)
        viewModel.doctorsLiveData.observe(lifecycleOwner, this::observeDoctorsList)
        profileViewModel.responseLiveData.observe(lifecycleOwner, this::observeProfileResponse)


    }

    private fun disableListeners() {

    }

    private fun observeContentState(state: ContentState) {
        if (state == ContentState.LOADING) {
            binding!!.progress.visibility = View.VISIBLE
        } else {
            binding!!.progress.visibility = View.GONE
            if (state == ContentState.ERROR) {
                Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeProfileResponse(profile: Profile) {
        binding!!.tvUserName.text = profile.name
    }

    private fun observeDoctorsList(doctors: List<DoctorsModel>) {
        adapter.doctors = doctors
    }

    private val adapterOnClickListener =
            object : DoctorsAdapter.DoctorsOnClickListener {
                override fun onClick(doctor: DoctorsModel) {
                    val bundle = bundleOf(ID_KEY_BUNDLE to doctor.id)
                    (activity as MainActivity).openDoctorMpDashboardFromMP(bundle)
                }
            }

    init {
        adapter = DoctorsAdapter(adapterOnClickListener)
    }

    companion object {
        const val ID_KEY_BUNDLE = "key_id"
        const val NAME_KEY_BUNDLE = "key_name"
    }
}