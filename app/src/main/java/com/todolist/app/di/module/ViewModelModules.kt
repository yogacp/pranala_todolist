package com.todolist.app.di.module

import com.todolist.app.presentation.homepage.viewmodel.HomepageViewModel
import org.koin.dsl.module

val viewModelModules = module {
    factory { HomepageViewModel() }
}