package com.maritech.arterium.ui.dashboard.doctor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.data.network.ArteriumDataProvider

class DashboardViewModel : ViewModel() {

    companion object {
        const val TAG = "DOCTOR_DASHBOARD_TAG"
    }

    private val dataProvider = ArteriumDataProvider.getInstance()

    val doctorLiveData = MutableLiveData<DoctorsModel>()
    val doctorViewStateLiveData = MutableLiveData<ContentState>()

    fun getDoctor(id: Int) {
        doctorViewStateLiveData.value = ContentState.LOADING
        dataProvider.getDoctorById(id).subscribe(
                { data ->
//                    if(data.data.){
//                        doctorViewStateLiveData.postValue(ContentState.ERROR)
//                        return@subscribe
//                    }
                    doctorViewStateLiveData.postValue(ContentState.CONTENT)
                    doctorLiveData.postValue(data.data)
                },
                { throwable ->
                    Log.i(TAG, "getDoctor: ${throwable.message}")
                    doctorViewStateLiveData.postValue(ContentState.ERROR)
                }
        )
    }
}