package com.todolist.app.data.repository.todos

import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.domain.repository.todos.response.TodosList
import com.todolist.app.domain.repository.todos.response.TodosResponse
import retrofit2.Response


fun Response<TodosResponse>.asDomainModel(): List<Todos> {
    return body()?.todos?.map {
        it.asTodosDomainModel()
    } ?: emptyList() //handle null response
}

fun TodosList.asTodosDomainModel(): Todos {
    return Todos(
        userId = this.userId,
        id = this.id,
        title = this.title,
        completed = this.completed
    )
}