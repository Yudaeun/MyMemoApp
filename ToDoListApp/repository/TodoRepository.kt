package com.example.todolistapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todolistapp.database.TodoDatabase
import com.example.todolistapp.dto.TodoDto
import java.lang.IllegalStateException

private const val DATABASE_NAME = "todo-database.db"
class TodoRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val todoDao=database.todoDao()
    fun list(): LiveData<MutableList<TodoDto>> = todoDao.list()
    fun getTodo(id: Long): TodoDto = todoDao.selectOne(id)
    fun insert(dto: TodoDto) = todoDao.insert(dto)
    suspend fun update(dto: TodoDto) = todoDao.update(dto)
    fun delete(dto: TodoDto) = todoDao.delete(dto)

    companion object{ // class가 생성될 때 메모리에 적재되면서 동시에 생성하는 객체로 데이터베이스 생성 및 초기화 담당
        private var INSTANCE: TodoRepository?=null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE= TodoRepository(context)
            }
        }
        fun get(): TodoRepository{
            return INSTANCE ?:
            throw IllegalStateException("TodoRepository must be initialized")
        }
    }
}