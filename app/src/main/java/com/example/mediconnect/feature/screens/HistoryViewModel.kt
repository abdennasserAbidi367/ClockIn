package com.example.mediconnect.feature.screens

import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.domain.entities.Comments
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.TopicRapport
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.usecase.GetListRateUseCase
import com.example.mediconnect.domain.usecase.GetListTopicUseCase
import com.example.mediconnect.domain.usecase.GetTopicByIdUseCase
import com.example.mediconnect.domain.usecase.SaveCommentUseCase
import com.example.mediconnect.domain.usecase.SaveRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getListTopicUseCase: GetListTopicUseCase,
    private val saveRateUseCase: SaveRateUseCase,
    private val saveCommentUseCase: SaveCommentUseCase,
    private val getTopicByIdUseCase: GetTopicByIdUseCase,
) : ViewModel() {

    val listRateById = MutableStateFlow<List<Rate>>(emptyList())

    val listTopicsById = MutableStateFlow<List<Topics>>(emptyList())

    fun getListTopics(userId: String) {
        viewModelScope.launch {
            listTopicsById.update {
                getTopicByIdUseCase.execute(userId)?.results ?: emptyList()
            }
            val s = listTopicsById.value.size
            Log.i("getTopicByIdUseCase", "getListTopics: $s")

        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        return sdf.format(Date())
    }

    val listNameTopics = MutableStateFlow<List<String>>(emptyList())

    val forToday = MutableStateFlow(false)

    fun changeForToday(isForToday: Boolean) {
        forToday.update {
            isForToday
        }
    }

    fun getTopicsDetail(list: List<Topics>, searchHistory: String) {
        val allSubject = mutableListOf<String>()
        Log.i("searchHistory", "getTopicsDetail: ${forToday.value}")
        Log.i("greklnhgje", "getTopicsDetail: $list")

        /*if (forToday.value) {
            list.mapIndexed { index, topics ->
                if (topics.dateWork == getCurrentDate()) {
                    val subject = "${topics.title} : ${topics.nbHour} hours"
                    allSubject.add(subject)
                }
            }
        } else {
            list.mapIndexed { index, topics ->
                val subject = "${topics.title} : ${topics.nbHour} hours"
                allSubject.add(subject)
            }
        }*/

        val ss = list.groupBy { it.title }


        if (forToday.value) {
            for ((key, value) in ss) {
                var iCount = 0
                Log.i("hahivalurssea", "getTopicsDetail: $value")
                value.map { topics ->
                    if (topics.dateWork == getCurrentDate()) {
                        iCount += topics.nbHour ?: 0
                    }
                }
                val subject = "$key : $iCount hours"
                allSubject.add(subject)
            }

        } else {
            for ((key, value) in ss) {
                var iCount = 0
                value.map { topics ->
                    iCount += topics.nbHour ?: 0
                }
                val subject = "$key : $iCount hours"
                allSubject.add(subject)
            }
        }

        val s = if (searchHistory.isNotEmpty()) allSubject.filter { txt -> txt.contains(searchHistory, true) }
        else allSubject

        listNameTopics.update {
            s
        }
    }

    val searchHistory = MutableStateFlow("")

    fun changeSearchHistory(text: String) {
        searchHistory.update { text }
    }

    val allRate = MutableStateFlow<List<Rate>>(emptyList())

    fun addRate(rating: Rate) {
        viewModelScope.launch {
            rating.comment?.map {
                val comments = Comments(it, id = ViewCompat.generateViewId(),rating.id ?: 0)
                saveCommentUseCase.execute(comments)
            }
        }

        viewModelScope.launch {
            saveRateUseCase.execute(rating)
        }
        val rate = allRate.value.toMutableList()
        rate.add(rating)
        allRate.update {
            rate
        }
    }

    val comments = MutableStateFlow<List<String>>(emptyList())

    fun addComments(rating: List<String>?) {
        comments.update {
            rating ?: emptyList()
        }
    }

}