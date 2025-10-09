package com.project.credmanager.utils

import android.app.ProgressDialog
import android.content.Context
import android.util.Log

object Loading {

    private var prDialog: ProgressDialog? = null

    fun showLoading(context: Context) {
        if (prDialog == null) {
            prDialog = ProgressDialog(context)
            prDialog?.setMessage("Please wait.....")
            prDialog?.setCancelable(false)
        }
        prDialog?.show()
    }

    fun dismissLoading() {
        prDialog?.dismiss()
    }

}