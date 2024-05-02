package com.example.todolistapp.config

import android.app.Application
import com.example.todolistapp.repository.TodoRepository

class ApplicationClass: Application() {
    override fun onCreate(){
        super.onCreate()

        TodoRepository.initialize(this)
    }
}