package com.chucknorrisfacts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chucknorrisfacts.search.presentation.facts.FactsActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, FactsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}
