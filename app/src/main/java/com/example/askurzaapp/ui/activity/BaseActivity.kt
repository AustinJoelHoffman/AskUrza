package com.example.askurzaapp.ui.activity

import android.content.Intent
import android.view.KeyEvent
import androidx.activity.ComponentActivity

open class BaseActivity : ComponentActivity() {

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            startActivity(Intent(this, EscapeActivity::class.java))
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
