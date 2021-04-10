package com.example.merchantapp2

import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.content.Intent
import android.content.SharedPreferences

class loginActivity : AppCompatActivity() {

    private lateinit var editText1 : EditText
    private lateinit var editText2: EditText
    private lateinit var btnLogin : Button

    private lateinit var sharedPreferences: SharedPreferences
    var preferenceName : String = "userSession"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initWidget()
        actionListeners()
    }

    private  fun validations() : Boolean {

        val userName : String = editText1.text.toString()
        val password : String = editText2.text.toString()

        val userNameValidations1 = "^[a-zA-Z]*\$"
        val userNameValidations2 = "^[0-9]{1,11}$"
        val userNameValidations3 = "[a-z]*@[a-z]*.[a-z]*$"

        val pattern1 : Pattern = Pattern.compile(userNameValidations1)
        val pattern2 : Pattern = Pattern.compile(userNameValidations2)
        val pattern3 : Pattern = Pattern.compile(userNameValidations3)

        val matcher1 : Matcher = pattern1.matcher(userName)
        val matcher2 : Matcher = pattern2.matcher(userName)
        val matcher3 : Matcher = pattern3.matcher(userName)

        if (userName.length == 0 && password.length == 0)
        {
            editText1.error = "Kindly fill in this field"
            editText2.error = "Kindly fill in this field"
            return false
        }
        else if (userName.length == 0 && password.length != 0)
        {
            editText1.error = "Kindly fill in this field"
            return false
        }
        else if (userName.length != 0 && password.length == 0)
        {
            editText2.error = "Kindly fill in this field"
            return false
        }
        else if (!matcher1.matches()) {
            if (!matcher2.matches()) {
                if (!matcher3.matches()) {
                    editText1.error = "Invalid input"
                    return false
                }
            }
        }

        return true
    }

    private fun actionListeners() {
        
        editText1.setOnFocusChangeListener { view, b ->
            validations()
        }

        editText2.setOnFocusChangeListener { view, b ->

            validations()
        }

        btnLogin.setOnClickListener(View.OnClickListener {

            val userName : String = editText1.text.toString()
            val password : String = editText2.text.toString()

            if(validations())
            {
                authentication(userName,password)
            }
        })
    }

    private fun initWidget() {
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        btnLogin = findViewById(R.id.btnLogin)
    }

    fun showHidePassword(view: View)
    {
        if(view.id == R.id.showpassword)
        {
            val img: ImageView = view as ImageView
            if(editText2.transformationMethod.equals(PasswordTransformationMethod.getInstance()))
            {
                img.setImageResource(R.drawable.ic_baseline_visibility_off_24)

                editText2.transformationMethod.equals(HideReturnsTransformationMethod.getInstance())
            }
            else
            {
                img.setImageResource(R.drawable.ic_baseline_visibility_24)

                editText2.transformationMethod.equals(PasswordTransformationMethod.getInstance())
            }
        }
    }

    public fun authentication(userName : String,password: String)
    {
        val requestToLogin = RequestToLogin()
        requestToLogin.setUserName(userName)
        requestToLogin.setPassword(password)

        val loginResponseCall : Call<ResponseOfLogin> = generateCallToAPI().userLogin(requestToLogin)
        loginResponseCall.enqueue(object : Callback<ResponseOfLogin>{
            override fun onResponse(call: Call<ResponseOfLogin>, response: Response<ResponseOfLogin>) {

                if(response.isSuccessful)
                {
                    Toast.makeText(this@loginActivity,"Logged in Successfully",Toast.LENGTH_SHORT).show()

                    val userName1 : String  = response.body()!!.getUserNameInfo()
                    val userPhone1 : String = response.body()!!.getUserContactInfo()
                    val userMail1 : String = response.body()!!.getUserMailInfo()

                    sharedPreferences = getSharedPreferences(preferenceName,0)
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("userMail",userName)
                    editor.putString("userpassword",password)
                    editor.putString("userName",userName1)
                    editor.putString("userPhone",userPhone1)
                    editor.apply()

                    Handler().postDelayed(Runnable {
                        val intent : Intent = Intent(this@loginActivity,MainActivity::class.java)
                        intent.putExtra("userName",userName1)
                        intent.putExtra("userPhone",userPhone1)
                        intent.putExtra("userMail",userMail1)
                        startActivity(intent)
                    },700)
                }
                else
                {
                    Toast.makeText(this@loginActivity,"Failed to Login",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseOfLogin>, t: Throwable) {

                Toast.makeText(this@loginActivity,t.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        })
    }
}