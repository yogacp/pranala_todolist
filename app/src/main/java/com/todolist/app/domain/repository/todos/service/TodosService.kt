package com.todolist.app.domain.repository.todos.service

import com.todolist.app.core.constant.RestConstant
import com.todolist.app.domain.repository.todos.response.TodosResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers

interface TodosService {
    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET("todos")
    fun getAllTodos(): Deferred<Response<TodosResponse>>
}

fun todoServices(adapter: Retrofit): TodosService {
    return return adapter.create(TodosService::class.java)
}