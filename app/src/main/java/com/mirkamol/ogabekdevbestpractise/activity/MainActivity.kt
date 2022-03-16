package com.mirkamol.ogabekdevbestpractise.activity

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mirkamol.ogabekdevbestpractise.R
import com.mirkamol.ogabekdevbestpractise.database.UserDB
import com.mirkamol.ogabekdevbestpractise.database.UserRepository
import com.mirkamol.ogabekdevbestpractise.helper.Logger
import com.mirkamol.ogabekdevbestpractise.model.User
import com.mirkamol.ogabekdevbestpractise.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var text: TextView
    val TAG: String = MainActivity::class.java.simpleName
    private lateinit var userList: ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        userList = ArrayList()
        text = findViewById(R.id.text)

        if (isInternetAvailable()) {
            text.text = "online"

            getUsers()
        } else {
            text.text = "offline"

            getUsersFromDatabse()
        }
    }

    private fun getUsersFromDatabse() {
        val repository = UserRepository(application)
        Logger.d(TAG, repository.getUsers().toString())
    }

    private fun getUsers() {
        RetrofitHttp.userService.getAllUsers().enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.body() != null) {
                    Logger.d(TAG, "onResponce: ${response.body().toString()}")
                    userList.clear()
                    userList.addAll(response.body()!!)

                    saveToDatabase(response.body()!!)
                } else {
                    Logger.e(TAG, "onResponce: null")
                }

            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Logger.e(TAG, "onFailure: ${t.localizedMessage}")
            }

        })
    }

    private fun saveToDatabase(respond: ArrayList<User>) {
        val repository = UserRepository(application)
        repository.deleteUsers()
        for (i in respond) {
            val userDB = UserDB(i.id!!, i.username, i.full_name, i.is_online)

            repository.saveUser(userDB)
        }
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}