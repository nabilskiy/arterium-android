package com.maritech.arterium.ui.dashboard.regionalManager.add_new_doctor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.maritech.arterium.R
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.AgentModel
import com.maritech.arterium.data.models.CreateDoctorRequestModel
import com.maritech.arterium.data.models.RegionModel
import com.maritech.arterium.databinding.ActivityAddNewDoctorBinding
import com.maritech.arterium.ui.base.BaseActivity

class AddNewDoctorActivity : BaseActivity<ActivityAddNewDoctorBinding?>() {

    companion object {
        const val TAG = "ADD_NEW_DOCTOR_TAG"
    }

    private var currentStep = 0

    private lateinit var viewModel: AddNewDoctorViewModel
    private val adapter: MPAdapter
    private val regionsAdapter: RegionsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initListeners()
    }


    fun init() {
        viewModel = ViewModelProvider(this).get(AddNewDoctorViewModel::class.java)
        binding!!.toolbar.tvToolbarTitle.text = getString(R.string.new_doctor)
        binding!!.ccInputRegion
        changeSecondStepNextButtonState()
        changeFirstStepNextButtonState()
        viewModel.getAgentsList()
        viewModel.getRegionsList()
        showFirstStep()
    }

    fun initListeners() {
        binding!!.toolbar.ivArrow.setOnClickListener { goBack() }
        binding!!.btnNextOne.setOnClickListener { showSecondStep() }
        binding!!.btnNextTwo.setOnClickListener { save() }
        binding!!.clChooseMp.setOnClickListener { showMPsList() }
        binding!!.ccInputRegion.setOnClickListener { showRegionsList() }
        binding!!.ivRemove.setOnClickListener { viewModel.selectedAgentLiveData.value = null }

        binding!!.clRenial.setOnClickListener { checkboxOnClick(binding!!.ivBtnCheckOne, PROGRAM_RENIAL) }
        binding!!.clGliptar.setOnClickListener { checkboxOnClick(binding!!.ivBtnCheckTwo, PROGRAM_GLIPTAR) }
        binding!!.clSagrada.setOnClickListener { checkboxOnClick(binding!!.ivBtnCheckThree, PROGRAM_SAGRADA) }

        viewModel.agentsContentStateLiveData.observe(this, this::observeMPContentState)
        viewModel.contentStateLiveData.observe(this, this::observeContentState)
        viewModel.selectedAgentLiveData.observe(this, this::observeSelectedAgent)
        viewModel.requestSuccessLiveData.observe(this, this::observeSaveResult)
        viewModel.agentsListLiveData.observe(this, this::observeAgentsList)
        viewModel.regionsStateLiveData.observe(this, this::observeRegionsContentState)
        viewModel.regionsListLiveData.observe(this, this::observeRegionsList)

        binding!!.ccInputCity.addTextChangeListener(textWatcher)
        binding!!.ccInputName.addTextChangeListener(textWatcher)
        binding!!.ccInputPhoneNumber.addTextChangedListener(textWatcher)
        binding!!.etRegion.addTextChangedListener(textWatcher)
        binding!!.ccInputSecondName.addTextChangeListener(textWatcher)
        binding!!.ccInputNameCorporation.addTextChangeListener(textWatcher)

    }

    private fun checkboxOnClick(imageView: ImageView, program: Int) {
        if (viewModel.drugPrograms.contains(program)) {
            viewModel.drugPrograms.remove(program)
            imageView.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_check_btn_square_disactive))
        } else {
            viewModel.drugPrograms.add(program)
            imageView.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_check_btn_square_active))
        }
        changeSecondStepNextButtonState()
    }

    private fun save() {
        val agentId: String =
                if (viewModel.selectedAgentLiveData.value != null)
                    viewModel.selectedAgentLiveData.value!!.id.toString()
                else ""
        val doctor = CreateDoctorRequestModel(
                "380" + binding!!.ccInputPhoneNumber.rawText,
                binding!!.ccInputName.text + " " + binding!!.ccInputSecondName.text,
                if (binding!!.radioGroup.checkedRadioButtonId == R.id.radio_female) "f" else "m",
                viewModel.region.toString(),
                binding!!.ccInputCity.text,
                binding!!.ccInputNameCorporation.text,
                agentId,
                viewModel.drugPrograms
        )
        viewModel.create(doctor)
    }

    private fun observeAgentsList(agents: List<AgentModel>) {
        Log.i(TAG, "observeAgentsList: ${agents.size}")
        adapter.agents = agents
    }


    private fun showFirstStep() {
        currentStep = 0
        binding!!.clProgressStepOne.visibility = View.VISIBLE
        binding!!.clProgressStepTwo.visibility = View.GONE
        binding!!.toolbar.tvHint.text = getString(R.string.personal_data)
        binding!!.toolbar.viewTwo.isActivated = false
        binding!!.toolbar.viewOne.isActivated = true
    }

    private fun showSecondStep() {
        currentStep = 1
        binding!!.toolbar.viewTwo.isActivated = true
        binding!!.toolbar.llSearch.visibility = View.GONE
        binding!!.toolbar.llProgress.visibility = View.VISIBLE
        binding!!.toolbar.tvHint.text = getString(R.string.working_data)
        binding!!.toolbar.tvToolbarTitle.text = getString(R.string.new_doctor)
        binding!!.toolbar.ivDone.visibility = View.GONE
        binding!!.toolbar.tvHint.visibility = View.VISIBLE
        binding!!.clProgressStepTwo.visibility = View.VISIBLE
        binding!!.clProgressStepOne.visibility = View.GONE
        binding!!.thirdStepCL.visibility = View.GONE
        binding!!.regionsCL.visibility = View.GONE

    }

    private fun showMPsList() {
        currentStep = 2
        binding!!.toolbar.llProgress.visibility = View.GONE
        binding!!.toolbar.llSearch.visibility = View.VISIBLE
        binding!!.clProgressStepTwo.visibility = View.GONE
        binding!!.thirdStepCL.visibility = View.VISIBLE
        binding!!.toolbar.tvToolbarTitle.text = getString(R.string.choose_mp)
        binding!!.toolbar.tvHint.visibility = View.INVISIBLE
        if (binding!!.rvMP.adapter == null)
            binding!!.rvMP.adapter = adapter
        Log.i(TAG, "showMPsList: ${viewModel.agentsListLiveData.value?.size}")
        adapter.agents = viewModel.agentsListLiveData.value ?: listOf()
    }

    private fun showRegionsList() {
        currentStep = 3
        binding!!.toolbar.llProgress.visibility = View.GONE
        binding!!.toolbar.llSearch.visibility = View.GONE
        binding!!.clProgressStepTwo.visibility = View.GONE
        binding!!.regionsCL.visibility = View.VISIBLE
        binding!!.toolbar.tvToolbarTitle.text = getString(R.string.choose_region)
        binding!!.toolbar.tvHint.visibility = View.INVISIBLE
        if (binding!!.rvRegions.adapter == null)
            binding!!.rvRegions.adapter = regionsAdapter
        regionsAdapter.regions = viewModel.regionsListLiveData.value ?: listOf()
        Log.i(TAG, "showRegionsList: ${viewModel.regionsListLiveData.value?.size}")
    }

    private fun goBack() {
        when (currentStep) {
            0 -> finish()
            1 -> showFirstStep()
            2 -> showSecondStep()
            3 -> showSecondStep()
        }
    }

    private fun isFirstStepValid(): Boolean {
        return binding!!.ccInputName.text!!.isNotEmpty() &&
                binding!!.ccInputSecondName.text!!.isNotEmpty() &&
                binding!!.ccInputPhoneNumber.rawText!!.isNotEmpty() &&
                binding!!.ccInputPhoneNumber.rawText.length >= 9
    }

    private fun isSecondStepValid(): Boolean {
//        Log.i(TAG, "isSecondStepValid: ${binding!!.ccInputRegion.text.isNotEmpty()}" +
//                " ${binding!!.ccInputCity.text.isNotEmpty()}" +
//                " ${binding!!.ccInputNameCorporation.text.isNotEmpty()}" +
//                " ${viewModel.selectedAgentLiveData.value != null}" +
//                " ${viewModel.drugPrograms.isNotEmpty()}")
        return binding!!.etRegion.text.isNotEmpty() &&
                binding!!.ccInputCity.text.isNotEmpty() &&
                binding!!.ccInputNameCorporation.text.isNotEmpty() &&
                viewModel.drugPrograms.isNotEmpty()
    }

    private fun changeFirstStepNextButtonState() {
        if (isFirstStepValid()) {
            binding!!.btnNextOne.isEnabled = true
            binding!!.btnNextOne.alpha = 1.0f
        } else {
            binding!!.btnNextOne.isEnabled = false
            binding!!.btnNextOne.alpha = 0.7f
        }
    }

    private fun changeSecondStepNextButtonState() {
        if (isSecondStepValid()) {
            binding!!.btnNextTwo.isEnabled = true
            binding!!.btnNextTwo.alpha = 1.0f
        } else {
            binding!!.btnNextTwo.isEnabled = false
            binding!!.btnNextTwo.alpha = 0.7f
        }
    }

    private fun observeSaveResult(u: Unit) {
        finish()
    }

    private fun observeSelectedAgent(agent: AgentModel?) {
        if (agent == null) {
            binding!!.clAgent.visibility = View.GONE
            binding!!.clChooseMp.visibility = View.VISIBLE
        } else {
            binding!!.clAgent.visibility = View.VISIBLE
            binding!!.clChooseMp.visibility = View.INVISIBLE
            binding!!.tvAgentName.text = agent.name
            showSecondStep()
            changeSecondStepNextButtonState()
        }
    }

    private fun observeRegionsContentState(state: ContentState) {
        when (state) {
            ContentState.LOADING -> binding!!.regionsProgress.visibility = View.VISIBLE
            else -> binding!!.regionsProgress.visibility = View.GONE
        }
    }

    private fun observeRegionsList(regions: List<RegionModel>) {
        regionsAdapter.regions = regions
    }

    private fun observeMPContentState(state: ContentState) {
        when (state) {
            ContentState.LOADING -> binding!!.mpProgress.visibility = View.VISIBLE
            else -> binding!!.mpProgress.visibility = View.GONE
        }
    }

    private fun observeContentState(state: ContentState) {
        when (state) {
            ContentState.ERROR -> {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                binding!!.frameProgress.visibility = View.GONE
            }
            ContentState.LOADING -> binding!!.frameProgress.visibility = View.VISIBLE
            ContentState.CONTENT -> binding!!.frameProgress.visibility = View.GONE
            else -> binding!!.frameProgress.visibility = View.GONE
        }
    }

    private val adapterOnClickListener =
            object : MPAdapter.MPOnClickListener {
                override fun onClick(position: Int) {
                    viewModel.selectedAgentLiveData.value = viewModel.agentsListLiveData.value?.get(position)
                }
            }

    init {
        adapter = MPAdapter(adapterOnClickListener)
    }

    private val regionOnClickListener =
            object : RegionsAdapter.RegionOnClickListener {
                override fun onClick(position: Int) {
                    binding!!.etRegion
                            .setText(viewModel.regionsListLiveData.value?.get(position)?.name)
                    viewModel.region = viewModel.regionsListLiveData.value?.get(position)?.id ?: -1
                    showSecondStep()
                }
            }

    init {
        regionsAdapter = RegionsAdapter(regionOnClickListener)
    }


    private val textWatcher =
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    changeFirstStepNextButtonState()
                    changeSecondStepNextButtonState()
                }
            }


    override fun getLayoutId(): Int {
        return R.layout.activity_add_new_doctor
    }
}