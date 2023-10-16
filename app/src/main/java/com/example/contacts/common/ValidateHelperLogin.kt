package com.example.contacts.common

import android.content.Context
import android.widget.Toast
import com.example.contacts.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidateHelperLogin(private val context: Context) {

    fun validateEmptyName(name: String): Boolean {
        return if (name.isEmpty()) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enter_your_firstName),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun validateName(name: String): Boolean {
        return if (name.length < 8) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.username_should_8_digits),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun validateEmptyPassword(password: String): Boolean {
        return if (password.isEmpty()) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enter_your_password),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun validatePassword(password: String): Boolean {
        return if (password.length < 8) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.password_should_8_digits),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[@#\$%^&+=]).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password.toString())
        return matcher.matches()
    }


}