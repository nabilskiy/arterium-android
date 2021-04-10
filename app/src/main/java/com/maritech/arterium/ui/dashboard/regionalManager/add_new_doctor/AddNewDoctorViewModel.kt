package com.maritech.arterium.ui.dashboard.regionalManager.add_new_doctor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.*
import com.maritech.arterium.data.network.ArteriumDataProvider
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_doctor.AddNewDoctorActivity.Companion.TAG

class AddNewDoctorViewModel : ViewModel() {

    private val dataProvider = ArteriumDataProvider.getInstance()


    val drugPrograms = mutableSetOf<Int>()
    var region: Int = -1

    val selectedAgentLiveData = MutableLiveData<AgentModel?>()
    val agentsContentStateLiveData = MutableLiveData<ContentState>()
    val contentStateLiveData = MutableLiveData<ContentState>()
    val requestSuccessLiveData = MutableLiveData<Unit>()
    val agentsListLiveData = MutableLiveData<List<AgentModel>>()
    val regionsListLiveData = MutableLiveData<List<RegionModel>>()
    val regionsStateLiveData = MutableLiveData<ContentState>()


    fun create(doctor: CreateDoctorRequestModel) {
        contentStateLiveData.value = ContentState.LOADING
        dataProvider.createDoctor(doctor).subscribe(
                { data: CreateDoctorResponseModel ->
//                    if (data.data) {
//                        Log.i(TAG, "create: empty")
//                        contentStateLiveData.postValue(ContentState.ERROR)
//                        return@subscribe
//                    }
                    contentStateLiveData.postValue(ContentState.CONTENT)
                    requestSuccessLiveData.postValue(Unit)
                },
                { throwable: Throwable ->
                    // TODO: 08.04.2021 add exception message
                    Log.i(TAG, "create: EROR: ${throwable.message}")
                    contentStateLiveData.postValue(ContentState.ERROR)
                }

        )
    }

    fun getRegionsList() {
        regionsStateLiveData.value = ContentState.LOADING
        dataProvider.regions.subscribe(
                { data: RegionsResponseModel ->
                    if (data.data.isEmpty()) {
                        regionsStateLiveData.postValue(ContentState.ERROR)
                        return@subscribe
                    }
                    regionsStateLiveData.postValue(ContentState.CONTENT)
                    regionsListLiveData.postValue(data.data)
                },
                { throwable: Throwable ->
                    Log.i(TAG, "getRegionsList: EROR: ${throwable.message}")
                    regionsStateLiveData.postValue(ContentState.ERROR)
                }
        )
    }

    fun getAgentsList() {
        agentsContentStateLiveData.value = ContentState.LOADING
        dataProvider.agents.subscribe(
                { data: AgentResponseModel ->
                    if (data.data.isEmpty()) {
                        agentsContentStateLiveData.postValue(ContentState.ERROR)
                        return@subscribe
                    }
                    agentsContentStateLiveData.postValue(ContentState.CONTENT)
                    agentsListLiveData.postValue(data.data)
                },
                { throwable: Throwable ->
                    // TODO: 08.04.2021 add exception message
                    agentsContentStateLiveData.postValue(ContentState.ERROR)
                }
        )
    }

}