package com.guelphwellingtonparamedicsapp.activities

import android.app.Activity
import android.content.Context
import android.support.test.InstrumentationRegistry
import androidx.appcompat.view.menu.MenuView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.activityScenarioRule
import com.guelphwellingtonparamedicsapp.R
import org.junit.Rule
import org.junit.Test
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers


class BottomNavigationActivityTest{
    val targetContext: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    var activityScenarioRule = activityScenarioRule<BottomNavigationActivity>()

    @Test
    fun closeSession(){
        openActionBarOverflowOrOptionsMenu(targetContext)
        onView(withText("Logout")).perform(click())
        activityScenarioRule.scenario.state.isAtLeast(Lifecycle.State.DESTROYED)
    }

    @Test
    fun openWebview(){
        openActionBarOverflowOrOptionsMenu(targetContext)
        onView(withText("Information Protection Act")).perform(click())
        onView(withId(R.id.fragmentWebview)).check(matches(isDisplayed()));
    }
}