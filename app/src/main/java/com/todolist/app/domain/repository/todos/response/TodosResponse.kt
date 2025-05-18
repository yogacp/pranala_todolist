package com.todolist.app.domain.repository.todos.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodosResponse (
    @SerialName("todos") val todos: List<TodosList>
)

@Serializable
data class TodosList (
    @SerialName("userId") val userId: Int,
    @SerialName("id") val id: Int,
    @SerialName("todo") val todo: String,
    @SerialName("completed") val completed: Boolean
)