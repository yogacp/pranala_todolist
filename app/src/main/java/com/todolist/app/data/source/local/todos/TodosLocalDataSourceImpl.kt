package com.todolist.app.data.source.local.todos

import com.todolist.app.data.database.AppDatabase
import com.todolist.app.data.database.entity.todos.TodosEntity
import kotlinx.coroutines.flow.Flow

class TodosLocalDataSourceImpl(appDatabase: AppDatabase) : TodosLocalDataSource {

    private val dao = appDatabase.todosDao()

    override fun getAllTodos(): Flow<List<TodosEntity>> {
        return dao.getAllTodos()
    }

    override fun getTodosById(id: Int): TodosEntity {
        return dao.getTodosById(id)
    }

    override suspend fun insertAll(todos: List<TodosEntity>) {
        return dao.insertAll(todos)
    }

    override suspend fun insert(todos: TodosEntity) {
        return dao.insert(todos)
    }

    override fun deleteById(id: Int) {
        return dao.deleteTodoById(id)
    }

    override fun deleteAllTodos() {
        return dao.deleteAllTodos()
    }

}