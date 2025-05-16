package com.todolist.app.di.module

import com.todolist.app.core.network.httpclient.coroutinesRestClient
import com.todolist.app.core.network.httpclient.httpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkRestModules = module {
    single { httpClient(mainApp = androidApplication()) }
    single { coroutinesRestClient(okHttpClient = get()) }
}