package com.example.mediconnect.common

/**
 * Base Use Case class
 */
abstract class BaseNormalUseCaseOut<Model> {

    abstract suspend fun buildRequest(): Model

    suspend fun execute(): Model? {
        return try {
            buildRequest()
        } catch (exception: Exception) {
            null
        }
    }
}