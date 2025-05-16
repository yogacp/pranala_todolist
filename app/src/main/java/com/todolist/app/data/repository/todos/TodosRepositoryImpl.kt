package com.todolist.app.data.repository.todos

import com.todolist.app.data.source.remote.todos.TodosRemoteDataSource
import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.domain.repository.todos.TodosRepository

class TodosRepositoryImpl(
    private val remote: TodosRemoteDataSource
) : TodosRepository {

    override suspend fun getAllTodos(): List<Todos> {
        return remote.getAllTodos().await().asDomainModel()
    }

}