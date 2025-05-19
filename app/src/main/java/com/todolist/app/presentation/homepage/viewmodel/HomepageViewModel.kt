package com.todolist.app.presentation.homepage.viewmodel

import androidx.lifecycle.ViewModel
import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.domain.usecase.TodoUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomepageViewModel : ViewModel(), KoinComponent {

    private val todoUseCase: TodoUseCase by inject()

    suspend fun getAllTodos() = todoUseCase.getAllTodos()

    suspend fun addTodos(todos: Todos) = todoUseCase.addTodos(todos)
}