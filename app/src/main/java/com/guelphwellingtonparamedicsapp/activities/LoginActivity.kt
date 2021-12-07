package com.guelphwellingtonparamedicsapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.daos.UserDao
import com.guelphwellingtonparamedicsapp.database.AppDatabase
import com.guelphwellingtonparamedicsapp.databinding.ActivityLoginBinding
import com.guelphwellingtonparamedicsapp.entities.User
import com.guelphwellingtonparamedicsapp.manager.SessionManager
import com.guelphwellingtonparamedicsapp.utils.Utils
import android.os.Vibrator



class LoginActivity : AppCompatActivity(), SessionManager.LoginListener {

    lateinit var activityLoginBinding: ActivityLoginBinding

    private var mUserDao: UserDao? = null
    private var list : List<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        supportActionBar?.hide()

        activityLoginBinding.signinButton.setOnClickListener {
           Utils.vibrate(application = application)
            if (Utils.hasInternetConnection(context = this)) {
                if(Utils.validateEmail(activityLoginBinding.emailEt.text.toString()) && Utils.validatePassword(activityLoginBinding.passwordEt.toString())){
                    if (activityLoginBinding.emailEt.text.toString()
                            .isNotBlank() && activityLoginBinding.passwordEt.text.toString()
                            .isNotBlank()
                    ) {
                        SessionManager.getInstance(this).setLoginListener(this)
                        SessionManager.getInstance(this).getlogin(
                            email = activityLoginBinding.emailEt.text.toString(),
                            password = activityLoginBinding.passwordEt.text.toString()
                        )
                    } else {
                        Toast.makeText(this, getString(R.string.login_email_error), Toast.LENGTH_SHORT)
                            .show()
                    }
                }else{
                    Toast.makeText(this, getString(R.string.email_validation), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        activityLoginBinding.emailEt.setText("")
        activityLoginBinding.passwordEt.setText("")

    }

    override fun onLoginSuccess(token: String) {

        Log.e("token", token)

        AppDatabase.databaseWriteExecutor.execute {

            val db = AppDatabase.getDatabase(this.application)
            var user = User(token = token)

            mUserDao = db!!.userDao()

            if(user != null){
                mUserDao?.insert(user = arrayOf(user))
                list = mUserDao!!.getUser()

                if(list.isNotEmpty()){
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    startActivity(intent)
                }else{
                   Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoginFail(message: String, code: Int?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()

        val db = AppDatabase.getDatabase(this.application)
        mUserDao = db!!.userDao()

        if(mUserDao!!.getUser().isNotEmpty())  mUserDao!!.deleteAll()

    }
}