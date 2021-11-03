package com.guelphwellingtonparamedicsapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    var uid : Int = 0,
    @ColumnInfo(name = "token")
    var token : String?
        )