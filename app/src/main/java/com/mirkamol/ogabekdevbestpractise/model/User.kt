package com.mirkamol.ogabekdevbestpractise.model

data class User(
    var full_name: String,
    var username: String,
    var is_online: Boolean,
    var id:String? = null
)
