package com.todolist.app.data.source.remote.todos

import com.todolist.app.domain.repository.todos.response.TodosResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface TodosRemoteDataSource {
    suspend fun getAllTodos(): Deferred<Response<TodosResponse>>
}