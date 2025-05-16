package com.todolist.app.di.component

import com.todolist.app.di.module.appModules
import com.todolist.app.di.module.networkRestModules
import com.todolist.app.di.module.networkServicesModule
import com.todolist.app.di.module.remoteDataSourceModules
import com.todolist.app.di.module.useCaseModules
import com.todolist.app.di.module.viewModelModules
import org.koin.core.module.Module

val appComponents: List<Module> = listOf(
    /**
     * Core Modules
     */
    appModules,

    /**
     * Network Modules
     */
    networkRestModules,
    networkServicesModule,
    remoteDataSourceModules,


    /**
     * Use Case Modules
     */
    useCaseModules,


    /**
     * Viewmodel Modules
     */
    viewModelModules
)