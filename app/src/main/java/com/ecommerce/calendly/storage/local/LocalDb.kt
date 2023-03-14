package com.ecommerce.calendly.storage.local

import android.content.Context
import androidx.room.*

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?
)



@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE uid IN (:uid)")
    fun loadAllByIds(uid: IntArray): List<Todo>

    @Query("SELECT * FROM todo WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Todo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todos: Todo)

    @Delete
    fun delete(todo: Todo)
}

@Database(entities = [Todo::class], version = 1)
abstract class TodoAppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}