package com.example.mediconnect.common

import android.util.Log

/**
 * Base Use Case class
 */
abstract class BaseNormalUseCase {

    abstract suspend fun buildRequest()

    suspend fun execute() {
        try {
            buildRequest()
        } catch (exception: Exception) {
            Log.i("", "execute: ")
        }
    }
}