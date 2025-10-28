package com.project.credmanager.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

object HandleUserInput {

    fun checkUserInput(
        isLogin: Boolean, phone: String, email: String, password: String, confirmPassword: String
    ): Pair<String, Boolean> {
        if (phone.isEmpty() || phone.length < 10) {
            return Pair("Enter valid number", false)
        } else if (email.isEmpty()) {
            return Pair("Enter email address", false)
        } else if (password.isEmpty()) {
            return Pair("Enter password", false)
        } else if (!isLogin && confirmPassword.isEmpty()) {
            return Pair("Enter password", false)
        } else if (!isLogin && password != confirmPassword) {
            return Pair("Password does not match", false)
        }
        return Pair("", true)
    }

    fun checkCredInput(
        title: String,
        userName: String,
        password: String,
        description: String
    ): Pair<String, Boolean> {
        if (title == "") {
            return Pair("Enter title", false)
        } else if (userName == "") {
            return Pair("Enter username", false)
        } else if (password == "") {
            return Pair("Enter password", false)
        } else if (description == "") {
            return Pair("Enter description", false)
        }
        return Pair("", true)
    }

    fun checkProfileInput(
        phone: String,
        email: String,
        oldPass: String,
        newPass: String,
        conFirmPass: String
    ): Pair<String, Boolean> {
        if (phone.isEmpty() || phone.length < 10) {
            return Pair("Enter valid number", false)
        } else if (email.isEmpty()) {
            return Pair("Enter email id", false)
        } else if (oldPass.isEmpty()) {
            return Pair("Enter old password", false)
        } else if (newPass.isEmpty()) {
            return Pair("Enter new password", false)
        } else if (conFirmPass.isEmpty()) {
            return Pair("Enter confirm password", false)
        } else if (newPass != conFirmPass) {
            return Pair("New and confirm password should same", false)
        }
        return Pair("", true)
    }

    fun bcryptHash(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(password: String, bcryptHash: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHash)
        return result.verified
    }

    fun checkVerifyPassInput(number: String, email: String): Pair<String, Boolean> {
        if (number.isEmpty() || number.length != 10) {
            return Pair("Enter valid phone number", false)
        } else if (email.isEmpty()) {
            return Pair("Enter email address", false)
        }
        return Pair("", true)
    }

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}