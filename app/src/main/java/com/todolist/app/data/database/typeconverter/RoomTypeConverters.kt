package com.todolist.app.data.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.todolist.app.domain.model.todos.Todos

class RoomTypeConverters {
    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringArrayList(value: ArrayList<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromTodos(value: Todos): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toTodos(value: String?): Todos {
        val typeToken = object : TypeToken<Todos>() {}.type
        return Gson().fromJson(value, typeToken)
    }
}