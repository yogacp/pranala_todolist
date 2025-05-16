package com.todolist.app.domain.repository.todos

import com.todolist.app.domain.model.todos.Todos

interface TodosRepository {
    suspend fun getAllTodos(): List<Todos>
}