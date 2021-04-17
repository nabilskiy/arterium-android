package com.maritech.arterium.ui.dashboard.medicalRep

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maritech.arterium.R
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.AgentModel
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.data.models.Profile
import com.maritech.arterium.data.sharePref.Pref
import com.maritech.arterium.databinding.FragmentDashboardMpBinding
import com.maritech.arterium.ui.MainActivity
import com.maritech.arterium.ui.base.BaseFragment
import com.maritech.arterium.ui.dashboard.doctor.DashboardViewModel
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel
import com.maritech.arterium.ui.dashboard.medicalRep.MPViewModel.Companion.TAG
import java.util.ArrayList

class DashboardMpFragment : BaseFragment<FragmentDashboardMpBinding?>() {

    private var isFromRM = false
    private var agentId: Int = -1
    private var name: String? = null


    private var navigator = DashboardMpNavigator()

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var viewModel: MPViewModel

    private var adapter: DoctorsAdapter

    private var searchQuery: String? = null
    private var allList = ArrayList<DoctorsModel>()
    private var filteredList = ArrayList<DoctorsModel>()

    override fun getContentView(): Int {
        return R.layout.fragment_dashboard_mp
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)
        Log.i(DashboardViewModel.TAG, "onViewCreated: MP")
        Pref.getInstance().setDrugProgramId(requireActivity(), 1);
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
        viewModel.searchQuery.observe(lifecycleOwner, this::search)
        profileViewModel.responseLiveData.observe(lifecycleOwner, this::observeProfileResponse)
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
            binding!!.details.etSearch.setText("")
        }
        binding!!.details.etSearch.addTextChangedListener(textWatcher)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            viewModel.searchQuery.value = s.toString()
        }
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
        binding!!.details.tvDoctors.text = getString(R.string.constraint_doctors)
        allList = doctors as ArrayList<DoctorsModel>
        filteredList = doctors
        setAdapter(allList)
    }

    private fun setAdapter(doctors: List<DoctorsModel>){
        adapter.doctors = doctors.asReversed()
    }

    private val adapterOnClickListener =
            object : DoctorsAdapter.DoctorsOnClickListener {
                override fun onClick(doctor: DoctorsModel) {
                    val bundle = bundleOf(ID_KEY_BUNDLE to doctor.id)
                    (activity as MainActivity).openDoctorDashboardFromMP(bundle)
                }
            }

    init {
        adapter = DoctorsAdapter(adapterOnClickListener)
    }


    private fun search(query: String){
        searchQuery = query
        if (searchQuery!!.isEmpty()) {
            setAdapter(allList)
        } else {
            val models = ArrayList<DoctorsModel>()
            for (model in filteredList) {
                if (model.name.toLowerCase().contains(searchQuery!!.toLowerCase())) {
                    models.add(model)
                }
            }
            setAdapter(models)
        }
    }

    companion object {
        const val ID_KEY_BUNDLE = "key_id"
        const val NAME_KEY_BUNDLE = "key_name"
    }
}