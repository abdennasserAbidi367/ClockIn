package com.example.mediconnect.feature.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.domain.usecase.ClearUserUseCase
import com.example.mediconnect.domain.usecase.GetListUserUseCase
import com.example.mediconnect.domain.usecase.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val getListUserUseCase: GetListUserUseCase
): ViewModel() {

    val allUser = MutableStateFlow(listOf(User("grghrg", username = "gjrzgjrz")))

    fun getAllUser() {
        viewModelScope.launch {
            allUser.update {
                getListUserUseCase.execute() ?: emptyList()
            }
        }
    }

    val savingMessage = MutableStateFlow("loading")

    fun saveUser(user: User) {
        viewModelScope.launch {
            savingMessage.update {
                saveUserUseCase.execute(user) ?: ""
            }
        }
    }
}