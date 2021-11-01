package com.guelphwellingtonparamedicsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.databinding.ActivityLoginBinding
import com.guelphwellingtonparamedicsapp.manager.SessionManager

class LoginActivity : AppCompatActivity(), SessionManager.LoginListener {

    lateinit var mBinding : ActivityLoginBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        supportActionBar?.hide()

        mBinding.signinButton.setOnClickListener {
            if(mBinding.emailEt.text.toString().isNotBlank() && mBinding.passwordEt.text.toString().isNotBlank()){
                SessionManager.getInstance(this).setLoginListener(this)
                SessionManager.getInstance(this).getlogin(email = mBinding.emailEt.text.toString(), password = mBinding.passwordEt.text.toString())
            }else{
                Toast.makeText(this, "You need to type email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoginSuccess() {
        val intent = Intent(this, BottomNavigationActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginFail(message: String, code: Int?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}