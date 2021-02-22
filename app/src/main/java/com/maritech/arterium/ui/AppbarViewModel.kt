package com.maritech.arterium.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.maritech.arterium.utils.Event

class AppbarViewModel : ViewModel() {
    val currentNavController = MutableLiveData<Event<NavController>>()
}