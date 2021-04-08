package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.data.models.DoctorsResponseModel
import com.maritech.arterium.data.network.ArteriumDataProvider

class AddNewMpViewModel : ViewModel(){

    private val dataProvider = ArteriumDataProvider.getInstance()

    val doctorsStateLiveData = MutableLiveData<ContentState>()
    val doctors = MutableLiveData<List<DoctorsModel>>()

    fun getDoctors() {
        doctorsStateLiveData.value = ContentState.LOADING
        dataProvider.doctors.subscribe(
                { data: DoctorsResponseModel ->
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
    }

}