package com.example.merchantapp2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var welcomeText : TextView
    lateinit var btnLogout : Button

    private lateinit var sharedPreferences: SharedPreferences
    var preferenceName : String = "userSession"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userName : String? = intent.getStringExtra("userName")
        val userPhone : String? = intent.getStringExtra("userPhone")
        val userMail : String? = intent.getStringExtra("userMail")

        welcomeText = findViewById(R.id.welcomeText1)
        welcomeText.setText("Welcome $userName")

        btnLogout = findViewById(R.id.btnLogout)
        actionListener()
    }

    private fun actionListener() {

        btnLogout.setOnClickListener(View.OnClickListener {

            sharedPreferences = getSharedPreferences(preferenceName,0)
            val editor : SharedPreferences.Editor = sharedPreferences.edit()

            editor.clear()
            editor.apply()

            val intent = Intent(this@MainActivity,loginActivity::class.java)
            startActivity(intent)
        })
    }
}