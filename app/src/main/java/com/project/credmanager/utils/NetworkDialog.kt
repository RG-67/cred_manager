package com.project.credmanager.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.TextView
import com.project.credmanager.R

object NetworkDialog {

    fun showNetworkDialog(context: Context): AlertDialog {

        val dialogView = LayoutInflater.from(context).inflate(R.layout.network_monitor_layout, null)

        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<TextView>(R.id.openBtn).setOnClickListener {
            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        alertDialog.show()
        return alertDialog

    }

}