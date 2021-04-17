package com.maritech.arterium.ui.dashboard.medicalRep

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.DoctorsModel
import com.maritech.arterium.data.models.DoctorsResponseModel
import com.maritech.arterium.data.network.ArteriumDataProvider
import com.maritech.arterium.ui.base.SingleLiveEvent

class MPViewModel : ViewModel() {

    companion object {
        const val TAG = "MP_DASHBOARD_TAG"
    }

    private val dataProvider = ArteriumDataProvider.getInstance()


    val doctorsLiveData = MutableLiveData<List<DoctorsModel>>()
    val doctorsViewStateLiveData = MutableLiveData<ContentState>()
    var searchQuery = SingleLiveEvent<String>()

    fun getDoctors() {
        doctorsViewStateLiveData.value = ContentState.LOADING
        dataProvider.doctors.subscribe(
                { data: DoctorsResponseModel ->
                    if (data.data.isEmpty()) {
                        doctorsViewStateLiveData.postValue(ContentState.ERROR)
                        return@subscribe
                    }
                    doctorsViewStateLiveData.postValue(ContentState.CONTENT)
                    doctorsLiveData.postValue(data.data)
                },
                {
                    doctorsViewStateLiveData.postValue(ContentState.ERROR)
                    Log.i(TAG, "getDoctors: ${it.message}")
                }
        )
    }

    fun getDoctorsById(id: Int) {
        Log.i(TAG, "getDoctorsById: $id")
        doctorsViewStateLiveData.value = ContentState.LOADING
        dataProvider.getDoctorsById(id).subscribe(
                { data: DoctorsResponseModel ->
                    if (data.data.isEmpty()) {
                        doctorsViewStateLiveData.postValue(ContentState.ERROR)
                        return@subscribe
                    }
                    doctorsViewStateLiveData.postValue(ContentState.CONTENT)
                    doctorsLiveData.postValue(data.data)
                },
                {
                    doctorsViewStateLiveData.postValue(ContentState.ERROR)
                    Log.i(TAG, "getDoctors: ${it.message}")
                }
        )
    }


}