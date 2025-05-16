package com.todolist.app.core.network.httpclient

import android.app.Application
import androidx.collection.ObjectList
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory.Companion.invoke
import com.todolist.app.BuildConfig
import com.todolist.app.core.extension.debugMode
import com.todolist.app.domain.repository.todos.response.TodosResponse
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

fun httpClient(mainApp: Application): OkHttpClient {
    val httpCacheDir = File(mainApp.cacheDir, "httpCache")
    val cache = Cache(httpCacheDir, 10 * 1024 * 1024)
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val chuckerCollector = ChuckerCollector(
        context = mainApp,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    val httpChuck = ChuckerInterceptor.Builder(context = mainApp)
        .collector(collector = chuckerCollector)
        .maxContentLength(250000L)
        .redactHeaders(headerNames = setOf("Authorization", "Bearer"))
        .build()

    return OkHttpClient.Builder().apply {
        cache(cache)
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)

        //Add interceptor to debug API only for debug mode
        debugMode {
            addInterceptor(loggingInterceptor)
            addInterceptor(httpChuck)
        }

        addInterceptor { chain ->
            try {
                val onlineRequest =
                    chain.request().newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
                chain.proceed(onlineRequest)
            } catch (e: Exception) {
                val offlineRequest =
                    chain.request().newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
                chain.proceed(offlineRequest)
            }
        }
    }.build()
}

fun coroutinesRestClient(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
    val gson = GsonBuilder().create()

    builder.apply {
        client(okHttpClient)
        baseUrl(BuildConfig.BASE_URL)
        addConverterFactory(GsonConverterFactory.create(gson))
        addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    return builder.build()
}
