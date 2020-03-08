package com.example.user.timertutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class sh_notice1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sh_notice1)

        var imgbtn_back = findViewById<ImageButton>(R.id.imgbtn_back)
        imgbtn_back.setOnClickListener{

            var backintent = Intent(this, board_notice::class.java)
            startActivity(backintent)
            overridePendingTransition(0, 0)
        }

    }
}
