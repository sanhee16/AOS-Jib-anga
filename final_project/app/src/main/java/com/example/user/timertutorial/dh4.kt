package com.example.user.timertutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button


class dh4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dh4)

        var btn4_dh = findViewById<Button>(R.id.btn4_dh)
        btn4_dh.setOnClickListener {
            finish()
        }

    }
}
