package com.todolist.app.data.database.builder

import android.content.Context
import androidx.room.Room
import com.todolist.app.data.database.AppDatabase

object DatabaseBuilder {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: buildRoomDB(context).also { instance = it }
        }
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "todos_db"
            ).fallbackToDestructiveMigration(false).build()
}