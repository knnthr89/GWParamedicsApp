package com.guelphwellingtonparamedicsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.database.AppDatabase
import com.guelphwellingtonparamedicsapp.utils.Utils
import java.io.Serializable
import android.widget.Toast
import com.guelphwellingtonparamedicsapp.fragments.*

class BottomNavigationActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        //supportActionBar!!.hide()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.navigation_assessments

        if (!Utils.hasInternetConnection(context = this)) {
            val intent = Intent(this, OfflineActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            this.startActivity(intent)
        }
    }

    fun showFragment(
        fragment: Fragment,
        addToStack: Boolean = true,
        model : Serializable? = null,
        url : String? = null,
        back : Boolean? = null
    ) {
        try {
            val args = Bundle()
            args.putSerializable("model", model)
            args.putString("url", url)
            if (back != null) {
                args.putBoolean("back", back)
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            fragment.arguments = args
            transaction.replace(R.id.nav_host_fragment_activity_bottom_navigation, fragment)

            if (addToStack) {
                transaction.addToBackStack(null)
            }
            transaction.commitAllowingStateLoss()

        } catch (e: Exception) {
            Log.e(this.javaClass.toString(), e.message!!)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Utils.vibrate(application = application)
        when (item.itemId) {
            R.id.navigation_assessments -> {
                showFragment(SectionsFragment(), false)
            }
            R.id.navigation_resources -> {
                showFragment(ResourcesFragment(), false)
            }
            R.id.navigation_contacts -> {
                showFragment(ContactsFragment(), false)
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()

        AppDatabase.databaseWriteExecutor.execute {
            val db = AppDatabase.getDatabase(this.application)
            var userDao = db!!.userDao()
            var user = userDao.getUser()

            if(user.isNotEmpty()) {
                for(u in user){
                    Log.e("user", u.token.toString())
                }
            }else{
                Log.e("user list", "empty")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.navigation_logout) {
            finish()
            return true
        }

        if (id == R.id.navigation_protection) {
            showFragment(WebViewFragment(), url = "https://guelph.ca/city-hall/access-to-information/personal-health-information-protection-act/", back = false)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
