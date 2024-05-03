package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolistapp.databinding.ActivityEditTodoBinding
import com.example.todolistapp.dto.TodoDto
import java.text.SimpleDateFormat

class EditTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTodoBinding
    private var todo: TodoDto?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit_todo)
        binding=ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type=intent.getStringExtra("type")

        if(type.equals("ADD")){
            binding.btnSave.text="추가하기"
        }else{
            todo=intent.getSerializableExtra("item") as TodoDto?
            binding.etTodoTitle.setText(todo!!.title)
            binding.etTodoContent.setText(todo!!.content)
            binding.btnSave.text="수정하기"
        }

        binding.btnSave.setOnClickListener{
            val title=binding.etTodoTitle.text.toString()
            val content=binding.etTodoContent.text.toString()
            val currentDate=SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())

            if (type.equals("ADD")){
                if(title.isNotEmpty()&&content.isNotEmpty()){
                    val todo=TodoDto(0,title,content,currentDate,false)
                    val intent= Intent().apply{
                        putExtra("todo",todo)
                        putExtra("flag","putAdd")
                    }
                    setResult(RESULT_OK,intent)
                    finish()
                    }
                }else{
                if (title.isNotEmpty()&&content.isNotEmpty()){
                    val todo=TodoDto(todo!!.id,title,content,currentDate,todo!!.isChecked)

                    val intent=Intent().apply{
                        putExtra("todo",todo)
                        putExtra("flag","putEdit")
                    }
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }

        }

    }
}