package com.example.contacts.common

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import com.example.contacts.R

class ValidateHelper(private val context: Context) {

    fun validateFirstName(firstName: String): Boolean {
        return if (firstName.isEmpty()) {
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

    fun validateLastName(lastName: String): Boolean {
        return if (lastName.isEmpty()) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enter_your_lastName),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enter_your_email),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.invalid_email),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun validateUserImage(image: Uri?): Boolean {
        return if (image == null) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.please_select_image),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }


}