package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.dto.TodoDto
import com.example.todolistapp.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAdd.setOnClickListener{
            val intent= Intent(this,EditTodoActivity::class.java).apply{
                putExtra("type","ADD")
            }
            requestActivity.launch(intent)
        }

        todoViewModel=ViewModelProvider(this)[TodoViewModel::class.java]
    }
    private val requestActivity=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode==RESULT_OK){
            val todo=it.data?.getSerializableExtra("todo") as TodoDto

            when(it.data?.getStringExtra("flag")){
                "putAdd"->{
                    
                }
            }
        }
    }
}