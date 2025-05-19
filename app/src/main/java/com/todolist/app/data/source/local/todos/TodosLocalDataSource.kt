package com.todolist.app.data.source.local.todos

import com.todolist.app.data.database.entity.todos.TodosEntity
import kotlinx.coroutines.flow.Flow

interface TodosLocalDataSource {
    fun getAllTodos(): Flow<List<TodosEntity>>
    fun getTodosById(id: Int): TodosEntity
    suspend fun insertAll(todos: List<TodosEntity>)
    suspend fun insert(todos: TodosEntity)
    fun deleteById(id: Int)
    fun deleteAllTodos()
}