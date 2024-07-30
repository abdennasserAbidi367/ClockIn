package com.example.mediconnect.common

import android.util.Log
import androidx.annotation.Nullable

/**
 * Base Use Case class
 */
abstract class BaseNormalUseCaseIn<Params> {

    abstract suspend fun buildRequest(@Nullable params: Params?)

    suspend fun execute(@Nullable params: Params?) {
        try {
            buildRequest(params)
        } catch (exception: Exception) {
            Log.i("", "execute: $exception")
        }
    }
}