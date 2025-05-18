package com.todolist.app.data.source.remote.todos

import com.todolist.app.core.dispatcher.DispatcherProvider
import com.todolist.app.domain.repository.todos.response.TodosResponse
import com.todolist.app.domain.repository.todos.service.TodosService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class TodosRemoteDataSourceImpl(
    private val service: TodosService,
    private val dispatcher: DispatcherProvider
) : TodosRemoteDataSource {

    override suspend fun getAllTodos(): Deferred<Response<TodosResponse>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getAllTodos().await() }
            }
        }
    }

}