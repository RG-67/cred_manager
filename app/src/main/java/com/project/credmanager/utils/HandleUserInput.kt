package com.project.credmanager.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.android.material.snackbar.Snackbar

object HandleUserInput {

    fun checkUserInput(
        isLogin: Boolean, phone: String, password: String, confirmPassword: String
    ): Pair<String, Boolean> {
        if (phone.isNullOrEmpty() || phone.length < 10) {
            return Pair("Enter valid number", false)
        } else if (password.isNullOrEmpty()) {
            return Pair("Enter password", false)
        } else if (!isLogin && confirmPassword.isNullOrEmpty()) {
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

    fun bcryptHash(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(password: String, bcryptHash: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHash)
        return result.verified
    }

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}