package com.guelphwellingtonparamedicsapp

import android.app.Application
import android.content.Context
import com.google.android.material.internal.ContextUtils.getActivity
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

import org.mockito.Mock
import com.guelphwellingtonparamedicsapp.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.Mockito
import org.mockito.kotlin.mock


@RunWith(MockitoJUnitRunner::class)
class LoginUnitTest {

    @Test
    fun validateCorrectFormatOfEmail() {
        val result = Utils.validateEmail("user@user.com")
        assertEquals(true, result)
    }

    @Test
    fun validateCorrectSizeOfPassword() {
        val result = Utils.validatePassword("12345678")
        assertEquals(true, result)
    }

}
