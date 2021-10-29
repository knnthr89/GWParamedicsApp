package com.guelphwellingtonparamedicsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.utils.Utils

class OfflineActivity : AppCompatActivity() {

    private lateinit var reload : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline)
        reload = findViewById(R.id.reload)

        supportActionBar?.hide()

        reload.setOnClickListener {
            if (Utils.hasInternetConnection(this)) {
                this.finish()
            }
        }
    }
}