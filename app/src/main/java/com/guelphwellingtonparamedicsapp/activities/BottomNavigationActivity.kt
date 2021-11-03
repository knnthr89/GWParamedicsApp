package com.guelphwellingtonparamedicsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.database.AppDatabase
import com.guelphwellingtonparamedicsapp.fragments.AssessmentsFragment
import com.guelphwellingtonparamedicsapp.fragments.ContactsFragment
import com.guelphwellingtonparamedicsapp.fragments.ResourcesFragment
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.utils.Utils

class BottomNavigationActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        supportActionBar!!.hide()
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
        individualFormModel: IndividualFormModel? = null
    ) {
        try {
            val args = Bundle()
            args.putSerializable("individualForm", individualFormModel)
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
        when (item.itemId) {
            R.id.navigation_assessments -> {
                showFragment(AssessmentsFragment(), false)
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
}
