package com.example.realtimebasico.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class FirebaseInstance(context:Context) {

    private val database = Firebase.database
    private val myRef = database.reference

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase(){

        val randomValue = Random.nextInt(1, 200).toString()
       // myRef.setValue("Mi primera escritura:$randomValue ")
        val newItem = myRef.push()
        newItem.setValue(getGenericTasksItem(randomValue))
    }

    fun setupDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }
    private fun getGenericTasksItem(randomValue:String)= Todo(title = "tarea $randomValue", description = "Esto es una descripción cremita")
    fun removeFromDatabase(reference: String) {
        myRef.child(reference).removeValue()
    }
}