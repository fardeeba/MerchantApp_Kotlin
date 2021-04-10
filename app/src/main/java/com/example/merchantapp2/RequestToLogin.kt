package com.example.merchantapp2

class RequestToLogin {

    private lateinit var userName : String
    private lateinit var password : String

    public fun setUserName(userName : String)
    {
        this.userName = userName
    }
    public fun setPassword(password : String)
    {
        this.password = password
    }
}