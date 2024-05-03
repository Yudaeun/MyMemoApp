package com.example.todolistapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolistapp.dto.TodoDto

@Dao
interface TodoDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insert(dto: TodoDto)

    @Query("select * from todo")
    fun list(): LiveData<MutableList<TodoDto>> // LiveData로 CUD에 의해 변화하는 값에 대해 즉시 반영

    @Query("select * from todo where id= (:id)")
    fun selectOne(id: Long): TodoDto

    @Update
    suspend fun update(dto: TodoDto)

    @Delete
    fun delete(dto: TodoDto)

}