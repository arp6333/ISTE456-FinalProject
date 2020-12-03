package com.example.finalproject

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class InfoDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setTitle("Info")
            .setMessage("Final Project - Ellie Parobek and Lowell Pence")
            .create()
    }
}