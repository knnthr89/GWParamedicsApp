package com.guelphwellingtonparamedicsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.guelphwellingtonparamedicsapp.R

class LoginActivity : AppCompatActivity() {
    lateinit var signinButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signinButton = findViewById(R.id.signin_button)

        signinButton.setOnClickListener {
            val intent = Intent(this, BottomNavigationActivity::class.java)
            startActivity(intent)
            finish()
        }
        supportActionBar?.hide()
    }
}