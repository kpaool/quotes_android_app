package com.ecommerce.calendly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.ecommerce.calendly.storage.local.MainApp
import com.ecommerce.calendly.storage.local.Todo
import com.ecommerce.calendly.storage.local.TodoAppDatabase
import com.ecommerce.calendly.storage.local.TodoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Todo : AppCompatActivity() {
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        val todoDao:TodoDao =MainApp.database.todoDao()

        backgroundScope.launch {
            val todo = Todo(uid = 4, title = "My Todo", description = "My Todo Description")
            todoDao.insertAll(todo)
            val todos = todoDao.getAll()
            Log.i("testinfo",todos.toString())

            // Update UI with the retrieved data
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        backgroundScope.cancel()
    }
}