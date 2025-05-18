package com.todolist.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.todolist.app.data.database.dao.todos.TodosDao
import com.todolist.app.data.database.entity.todos.TodosEntity
import com.todolist.app.data.database.typeconverter.RoomTypeConverters

@Database(version = 1, exportSchema = false, entities = [TodosEntity::class])
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao
}