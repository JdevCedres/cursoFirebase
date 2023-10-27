package com.example.realtimebasico.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimebasico.databinding.ItemTodoBinding

class TodoViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemTodoBinding.bind(view)
    fun bind(todoTask: Pair<String, Todo>, onItemSelected: (String)-> Unit) {
        binding.tvTitle.text = todoTask.second.title
        binding.tvDescription.text= todoTask.second.description
        binding.tvReference.text = todoTask.first
        binding.cvItem.setOnClickListener{onItemSelected(todoTask.first)}

    }
}