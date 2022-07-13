package developer.abdulaziz.myroom.RoomDatabase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import developer.abdulaziz.myroom.RoomDatabase.Entity.User

@Dao
interface MyDao {
    @Insert
    fun create(user: User)

    @Query("select * from user")
    fun read(): List<User>

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}