package com.example.todolistapp.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="todo")
class TodoDto (
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate=true)
    var id: Long=0,

    @ColumnInfo(name="title")
    val title: String,

    @ColumnInfo(name="content")
    val content: String,

    @ColumnInfo(name="timestamp")
    val timestamp: String,

    @ColumnInfo(name="isChecked")
    var isChecked: Boolean

): Serializable {

}