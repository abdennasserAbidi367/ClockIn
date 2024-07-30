package com.example.mediconnect.feature

import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.common.TextFieldState
import com.example.mediconnect.domain.entities.AuthResult
import com.example.mediconnect.domain.entities.TimeLineTopic
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.usecase.loginUseCase
import com.example.mediconnect.domain.usecase.registerUseCase
import com.example.mediconnect.local.datastore.DataStoreManager
import com.example.mediconnect.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val loginUseCase: loginUseCase,
                                        private val registerUseCase: registerUseCase
) : ViewModel(){

    @Inject
    lateinit var dataStore: DataStoreManager

    val isCheckedStep1 = MutableStateFlow(false)
    val isCheckedStep2 = MutableStateFlow(false)
    val isCheckedStep3 = MutableStateFlow(false)

    val step1 = MutableStateFlow(true)
    val step2 = MutableStateFlow(false)
    val step3 = MutableStateFlow(false)

    private val timeLine = MutableStateFlow(TimeLineTopic())
    val uiState = timeLine.asStateFlow()

    val listTopics = MutableStateFlow<MutableList<Topics>>(mutableListOf())


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _authResult = MutableStateFlow<AuthResult?>(null)
    val authResult: StateFlow<AuthResult?> get() = _authResult

    private val _emailState = MutableStateFlow(TextFieldState())
    val emailState: StateFlow<TextFieldState> get() = _emailState

    private val _firstnameState = MutableStateFlow(TextFieldState())
    val firstnameState: StateFlow<TextFieldState> get() = _firstnameState

    private val _lastnameState = MutableStateFlow(TextFieldState())
    val lastnameState: StateFlow<TextFieldState> get() = _lastnameState

    private val _confirmPasswordState = MutableStateFlow(TextFieldState())
    val confirmPasswordState: StateFlow<TextFieldState> get() = _confirmPasswordState

    fun setConfirmPassword(value:String){
        _confirmPasswordState.value = confirmPasswordState.value.copy(text = value)
    }

    fun setFirstname(value:String){
        _firstnameState.value = firstnameState.value.copy(text = value)
    }

    fun setlastname(value:String){
        _lastnameState.value = lastnameState.value.copy(text = value)
    }

    fun setEmail(value:String){
        _emailState.value = emailState.value.copy(text = value)
    }

    private val _passwordState = MutableStateFlow(TextFieldState())
    val passwordState: StateFlow<TextFieldState> get() = _passwordState

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }

    fun isValidEmail(email: String): Boolean {
        val emailMatcher = Patterns.EMAIL_ADDRESS.matcher(email)
        return emailMatcher.matches()
    }
    fun isValidPassword(password: String): Boolean {
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}\$")
        return passwordRegex.matches(password)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun authenticate(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                Log.d("AuthViewModel", "Attempting login...")
                _authResult.value = AuthResult(result = Resource.Loading())
                val result = loginUseCase.login(email, password)
                Log.d("AuthViewModel", "Result: $result")
                _authResult.value = result
                if (result.result is Resource.Error) {
                    val errorMessage = (result.result as Resource.Error).message ?: "Unknown error"
                }

            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login failed: ${e.message}")
                _authResult.value = AuthResult(result = Resource.Error("${e.message}"))
            } finally {
                delay(1000)
                _loading.value = false
            }
        }
    }

    fun resetSteps() {
        isCheckedStep1.update { false }
        isCheckedStep2.update { false }
        isCheckedStep3.update { false }

        step1.update { true }
        step2.update { false }
        step3.update { false }
    }

    fun updateStep1() {
        isCheckedStep1.update { true }

        step1.update { false }

        step2.update { true }
    }

    fun updateStep2() {
        isCheckedStep2.update { true }

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

    fun getTopics(): String {
        var text = ""
        listTopics.value.map {
            text += "${it.title} \n"
        }
        return text
    }

    val sq = MutableStateFlow(
        listOf(Topics(1, "fneanfae", isChecked = false),
            Topics(2, "qqsdqd", isChecked = false),
            Topics(3, "gftergeg", isChecked = false),
            Topics(4, "dfzfzfze", isChecked = false),
            Topics(5, "cdscvs", isChecked = false),
            Topics(6, "frzgrzg", isChecked = false))
    )

    fun checkTopic(index: Int) {
        sq.update {
            it[index].isChecked = it[index].isChecked == false
            if (it[index].isChecked == true) saveTopicChoosed(it[index]) else removeTopicChoosed(it[index])
            it
        }
    }

    fun search(word: String) {
        var listWord = listOf<Topics>()
        if (word.isEmpty()) {
            sq.update {
                listOf(Topics(1, "fneanfae", isChecked = false),
                    Topics(2, "qqsdqd", isChecked = false),
                    Topics(3, "gftergeg", isChecked = false),
                    Topics(4, "dfzfzfze", isChecked = false),
                    Topics(5, "cdscvs", isChecked = false),
                    Topics(6, "frzgrzg", isChecked = false))
            }
        }
        sq.update {
            listWord = it.filter { topic ->
                topic.title?.contains(word) == true
            }
            listWord
        }
    }

    fun register(firstname:String,lastname:String,email:String,password:String,role:String) {
         viewModelScope.launch {
             try {
                 _authResult.value = AuthResult(result = Resource.Loading())
                 val result = registerUseCase.register(firstname,lastname,email,password,role)
                 _authResult.value = AuthResult(result = Resource.Success(Unit))
             } catch (e: Exception) {
                 _authResult.value = AuthResult(result = Resource.Error("error"))
             }
         }
     }

    fun saveToStore(clock: String) {
        viewModelScope.launch {
            dataStore.saveClock(clock)
        }
    }
}

