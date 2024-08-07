package com.example.mediconnect

import android.app.Application
import android.util.Log
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.domain.usecase.GetListUserUseCase
import com.example.mediconnect.domain.usecase.SaveUserUseCase
import com.example.mediconnect.feature.screens.getListId
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application(){

    @Inject
    lateinit var saveUserUseCase: SaveUserUseCase
    @Inject
    lateinit var getListUserUseCase: GetListUserUseCase

    override fun onCreate() {
        super.onCreate()
        val listId = getListId(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            val list = getListUserUseCase.execute() ?: emptyList()
            val admin = list.filter { it.role == "admin" }.toMutableList()
            if (list.isEmpty()) {
                listId.map {
                    saveUserUseCase.execute(it)
                }
            } else if (admin.isEmpty()) {
                val userAdmin = User(id = "AAAAAA", username = "Ala", role = "admin")
                saveUserUseCase.execute(userAdmin)
            }
        }
    }
}