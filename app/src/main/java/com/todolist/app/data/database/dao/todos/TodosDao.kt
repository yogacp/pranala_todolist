package com.todolist.app.data.database.dao.todos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todolist.app.data.database.entity.todos.TodosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodosDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<TodosEntity>>

    @Query("SELECT * FROM todos WHERE id = :todosId")
    fun getTodosById(todosId: Int): TodosEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<TodosEntity>)

    @Query("DELETE FROM todos WHERE id = :id")
    fun deleteTodoById(id: Int)

    @Query("DELETE FROM todos")
    fun deleteAllTodos()
}