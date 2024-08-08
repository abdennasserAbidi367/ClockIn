package com.example.mediconnect

import android.app.Application
import android.util.Log
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.domain.usecase.ClearUserUseCase
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
    @Inject
    lateinit var clearUserUseCase: ClearUserUseCase

    override fun onCreate() {
        super.onCreate()
        val listId = getListId(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            val list = getListUserUseCase.execute() ?: emptyList()
            val admin = list.filter { it.role == "admin" }.toMutableList()
            Log.i("compareLists", "list: ${list.size}")
            Log.i("compareLists", "listId: ${listId.size}")
            Log.i("compareLists", "onCreate: ${canChangeList(listId, list)}")

            if (canChangeList(listId, list)) {
                clearUserUseCase.execute()
                listId.map {
                    saveUserUseCase.execute(it)
                }
            }
        }
    }

    private fun canChangeList(l1: List<User>, l2: List<User>): Boolean {
        return l1.isNotEmpty() && l1 != l2
    }
}