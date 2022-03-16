package com.mirkamol.ogabekdevbestpractise.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDB(
    @PrimaryKey
    var id:String,

    var full_name: String,
    var username: String,
    var is_online: Boolean

)