package com.example.merchantapp2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splashScreen : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    var preferenceName : String = "userSession"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {

            sharedPreferences = getSharedPreferences(preferenceName,0)
            val getEmail = sharedPreferences.getString("userMail","")
            val getPassword = sharedPreferences.getString("userpassword","")
            val getName = sharedPreferences.getString("userName","")
            val getPhone = sharedPreferences.getString("userPhone","")

            lateinit var intent: Intent

            if(getEmail.equals("") && getPassword.equals(""))
            {
                intent = Intent(this, loginActivity::class.java)
            }
            else
            {
                intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userMail",getEmail)
                intent.putExtra("userName",getName)
                intent.putExtra("userPhone",getPhone)
            }
            startActivity(intent)
        },700)

    }
}