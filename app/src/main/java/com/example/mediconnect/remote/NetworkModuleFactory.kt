package com.example.mediconnect.remote

import com.example.mediconnect.remote.api.AuthApi
import okhttp3.OkHttpClient

object NetworkModuleFactory : BaseNetworkModuleFactory() {

    const val LOG_INTERCEPTOR = "LogInterceptor"
    const val REQUEST_INTERCEPTOR = "RequestInterceptor"

    fun makeService(): AuthApi = makeService(makeOkHttpClient())

    private fun makeService(okHttpClient: OkHttpClient): AuthApi {
        val retrofit = buildRetrofitObject(okHttpClient)
        return retrofit.create(AuthApi::class.java)
    }
}
