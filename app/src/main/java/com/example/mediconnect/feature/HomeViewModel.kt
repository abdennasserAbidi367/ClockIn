package com.example.mediconnect.feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.domain.usecase.ClearDataBaseUseCase
import com.example.mediconnect.domain.usecase.GetListTopicUseCase
import com.example.mediconnect.domain.usecase.loginUseCase
import com.example.mediconnect.domain.usecase.registerUseCase
import com.example.mediconnect.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clearDataBaseUseCase: ClearDataBaseUseCase,
    var dataStore: DataStoreManager
) : ViewModel() {


    var clock = MutableStateFlow("")
    var logout = MutableStateFlow(false)
    var chooseTopic = MutableStateFlow(false)

    init {
        logout.update { false }
        chooseTopic.update { false }
        Log.i("logout", ": ${logout.value}")
    }

    fun getDate(): String = "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)} : ${
        Calendar.getInstance().get(Calendar.MINUTE)
    }"

    fun getClock() {
        viewModelScope.launch {
            dataStore.getClock.collectLatest {
                clock.value = it ?: ""
            }
        }
    }

    fun clearDataBase() {
        viewModelScope.launch {
            clearDataBaseUseCase.execute()
        }
    }
}

