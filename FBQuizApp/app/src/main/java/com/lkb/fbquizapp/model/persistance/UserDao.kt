package com.lkb.fbquizapp.model.persistance


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUser(): Maybe<List<User>>

    @Query("select * from user order by score desc limit 5")
    fun getTopFileUser(): Maybe<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User?): Completable?

    /**
     * Delete all users.
     */
    @Query("DELETE FROM user")
    fun deleteAllUsers()
}