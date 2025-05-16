package com.todolist.app.data.source.remote.todos

import com.todolist.app.domain.repository.todos.response.TodosResponse
import com.todolist.app.domain.repository.todos.service.TodosService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class TodosRemoteDataSourceImpl(private val service: TodosService) : TodosRemoteDataSource {

    override suspend fun getAllTodos(): Deferred<Response<TodosResponse>> {
        return withContext(Dispatchers.IO) {
            coroutineScope {
                async { service.getAllTodos().await() }
            }
        }
    }

}