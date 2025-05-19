package com.todolist.app.domain.usecase

import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.domain.repository.todos.TodosRepository

class TodoUseCase(private val repository: TodosRepository) {

    suspend fun getAllTodos() = repository.getAllTodos()
    suspend fun addTodos(todos: Todos) = repository.addTodos(todos)
}