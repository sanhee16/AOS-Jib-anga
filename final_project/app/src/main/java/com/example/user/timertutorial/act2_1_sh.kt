package com.example.user.timertutorial

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.R.id.left
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.support.v4.content.ContextCompat
import android.os.Build
import android.support.annotation.RequiresApi


class act2_1_sh : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act2_1_sh)

        var imgbtnmap = findViewById<ImageButton>(R.id.imgbtn_map)
        var btnmap = findViewById<Button>(R.id.btn_map)

        var imgbtnboard = findViewById<ImageButton>(R.id.imgbtn_board)
        var btnboard = findViewById<Button>(R.id.btn_board)

        var imgbtnalarm = findViewById<ImageButton>(R.id.imgbtn_alarm)
        var btnalarm = findViewById<Button>(R.id.btn_alarm)

        imgbtnmap.setOnClickListener {

            var mapintent = Intent(this, MapsActivity::class.java)
            startActivity(mapintent)
            overridePendingTransition(0, 0)
        }
        btnmap.setOnClickListener {

            var mapintent = Intent(this, MapsActivity::class.java)
            startActivity(mapintent)
            overridePendingTransition(0, 0)
        }

        imgbtnboard.setOnClickListener {

            var boardintent = Intent(this, board_notice::class.java)
            startActivity(boardintent)
            overridePendingTransition(0, 0)
        }
        btnboard.setOnClickListener {

            var boardintent = Intent(this, board_notice::class.java)
            startActivity(boardintent)
            overridePendingTransition(0, 0)
        }

        imgbtnalarm.setOnClickListener {

            var alarmintent = Intent(this, Main_dh::class.java)
            startActivity(alarmintent)
            overridePendingTransition(0, 0)
        }
        btnalarm.setOnClickListener {

            var alarmintent = Intent(this, Main_dh::class.java)
            startActivity(alarmintent)
            overridePendingTransition(0, 0)
        }

        var imgbtn_back = findViewById<ImageButton>(R.id.imgbtn_back)
        imgbtn_back.setOnClickListener{

            var backintent = Intent(this, act1_sh::class.java)
            startActivity(backintent)
            overridePendingTransition(0, 0)
        }

        var btn_add_sh = findViewById<Button>(R.id.btn_add_sh)

        var helper = DBphone(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        //쿼리문 만들기
        var sql = "select*from PhoneTable"
        var idx = 0
        var nameData = ""
        var numberData = ""
        var relateData = ""

        var c: Cursor = db.rawQuery(sql, null)
        var txt_show_name = ""
        var txt_show_num = ""

        while (c.moveToNext()) {
            var idx_pos = c.getColumnIndex("idx")
            var nameData_pos = c.getColumnIndex("nameData")
            var numberData_pos = c.getColumnIndex("numberData")
            var relateData_pos = c.getColumnIndex("relateData")

            idx = c.getInt(idx_pos)
            nameData = c.getString(nameData_pos)
            numberData = c.getString(numberData_pos)
            relateData= c.getString(relateData_pos)
            txt_show_name = nameData.toString()
            txt_show_num = numberData.toString()
            var relate_var = relateData

            if(relate_var == "친구") {
                val friend = findViewById(R.id.friend) as LinearLayout

                var button_friend = Button(this)
                button_friend.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                button_friend.layoutParams.height = MATCH_PARENT
                button_friend.layoutParams.width = MATCH_PARENT
                button_friend.textSize = 18f
                button_friend.setPadding(50, 25, 0, 0)
                button_friend.background = ContextCompat.getDrawable(this, R.drawable.thin2) //drawable 바꾸기
                button_friend.gravity = left

                var next = txt_show_num
                button_friend.text = txt_show_name + "  " + txt_show_num
                button_friend.setOnClickListener {
                    var nextIntent = Intent(this, act3_sh::class.java)
                    nextIntent.putExtra("phonenum", next as String) //idx보내기
                    startActivity(nextIntent)
                    overridePendingTransition(0, 0)
                }
                friend.addView(button_friend)

            }

            if(relate_var == "가족") {
                val family = findViewById(R.id.family) as LinearLayout

                var button_family = Button(this)
                button_family.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                    )

                button_family.layoutParams.height = MATCH_PARENT
                button_family.layoutParams.width = MATCH_PARENT
                button_family.textSize = 18f
                button_family.setPadding(50, 25, 0, 0)
                button_family.background = ContextCompat.getDrawable(this, R.drawable.thin2) //drawable 바꾸기
                button_family.gravity = left

                var next = txt_show_num
                button_family.text = txt_show_name + "  " + txt_show_num
                button_family.setOnClickListener {
                    var nextIntent = Intent(this, act3_sh::class.java)
                    nextIntent.putExtra("phonenum", next as String) //idx보내기
                    startActivity(nextIntent)
                    overridePendingTransition(0, 0)
                }
                family.addView(button_family)

            }

            if(relate_var == "지인") {
                val acquaintance = findViewById(R.id.acquaintance) as LinearLayout

                var button_acq = Button(this)
                button_acq.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                button_acq.layoutParams.height = MATCH_PARENT
                button_acq.layoutParams.width = MATCH_PARENT
                button_acq.textSize = 18f
                button_acq.setPadding(50, 25, 0, 0)
                button_acq.background = ContextCompat.getDrawable(this, R.drawable.thin2) //drawable 바꾸기
                button_acq.gravity = left

                var next = txt_show_num
                button_acq.text = txt_show_name + "  " + txt_show_num
                button_acq.setOnClickListener {
                    var nextIntent = Intent(this, act3_sh::class.java)
                    nextIntent.putExtra("phonenum", next as String) //idx보내기
                    startActivity(nextIntent)
                    overridePendingTransition(0, 0)
                }
                acquaintance.addView(button_acq)

            }

            if(relate_var == "친척") {
                val relative = findViewById(R.id.relative) as LinearLayout

                var button_rel = Button(this)
                button_rel.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                button_rel.layoutParams.height = MATCH_PARENT
                button_rel.layoutParams.width = MATCH_PARENT
                button_rel.textSize = 18f
                button_rel.setPadding(50, 25, 0, 0)
                button_rel.background = ContextCompat.getDrawable(this, R.drawable.thin2) //drawable 바꾸기
                button_rel.gravity = left

                var next = txt_show_num
                button_rel.text = txt_show_name + "  " + txt_show_num
                button_rel.setOnClickListener {
                    var nextIntent = Intent(this, act3_sh::class.java)
                    nextIntent.putExtra("phonenum", next as String) //idx보내기
                    startActivity(nextIntent)
                    overridePendingTransition(0, 0)
                }
                relative.addView(button_rel)

            }
        }

        db.close()

        btn_add_sh.setOnClickListener {
            var nextIntent = Intent(this, act2_2_sh::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)
        }

    }
}