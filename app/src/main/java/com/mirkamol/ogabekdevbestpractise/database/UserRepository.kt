package com.mirkamol.ogabekdevbestpractise.database

import android.app.Application
import com.mirkamol.ogabekdevbestpractise.helper.Logger

import com.mirkamol.ogabekdevbestpractise.manager.RoomManager


class UserRepository(application: Application) {
    val TAG:String = UserRepository::class.java.simpleName

    private val db = RoomManager.getDatabase(application)
    private val userDao:UserDao = db!!.userDao()

    fun getUsers():List<UserDB>{
        Logger.d(TAG, "Done")

        return userDao.getUsers()
    }

    fun saveUser(userDB: UserDB){
        Logger.d(TAG, "Saved")
        userDao.saveUser(userDB)
    }

    fun deleteUsers(){
        Logger.d(TAG, "Database cleared")

        userDao.deleteAllUsers()
    }
}