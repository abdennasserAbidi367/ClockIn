package com.example.mediconnect.feature.screens

import android.util.Log
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.domain.entities.Comments
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.usecase.DeleteRateUseCase
import com.example.mediconnect.domain.usecase.GetListCommentUseCase
import com.example.mediconnect.domain.usecase.GetListRateUseCase
import com.example.mediconnect.domain.usecase.SaveCommentUseCase
import com.example.mediconnect.domain.usecase.UpdateRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RateViewModel @Inject constructor(
    private val getListRateUseCase: GetListRateUseCase,
    private val deleteRateUseCase: DeleteRateUseCase,
    private val updateRateUseCase: UpdateRateUseCase,
    private val saveCommentUseCase: SaveCommentUseCase,
    private val getListCommentUseCase: GetListCommentUseCase
) : ViewModel() {

    val listRateById = MutableStateFlow<List<Rate>>(emptyList())

    val allRate = MutableStateFlow<List<Rate>>(emptyList())

    fun getRate(userId: String) {
        viewModelScope.launch {
            val list = getListRateUseCase.execute(userId)
            listRateById.update {
                list ?: emptyList()
            }
        }
    }

    val comments = MutableStateFlow<List<Comments>>(emptyList())
    val rates = MutableStateFlow(Rate())

    fun deleteRate(rate: Rate) {
        val list = listRateById.value.toMutableList()
        list.remove(rate)
        viewModelScope.launch {
            deleteRateUseCase.execute(rate.id)
            listRateById.update {
                list
            }
        }
    }

    fun addComments(rate: Rate) {
        val ls = comments.value.toMutableList()
        viewModelScope.launch {
            rate.comment?.map {
                val comments = Comments(it, id = ViewCompat.generateViewId(),rate.id ?: 0)
                ls.add(comments)
                val l = saveCommentUseCase.execute(comments)
                Log.i("rateComment", "addComments: $l")
            }
            rate.comment?.clear()
            comments.update {
                ls
            }
        }
    }

    fun updateRate(comment: Comments) {
        viewModelScope.launch {
            val messageRes = updateRateUseCase.execute(comment.id ?: 0)
            if (messageRes?.message == "Successfully deleted one document.") {
                val list = comments.value.toMutableList()
                list.remove(comment)
                comments.update {
                    list
                }
            }
        }
    }


    fun changeRate(rate: Rate) {
        Log.i("ngengne", "changeRate: $rate")

        rates.update {
            rate
        }

        viewModelScope.launch {
            Log.i("ngengne", "getComm: ${getListCommentUseCase.execute(rate.id)}")

            val list = getListCommentUseCase.execute(rate.id)
            comments.update {
                list ?: mutableListOf()
            }
        }


    }
}