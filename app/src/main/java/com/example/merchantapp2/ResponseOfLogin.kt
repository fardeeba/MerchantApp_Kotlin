package com.example.merchantapp2

class ResponseOfLogin {

    private lateinit var token : String

    var user : User = User()

    fun getToken() : String
    {
        return token
    }

    fun getUserNameInfo() : String
    {
        return user.getFullName()
    }

    fun getUserContactInfo() : String
    {
        return user.getPhoneNumber()
    }
    fun getUserMailInfo() : String
    {
        return user.getEmail()
    }

}