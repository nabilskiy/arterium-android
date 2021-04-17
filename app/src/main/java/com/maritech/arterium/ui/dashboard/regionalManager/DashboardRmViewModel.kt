package com.maritech.arterium.ui.dashboard.regionalManager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maritech.arterium.common.ContentState
import com.maritech.arterium.data.models.AgentModel
import com.maritech.arterium.data.models.AgentResponseModel
import com.maritech.arterium.data.network.ArteriumDataProvider
import com.maritech.arterium.ui.base.SingleLiveEvent

class DashboardRmViewModel : ViewModel() {

    val agentsLiveData: MutableLiveData<List<AgentModel>> = MutableLiveData()
    val agentsStateViewModel: MutableLiveData<ContentState> = MutableLiveData()
    val agentsLiveDataError: MutableLiveData<String> = MutableLiveData()
    var searchQuery = SingleLiveEvent<String>()

    val dataProvider = ArteriumDataProvider.getInstance()

    fun getAgents(search: String) {
        agentsStateViewModel.value = ContentState.LOADING
        dataProvider.agents.subscribe(
                { data: AgentResponseModel? ->
                    data ?: return@subscribe
                    if (data.data.isEmpty()) return@subscribe
                    agentsStateViewModel.postValue(ContentState.CONTENT)
                    agentsLiveData.postValue(data.data)
                },
                { throwable: Throwable? ->
                    agentsStateViewModel.postValue(ContentState.ERROR)
                    agentsLiveDataError.postValue(throwable?.message)
                }
        )
    }

    fun getAgents() {
        getAgents("")
    }

}