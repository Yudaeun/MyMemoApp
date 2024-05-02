package com.example.todolistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolistapp.dao.TodoDao
import com.example.todolistapp.dto.TodoDto

@Database(entities=arrayOf(TodoDto::class),version=1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}