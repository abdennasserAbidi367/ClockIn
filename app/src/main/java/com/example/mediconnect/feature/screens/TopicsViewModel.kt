package com.example.mediconnect.feature.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.domain.entities.AllTopics
import com.example.mediconnect.domain.entities.TimeLineTopic
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.usecase.ClearDataBaseUseCase
import com.example.mediconnect.domain.usecase.GetListTopicUseCase
import com.example.mediconnect.domain.usecase.SaveListTopicUseCase
import com.example.mediconnect.domain.usecase.SaveTopicUseCase
import com.example.mediconnect.domain.usecase.loginUseCase
import com.example.mediconnect.domain.usecase.registerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(private val saveTopicUseCase: SaveTopicUseCase,
                                          private val saveListTopicUseCase: SaveListTopicUseCase,
                                          private val getListTopicUseCase: GetListTopicUseCase,
                                          private val clearDataBaseUseCase: ClearDataBaseUseCase
) : ViewModel(){

    private val timeLine = MutableStateFlow(TimeLineTopic())
    val uiState = timeLine.asStateFlow()

    val listTopics = MutableStateFlow<MutableList<Topics>>(mutableListOf())

    val isCheckedStep1 = MutableStateFlow(false)
    val isCheckedStep2 = MutableStateFlow(false)
    val isCheckedStep3 = MutableStateFlow(false)

    val step1 = MutableStateFlow(true)
    val step2 = MutableStateFlow(false)
    val step3 = MutableStateFlow(false)


    fun resetSteps() {
        /*timeLine.update {
            it.isCheckedStep1 = false
            it.isCheckedStep2 = false
            it.isCheckedStep3 = false

            it.step1 = true
            it.step2 = false
            it.step3 = false
            it
        }*/

        isCheckedStep1.update { false }
        isCheckedStep2.update { false }
        isCheckedStep3.update { false }

        step1.update { true }
        step2.update { false }
        step3.update { false }
    }

    /*fun updateStep1() {
        timeLine.update {
            it.isCheckedStep1 = true

            it.step1 = false
            it.step2 = true
            it
        }
    }

    fun updateStep2() {
        timeLine.update {
            it.isCheckedStep2 = true

            it.step2 = false
            it.step3 = true
            it
        }
    }

    fun updateStep3() {
        timeLine.update {
            it.isCheckedStep3 = true

            it.step3 = false
            it
        }
    }*/

    fun updateStep1(timeLineUpdate: Boolean ? = false) {
        isCheckedStep1.update { true }

        if (timeLineUpdate == true) {
            isCheckedStep2.update { false }
            isCheckedStep3.update { false }
        }

        step1.update { false }

        step2.update { true }

        step3.update { false }
    }

    fun updateStep2() {
        isCheckedStep2.update { true }

        step1.update { false }

        step2.update { false }

        step3.update { true }
    }

    fun updateStep3() {
        isCheckedStep3.update { true }

        step3.update { false }
    }

    fun saveTopicChoosed(topics: Topics) {
        listTopics.update {
            if (isContain(it, topics.title ?: "")) it.add(topics)
            it
        }
    }

    fun isContain(list: MutableList<Topics>, title: String): Boolean {
        return list.filter { it.title == title }.none()
    }

    fun removeTopicChoosed(topics: Topics) {
        listTopics.update {
            if (!isContain(it, topics.title ?: "")) it.remove(topics)
            it
        }
    }

    fun getTopicsDetail(list: List<Topics>): MutableList<String> {
        val allSubject = mutableListOf<String>()
        list.map {
            //allSubject.add("${it.title} From ${it.hourStart} To ${it.hourEnd}")
            allSubject.add("${it.title} : ${it.nbHour} hours")
        }
        return allSubject
    }

    val listTopicsTitle = MutableStateFlow<MutableList<String>>(mutableListOf())
    val hourStartState = MutableStateFlow<MutableList<String>>(mutableListOf())
    val hourEndState = MutableStateFlow<MutableList<String>>(mutableListOf())
    val durations = MutableStateFlow<MutableList<String>>(mutableListOf())
    val durationHourState = MutableStateFlow<MutableList<String>>(mutableListOf())

    val startState = MutableStateFlow("")
    val endState = MutableStateFlow("")
    val durationState = MutableStateFlow("")
    val keepIndexed = MutableStateFlow(0)

    fun changeDuration(duration: String, index: Int) {
        val s = durations.value
        s[index] = duration
        durations.update {
            s
        }
    }

    fun changeHourStart(hour: String, index: Int) {
        startState.update {
            "${index}-$hour"
        }
        durationState.update {
            "${index}-$hour"
        }

        val q = durationHourState.value
        q[index] = hour
        durationHourState.update {
            q
        }

        val s = hourStartState.value
        s[index] = hour
        hourStartState.update {
            s
        }
    }

    fun changeHourEnd(hour: String, index: Int) {
        endState.update {
            "${index}-$hour"
        }
        val s = hourEndState.value
        s[index] = hour
        hourEndState.update {
            s
        }
    }

    fun getTopicsTitle() {
        val list = mutableListOf<String>()
        val hourStart = mutableListOf<String>()
        val hourEnd = mutableListOf<String>()
        val durationPeriod = mutableListOf<String>()

        if (hourStartState.value.size == 0 || (hourStartState.value.size != listTopics.value.size)) {
            listTopics.value.map {
                hourStart.add("0h")
                hourEnd.add("0h")
                durationPeriod.add("0")
            }

            hourStartState.update {
                hourStart
            }
            hourEndState.update { hourEnd }

            durations.update {
                durationPeriod
            }
        }

        Log.i("getTopicsTitle", "hourStartState: ${hourStartState.value.size}")
        Log.i("getTopicsTitle", "listTopics: ${listTopics.value.size}")

        listTopics.value.mapIndexed { index, topics ->
            list.add(topics.title ?: "")
        }

        listTopicsTitle.update {
            list
        }
    }

    val timeOfTheDay = MutableStateFlow(
        listOf("7h", "8h","9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h", "17h", "18h", "19h")
    )

    fun checkHourValidity(start: String, end: String): Boolean {
        Log.i("splitHour", "checkHourValidity: ${start.split("h")}")
        val hourStart = start.split("h")[0].toInt()
        val hourEnd = end.split("h")[0].toInt()

        return hourStart < hourEnd
    }

    fun getNumberHour(start: String, end: String): Int {
        Log.i("splitHour", "checkHourValidity: ${start.split("h")}")
        val hourStart = start.split("h")[0].toInt()
        val hourEnd = end.split("h")[0].toInt()

        return hourEnd - hourStart

    }

    val sq = MutableStateFlow(
        listOf(Topics(1, "Thrust Reverser RH", isChecked = false),
            Topics(2, "Randome", isChecked = false),
            Topics(3, "FSM", isChecked = false),
            Topics(4, "Nacelle", isChecked = false),
            Topics(5, "cdscvs", isChecked = false),
            Topics(6, "frzgrzg", isChecked = false))
    )

    val normalList = MutableStateFlow<List<Topics>>(emptyList())

    fun changeTopics(list: List<Topics>) {
        normalList.update {
            list
        }
        sq.update {
            list
        }
    }

    fun checkTopic(index: Int) {
        sq.update {
            it[index].isChecked = it[index].isChecked == false
            if (it[index].isChecked == true) saveTopicChoosed(it[index]) else removeTopicChoosed(it[index])
            it
        }
    }

    fun search(word: String) {
        var listWord: List<Topics>
        if (word.isEmpty()) {
            sq.update {
                normalList.value
            }
        } else {
            sq.update {
                listWord = it.filter { topic ->
                    topic.title?.contains(word) == true
                }
                listWord
            }
        }
    }

    fun saveTopics(topics: Topics) {
        viewModelScope.launch {
            saveTopicUseCase.execute(topics)
            Log.i("finalT", "saveListTopics: ${saveTopicUseCase.execute(topics)}")
        }
    }

    fun saveListTopics(topics: Topics) {
        viewModelScope.launch {
            saveListTopicUseCase.execute(topics)
        }
    }

    val listTopicsById = MutableStateFlow<List<Topics>>(emptyList())

    fun getListTopics(userId: String) {
        viewModelScope.launch {
            listTopicsById.update {
                getListTopicUseCase.execute(userId) ?: emptyList()
            }
        }
    }

    fun clearDataBase() {
        viewModelScope.launch {
            clearDataBaseUseCase.execute()
        }
    }
}

