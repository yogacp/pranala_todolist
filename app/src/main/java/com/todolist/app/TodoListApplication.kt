package com.todolist.app

import android.app.Application
import com.todolist.app.di.component.appComponents
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TodoListApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@TodoListApplication)
            modules(appComponents)
        }
    }
}