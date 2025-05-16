package com.todolist.app.di.module

import com.todolist.app.data.repository.todos.TodosRepositoryImpl
import com.todolist.app.data.source.remote.todos.TodosRemoteDataSource
import com.todolist.app.data.source.remote.todos.TodosRemoteDataSourceImpl
import com.todolist.app.domain.repository.todos.TodosRepository
import org.koin.dsl.module

val remoteDataSourceModules = module {
    single<TodosRepository> { TodosRepositoryImpl(get()) }
    single<TodosRemoteDataSource> { TodosRemoteDataSourceImpl(get()) }
}