package com.lkb.fbquizapp.model.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "age")val age: Int,
    @ColumnInfo(name = "gender")val gender: String,
    @ColumnInfo(name = "score")val score: Int
)
{
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0 // or uid:Int?=null
}