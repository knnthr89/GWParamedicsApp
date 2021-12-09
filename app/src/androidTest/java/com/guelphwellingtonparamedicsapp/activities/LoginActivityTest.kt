package com.guelphwellingtonparamedicsapp.activities

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.res.Resources
import android.support.test.InstrumentationRegistry
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.ToastMatcher
import org.hamcrest.CoreMatchers
import java.util.*
import kotlin.coroutines.coroutineContext


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest{

    val targetContext: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    var activityScenarioRule = activityScenarioRule<LoginActivity>()

    @Test
    fun dataIsCorrect() {
        typeInEmptyField(R.id.email_et, "admin@guelph.ca")
        typeInEmptyField(R.id.password_et, "Admin!234")
        onView(withId(R.id.password_et)).perform(closeSoftKeyboard())
        clickButton(R.id.signin_button)
        compareText(R.id.errorMessage, "")
    }

    @Test
    fun emailFormatIncorrect() {
        typeInEmptyField(R.id.email_et, "admin.ca")
        typeInEmptyField(R.id.password_et, "Admin!234")
        onView(withId(R.id.password_et)).perform(closeSoftKeyboard())
        clickButton(R.id.signin_button)
        compareText(R.id.errorMessage, targetContext.getString(R.string.email_validation))
    }

    @Test
    fun passwordSizeError() {
        typeInEmptyField(R.id.email_et, "admin@guelph.ca")
        typeInEmptyField(R.id.password_et, "123")
        onView(withId(R.id.password_et)).perform(closeSoftKeyboard())
        clickButton(R.id.signin_button)
        compareText(R.id.errorMessage, targetContext.getString(R.string.password_validation))
    }

    @Test
    fun showMessageForgotPassword() {
        clickButton(R.id.forgotPasswordTv)
        compareText(R.id.errorMessage, targetContext.getString(R.string.forgot_password_message))

    }

    fun typeInEmptyField(parentId: Int, text: String) {
        onView(
            withId(parentId)
        ).perform(typeText(text))
    }

    fun clickButton(parentId: Int) {
        onView(
            withId(parentId)
        ).perform(click())
    }

    fun compareText(parentId: Int, text: String) {
        onView(
            withId(parentId)
        ).check(matches(withText(text)))
    }

}