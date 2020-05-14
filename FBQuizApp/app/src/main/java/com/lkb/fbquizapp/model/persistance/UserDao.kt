package com.lkb.fbquizapp.model.persistance


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lkb.fbquizapp.model.persistance.User
import io.reactivex.Maybe

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUser(): Maybe<List<User>>

    @Insert
    fun insertAll(vararg user: User)

}