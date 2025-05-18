package com.todolist.app.data.repository.todos

import com.todolist.app.data.database.entity.todos.TodosEntity
import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.domain.repository.todos.response.TodosList
import com.todolist.app.domain.repository.todos.response.TodosResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
        title = this.todo,
        completed = this.completed
    )
}

fun List<Todos>.asDatabaseModel(): List<TodosEntity> {
    return map { it.asEntityModel() }
}

fun Todos.asEntityModel(): TodosEntity {
    return TodosEntity(
        userId = this.userId,
        id = this.id,
        todo = this.title,
        completed = this.completed
    )
}

fun Flow<List<TodosEntity>>.asDomainModel(): Flow<List<Todos>> {
    return flow {
        emitAll(
            map {
                it.map { it.asDomainModel() }
            }
        )
    }
}

fun TodosEntity.asDomainModel(): Todos {
    return Todos(
        userId = this.userId,
        id = this.id,
        title = this.todo,
        completed = this.completed
    )
}