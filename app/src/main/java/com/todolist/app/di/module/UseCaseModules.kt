package com.todolist.app.di.module

import com.todolist.app.domain.usecase.TodoUseCase
import org.koin.dsl.module

val useCaseModules = module {
    single { TodoUseCase(get()) }
}