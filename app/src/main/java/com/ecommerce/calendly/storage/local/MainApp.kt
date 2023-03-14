package com.ecommerce.calendly.storage.local

import android.app.Application
import androidx.room.Room

class MainApp : Application() {
    companion object {
        lateinit var instance: MainApp
        lateinit var database: TodoAppDatabase
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, TodoAppDatabase::class.java, "todo_database").build()


    }

    fun getDatabase():TodoAppDatabase{
        return database
    }
}