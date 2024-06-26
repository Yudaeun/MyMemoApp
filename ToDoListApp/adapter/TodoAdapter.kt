package com.example.todolistapp.adapter

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.R
import com.example.todolistapp.dto.TodoDto

class TodoAdapter(val context: Context): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var list=mutableListOf<TodoDto>()

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title=itemView.findViewById<TextView>(R.id.tvTodoItem)
        var timestamp=itemView.findViewById<TextView>(R.id.tvTimeStamp)
        var checkbox=itemView.findViewById<CheckBox>(R.id.cbCheck)

        fun onBind(data: TodoDto){
            title.text=data.title
            timestamp.text=data.timestamp
            checkbox.isChecked=data.isChecked

            if(data.isChecked){
                title.paintFlags=title.paintFlags or STRIKE_THRU_TEXT_FLAG
            }else{
                title.paintFlags=title.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }
            checkbox.setOnClickListener{
                itemCheckBoxClickListener.onClick(it,layoutPosition,list[layoutPosition].id)
            }

            itemView.setOnClickListener{
                itemClickListener.onClick(it,layoutPosition,list[layoutPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list,parent,false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun update(newList: MutableList<TodoDto>){
        this.list=newList
        notifyDataSetChanged()
    }

    interface ItemCheckBoxClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }

    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener

    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener){
        this.itemCheckBoxClickListener=itemCheckBoxClickListener
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }
}