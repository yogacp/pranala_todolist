package com.todolist.app.di.module

import com.todolist.app.data.database.builder.DatabaseBuilder
import com.todolist.app.data.source.local.todos.TodosLocalDataSource
import com.todolist.app.data.source.local.todos.TodosLocalDataSourceImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseBuilder.getInstance(androidApplication()) }
    single<TodosLocalDataSource> { TodosLocalDataSourceImpl(get()) }
}