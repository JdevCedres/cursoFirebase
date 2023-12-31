package com.example.realtimebasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realtimebasico.data.FirebaseInstance
import com.example.realtimebasico.data.Todo
import com.example.realtimebasico.data.TodoAdapter
import com.example.realtimebasico.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseInstance: FirebaseInstance
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)
        setUI()
        setupListeners()

    }

    private fun setUI() {

        todoAdapter = TodoAdapter{ reference ->
            firebaseInstance.removeFromDatabase(reference)
        }
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnAddTask -> {
                firebaseInstance.writeOnFirebase()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupListeners() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
 //               val data = snapshot.getValue<String>()
 //               if (data != null) {
 //                   binding.tvResult.text = data
 //               }
                val data = getCleanSnapshot(snapshot)
                todoAdapter.setNewList(data)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Jdev onCancelled", error.details)
            }

        }
        firebaseInstance.setupDatabaseListener(postListener)
    }
    private fun getCleanSnapshot(snapshot:DataSnapshot):List<Pair<String, Todo>> {
        val list = snapshot.children.map { item ->
            Pair(item.key!!, item.getValue(Todo::class.java)!!)
        }
        return list
    }

}