package com.example.mediconnect.common

import androidx.annotation.Nullable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Base Use Case class
 */
abstract class BaseUseCaseInOut<Model, Params> {

    abstract suspend fun buildRequest(@Nullable params: Params?): Flow<Model?>

    suspend fun execute(@Nullable params: Params?): Flow<Model?> {
        return try {
            buildRequest(params)
        } catch (exception: Exception) {
            flow { emit(null) }
        }
    }
}