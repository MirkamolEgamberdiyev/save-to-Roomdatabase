package com.mirkamol.ogabekdevbestpractise.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mirkamol.ogabekdevbestpractise.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userDB: UserDB)

    @Query("SELECT *FROM users")
    fun getUsers():List<UserDB>

    @Query("DELETE FROM users")
    fun deleteAllUsers()

}