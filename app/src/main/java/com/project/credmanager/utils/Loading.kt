package com.project.credmanager.utils

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.project.credmanager.R

object Loading {

    private var dialog: AlertDialog? = null

    fun showLoading(context: Context) {
        if (dialog?.isShowing == true) return

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.progress_dialog_layout, null)
        builder.setView(view)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.show()
    }

    fun dismissLoading() {
        dialog?.dismiss()
        dialog = null
    }

}