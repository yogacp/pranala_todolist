package com.todolist.app.di.module

import com.google.gson.Gson
import com.todolist.app.core.dispatcher.AppDispatcher
import com.todolist.app.core.dispatcher.DispatcherProvider
import org.koin.dsl.module

val appModules = module {
    single { Gson() }
    single<DispatcherProvider> { AppDispatcher() }
}