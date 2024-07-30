package com.example.mediconnect.feature.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.domain.usecase.GetListUserUseCase
import com.example.mediconnect.domain.usecase.GetUserUseCase
import com.example.mediconnect.domain.usecase.SaveUserUseCase
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanner: GmsBarcodeScanner,
    private val saveUserUseCase: SaveUserUseCase,
    private val getListUserUseCase: GetListUserUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val users = MutableStateFlow(listOf(User(id = "", username = "")))

    fun getUserById(id: String) {
        viewModelScope.launch {
            users.update {
                getUserUseCase.execute(id) ?: emptyList()
            }
        }
    }

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    val userId = MutableStateFlow("2019-A-0044")
    //val userId = MutableStateFlow("2022-B-0068")
    //val userId = MutableStateFlow("AAAAAA")

    fun changeUserId(id: String) {
        userId.update {
            id
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

    val allUser = MutableStateFlow(emptyList<User>())

    fun getAllUser() {
        viewModelScope.launch {
            Log.i("oussama", "getAllUser: ${getListUserUseCase.execute()?.size}")

            allUser.update {
                getListUserUseCase.execute() ?: emptyList()
            }
        }
    }

    fun startScanning() {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                _state.update {
                    getDetails(barcode)
                }

                var id = getDetails(barcode)
                val num = id[4]
                id = id.replace('7', '-')
                val sb = StringBuilder(id)
                sb.insert(7, '0')

                userId.update {
                    sb.toString()
                }

            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun getDetails(barcode: Barcode): String {
        return when (barcode.valueType) {
            Barcode.TYPE_WIFI -> {
                val ssid = barcode.wifi!!.ssid
                val password = barcode.wifi!!.password
                val type = barcode.wifi!!.encryptionType
                "ssid : $ssid, password : $password, type : $type"
            }
            Barcode.TYPE_URL -> {
                "url : ${barcode.url!!.url}"
            }
            Barcode.TYPE_PRODUCT -> {
                "productType : ${barcode.displayValue}"
            }
            Barcode.TYPE_EMAIL -> {
                "email : ${barcode.email}"
            }
            Barcode.TYPE_CONTACT_INFO -> {
                "contact : ${barcode.contactInfo}"
            }
            Barcode.TYPE_PHONE -> {
                "phone : ${barcode.phone}"
            }
            Barcode.TYPE_CALENDAR_EVENT -> {
                "calender event : ${barcode.calendarEvent}"
            }
            Barcode.TYPE_GEO -> {
                "geo point : ${barcode.geoPoint}"
            }
            Barcode.TYPE_ISBN -> {
                "isbn : ${barcode.displayValue}"
            }
            Barcode.TYPE_DRIVER_LICENSE -> {
                "driving license : ${barcode.driverLicense}"
            }
            Barcode.TYPE_SMS -> {
                "sms : ${barcode.sms}"
            }
            Barcode.TYPE_TEXT -> {
                //"text : ${barcode.rawValue}"
                "${barcode.rawValue}"
            }
            Barcode.TYPE_UNKNOWN -> {
                "unknown : ${barcode.rawValue}"
            }
            else -> {
                "Couldn't determine"
            }
        }

    }


}