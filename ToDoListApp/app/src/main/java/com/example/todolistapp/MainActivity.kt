package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.adapter.TodoAdapter
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.dto.TodoDto
import com.example.todolistapp.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, EditTodoActivity::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }

        todoViewModel = ViewModelProvider(
            this,
            TodoViewModel.Factory(application)
        ).get(TodoViewModel::class.java)

        todoViewModel.todoList.observe(this) {
            //todoList는 LiveData이므로 observe()로 변화된 값 감지 가능
            todoAdapter.update(it)
        }
        todoAdapter = TodoAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        // 체크 박스 클릭=체크 표시
        todoAdapter.setItemCheckBoxClickListener(object : TodoAdapter.ItemCheckBoxClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)
                    todo.isChecked = !todo.isChecked
                    todoViewModel.update(todo)
                }
            }
        })

        // 항목 클릭=체크 표시
        todoAdapter.setItemClickListener(object : TodoAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo=todoViewModel.getOne(itemId)
                    todo.isChecked=!todo.isChecked
                    todoViewModel.update(todo)
                }
            }
        })

        // 길게 클릭=항목 수정
        todoAdapter.setItemLongClickListener(object : TodoAdapter.ItemLongClickListener{
            override fun onLongClick(view: View, position: Int, itemId: Long): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)

                    val intent = Intent(this@MainActivity, EditTodoActivity::class.java).apply {
                        putExtra("type", "EDIT")
                        putExtra("item", todo)
                    }
                    requestActivity.launch(intent)
                }
                return true
            }
        })
    }
    private val requestActivity=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode==RESULT_OK){
            val todo=it.data?.getSerializableExtra("todo") as TodoDto

            when(it.data?.getStringExtra("flag")){
                "putAdd"->{
                    // DB에 저장하는 작업은 시간이 걸려서 메인스레드에서 동작하지 않으므로 코루틴을 통해 IO 스레드가 작업을 수행하도록 구현
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.insert(todo)
                    }
                    Toast.makeText(this,"추가되었습니다.",Toast.LENGTH_SHORT).show()
                }
                "putEdit"->{
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.update(todo)
                    }
                    Toast.makeText(this,"수정되었습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option,menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_item_delete->{
                Toast.makeText(this,"삭제되었습니다.",Toast.LENGTH_SHORT).show()
                todoViewModel.todoList.value!!.forEach{
                    if(it.isChecked){
                        todoViewModel.delete(it)
                    }
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}