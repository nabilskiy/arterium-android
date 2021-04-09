package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.*
import com.maritech.arterium.data.network.ArteriumDataProvider
import com.maritech.arterium.ui.base.SingleLiveEvent

class AddNewMpViewModel : ViewModel() {

    private val TAG = "AddNewMpActivity_TAG";


    private val dataProvider = ArteriumDataProvider.getInstance()

    val doctorsStateLiveData = MutableLiveData<ContentState>()
    val doctors = MutableLiveData<List<DoctorsModel>>(listOf())
    val selectedDoctors = MutableLiveData<List<DoctorsModel>>(listOf())
    val saveResponseLiveData = MutableLiveData<Unit>()
    val contentState = MutableLiveData<ContentState>()

    fun getDoctorsList() {
        doctorsStateLiveData.value = ContentState.LOADING
        dataProvider.doctors.subscribe(
                { data: DoctorsResponseModel ->
                    Log.i(TAG, "getDoctorsList: ")
                    if (data.data.isEmpty())
                        return@subscribe
                    doctorsStateLiveData.postValue(ContentState.CONTENT)
                    doctors.postValue(data.data)
                },
                { throwable: Throwable ->
                    // TODO: 08.04.2021 add exception message
                    doctorsStateLiveData.postValue(ContentState.ERROR)
                }
        )
    }

    fun notifyDoctorsSetChanged() {
        doctors.postValue(doctors.value)
        selectedDoctors.postValue(doctors.value?.filter { it.selected })
    }

    fun save(agent:AgentRequestModel) {
        contentState.value = ContentState.LOADING
        dataProvider.saveAgent(agent).subscribe(
                { data: CreateAgentResponseModel ->
                    if (data!=null && data.error!=null && data.error.isNotEmpty()) {
                        Log.i(TAG, "save: error")
                        contentState.postValue(ContentState.ERROR)
                        return@subscribe
                    }
                    Log.i(TAG, "save: OK")
                    addDoctors(data.data.id)
                },
                { throwable: Throwable ->
                    Log.i(TAG, "save: ERROR")
                    contentState.postValue(ContentState.ERROR)
                }
        )
    }

    private fun addDoctors(id:Int) {
        Log.i(TAG, "addDoctors: ")
        dataProvider.addDoctors(id, AddDoctorsRequestModel(getDoctorsIds())).subscribe(
                { data: BaseResponse ->
                    if (data.isSuccess) {
                        contentState.postValue(ContentState.CONTENT)
                        saveResponseLiveData.postValue(Unit)
                    } else
                        contentState.postValue(ContentState.ERROR)
                },
                { throwable: Throwable ->
                    contentState.postValue(ContentState.ERROR)
                    Log.i(TAG, "addDoctors: ${throwable.message}")
                }
        )
    }

    private fun getDoctorsIds():List<Int> {
        val list = mutableListOf<Int>()
        for (doctor in selectedDoctors.value ?: listOf()) {
            list.add(doctor.id)
        }
        return list.toList()
    }



}