package com.todolist.app.di.module

import com.todolist.app.domain.repository.todos.service.todoServices
import org.koin.dsl.module

val networkServicesModule = module {
    single { todoServices(get()) }
}