package com.guelphwellingtonparamedicsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, LoginActivity::class.java)

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(3000)
            startActivity(intent)
            finish()
        }
    }
}