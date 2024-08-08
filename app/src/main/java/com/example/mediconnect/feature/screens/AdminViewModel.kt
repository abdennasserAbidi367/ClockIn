package com.example.mediconnect.feature.screens

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.domain.entities.TopicRapport
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.domain.usecase.GetListUserUseCase
import com.example.mediconnect.domain.usecase.GetTopicByDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val getTopicByDateUseCase: GetTopicByDateUseCase,
    private val getListUserUseCase: GetListUserUseCase
) : ViewModel() {

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        return sdf.format(Date())
    }

    val listTopicsByDate = MutableStateFlow<List<Topics>>(emptyList())
    val listTopicsRapport = MutableStateFlow<List<TopicRapport>>(emptyList())

    init {
        getListTopics(getCurrentDate())
    }

    fun getListTopics(date: String) {
        val listRapportTopic = mutableListOf<TopicRapport>()
        viewModelScope.launch {
            val s = getTopicByDateUseCase.execute(date)?.results ?: emptyList()
            val ss = s.groupBy { it.title }
            for ((key, value) in ss) {
                val tr = TopicRapport()
                tr.id = View.generateViewId()
                tr.title = key

                var workedUser = ""

                value.map {
                    val listUser = getListUserUseCase.execute() ?: emptyList()
                    if (listUser.isNotEmpty()) {
                        val u = listUser.findLast { l -> l.id == it.idUser } ?: User("", username = "")
                        if (u.id.isNotEmpty()) workedUser += "The user ${u.username}  worked  ${it.nbHour} hours \n"
                    }
                    tr.idUsers?.add(it.idUser ?: "")
                    tr.nbHours?.add(it.nbHour ?: 0)
                }

                tr.workedUser = workedUser
                listRapportTopic.add(tr)
            }
            Log.i("listRapportTopic", "getListTopics: $listRapportTopic")
            listTopicsRapport.update {
                listRapportTopic
            }

            listTopicsByDate.update {
                getTopicByDateUseCase.execute(date)?.results ?: emptyList()
            }
        }
    }
}