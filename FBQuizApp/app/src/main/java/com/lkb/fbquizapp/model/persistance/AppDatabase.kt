package com.lkb.fbquizapp.model.persistance

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)//arrayOf(User::class)
abstract class AppDatabase : RoomDatabase() {
abstract fun userDao(): UserDao?
}