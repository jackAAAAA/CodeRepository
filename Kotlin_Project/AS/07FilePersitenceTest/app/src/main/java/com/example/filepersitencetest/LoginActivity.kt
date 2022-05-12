package com.example.filepersitencetest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val pres = getSharedPreferences("data", Context.MODE_PRIVATE)
        val isRemember = pres.getBoolean("remember_password", false)
        if (isRemember) {
            val account = pres.getString("account", "")
            val password = pres.getString("password", "")
            accountEdit.setText(account)
            passwordEdit.setText(password)
            rememberPass.isChecked = true
        }
        login.setOnClickListener {
            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()
            if (account == "admin" && password == "123456") {
                val editor = pres.edit()
                if (rememberPass.isChecked) {
                    editor.putBoolean("remember_password", true)
                    editor.putString("account", "admin")
                    editor.putString("password", "123456")
                } else {
                    editor.clear()
                }
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "The account or password is invalid!", Toast.LENGTH_LONG).show()
            }
        }
    }
}