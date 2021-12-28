package com.chus.clua.breakingbad.presentation.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    fun showToast(message: String?) {
        message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
    }

    fun showToast(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}