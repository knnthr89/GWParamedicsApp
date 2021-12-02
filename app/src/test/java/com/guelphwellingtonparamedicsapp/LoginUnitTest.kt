package com.guelphwellingtonparamedicsapp

import android.content.Context
import com.guelphwellingtonparamedicsapp.daos.UserDao
import com.guelphwellingtonparamedicsapp.database.AppDatabase
import com.guelphwellingtonparamedicsapp.entities.User
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Test

import com.guelphwellingtonparamedicsapp.utils.Utils
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.mock


@RunWith(MockitoJUnitRunner::class)
class LoginUnitTest {

    private var context: Context? = null

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        assertNotNull(context)
    }

    @Test
    fun validFormatOfEmail() {
        assertTrue(Utils.validateEmail("user@user.com"))
    }

    @Test
    fun emptyStringEmail() {
        assertFalse(Utils.validateEmail(""))
    }

    @Test
    fun invalidFormatOfEmail() {
        assertFalse(Utils.validateEmail("user.user"))
    }

    @Test
    fun validSizeOfPassword() {
        assertTrue(Utils.validatePassword("12345678"))
    }

    @Test
    fun emptyStringPassword() {
        assertFalse(Utils.validatePassword(""))
    }

    @Test
    fun invalidSizeOfPassword() {
        assertFalse(Utils.validatePassword("1238"))
    }

    @Test
    fun insertUserLocally(){
        var mUserDao: UserDao?
        AppDatabase.databaseWriteExecutor.execute {
            val db = context?.let { AppDatabase.getDatabase(context = it) }
            mUserDao = db!!.userDao()
            mUserDao?.insert(user = arrayOf(User(token = "12345678")))
            assertFalse(mUserDao!!.getUser().isEmpty())
        }
    }

    @Test
    fun readUserLocally(){
        var mUserDao: UserDao?
        AppDatabase.databaseWriteExecutor.execute {
            val db = context?.let { AppDatabase.getDatabase(context = it) }
            mUserDao = db!!.userDao()
            val user = mUserDao!!.getUser()[0]
            assertNotNull(user)
        }
    }

}
