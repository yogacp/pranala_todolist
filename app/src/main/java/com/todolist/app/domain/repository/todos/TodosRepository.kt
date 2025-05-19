package com.todolist.app.domain.repository.todos

import com.todolist.app.core.utils.Resource
import com.todolist.app.domain.model.todos.Todos
import kotlinx.coroutines.flow.Flow

interface TodosRepository {
    suspend fun getAllTodos(): Flow<Resource<List<Todos>>>
    suspend fun addTodos(todos: Todos)
}