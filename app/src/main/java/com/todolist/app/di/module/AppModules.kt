package com.todolist.app.di.module

import com.google.gson.Gson
import org.koin.dsl.module

val appModules = module {
    single { Gson() }
}