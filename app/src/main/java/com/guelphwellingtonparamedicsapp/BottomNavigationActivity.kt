package com.guelphwellingtonparamedicsapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.guelphwellingtonparamedicsapp.fragments.AssessmentsFragment
import com.guelphwellingtonparamedicsapp.fragments.ContactsFragment
import com.guelphwellingtonparamedicsapp.fragments.ResourcesFragment

class BottomNavigationActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        supportActionBar!!.hide()
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.navigation_assessments
    }

    fun showFragment(fragment: Fragment, addToStack: Boolean = true) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
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
    }