package com.todolist.app.domain.model.todos

data class Todos(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)