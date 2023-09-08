package com.example.cgtaska.common

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.Toast

class ValidateHelper(private val context: Context) {

    fun validateFirstName(firstName: String): Boolean {
        return if (firstName.isEmpty()) {
            Toast.makeText(context, "Please enter your first name", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun validateLastName(lastName: String): Boolean {
        return if (lastName.isEmpty()) {
            Toast.makeText(context, "Please enter your last name", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            Toast.makeText(context, "Please enter your email address", Toast.LENGTH_SHORT).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun validateUserImage(image: Uri?): Boolean {
        return if (image == null) {
            Toast.makeText(context, "Please select your image", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }


}