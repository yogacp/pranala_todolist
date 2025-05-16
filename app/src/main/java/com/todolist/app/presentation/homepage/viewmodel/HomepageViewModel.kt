package com.todolist.app.presentation.homepage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todolist.app.core.extension.asLiveData
import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.domain.usecase.TodoUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomepageViewModel : ViewModel(), KoinComponent {

    private val todoUseCase: TodoUseCase by inject()

    private val _todolist = MutableLiveData<List<Todos>>()
    val todolist = _todolist.asLiveData()

    init {
        getAllTodos()
    }

    private fun getAllTodos() {
        viewModelScope.launch {
            val todos = todoUseCase.getAllTodos()
            _todolist.postValue(todos)
        }
    }
}