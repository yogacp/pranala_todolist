package com.todolist.app.data.repository.todos

import com.todolist.app.core.utils.Resource
import com.todolist.app.core.utils.networkBoundResource
import com.todolist.app.data.database.entity.todos.TodosEntity
import com.todolist.app.data.source.local.todos.TodosLocalDataSource
import com.todolist.app.data.source.remote.todos.TodosRemoteDataSource
import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.domain.repository.todos.TodosRepository
import kotlinx.coroutines.flow.Flow

class TodosRepositoryImpl(
    private val local: TodosLocalDataSource,
    private val remote: TodosRemoteDataSource
) : TodosRepository {

    override suspend fun getAllTodos(): Flow<Resource<List<Todos>>> {
        return networkBoundResource(
            query = {
                local.getAllTodos().asDomainModel()
            },
            fetch = {
                remote.getAllTodos().await().asDomainModel()
            },
            saveFetchResult = {
                local.insertAll(it.asDatabaseModel())
            },
            shouldFetch = {
                it.isEmpty() //Fetch fresh data only when the local cache is empty
            }
        )
    }

    override suspend fun addTodos(todos: Todos) {
        local.insert(TodosEntity(
            id = todos.id,
            userId = todos.userId,
            todo = todos.title,
            completed = todos.completed
        ))
    }

}