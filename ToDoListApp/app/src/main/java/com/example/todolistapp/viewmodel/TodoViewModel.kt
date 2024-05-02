package com.example.todolistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.dto.TodoDto
import com.example.todolistapp.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// viewModel은 Activity의 lifecycle과 별개로 돌아가서 데이터 유지, 공유 가능->Activity 이동이 있어도 동일한 데이터를 받아올 수 있도록 구현
class TodoViewModel: ViewModel() {
    val todoList: LiveData<MutableList<TodoDto>>
    private val todoRepository: TodoRepository=TodoRepository.get()

    init{
        todoList=todoRepository.list()
    }

    fun getOne(id: Long)=todoRepository.getTodo(id)
    fun insert(dto:TodoDto)=viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insert(dto)
    }
    fun update(dto:TodoDto)=viewModelScope.launch(Dispatchers.IO) {
        todoRepository.update(dto)
    }
    fun delete(dto: TodoDto)=viewModelScope.launch(Dispatchers.IO){
        todoRepository.delete(dto)
    }
}