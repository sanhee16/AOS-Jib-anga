package com.example.user.timertutorial

import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.*

class board_link : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_link)
        /////////////////////////////////////////////////////////////////////////////////////
        var helper = DBboard(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        //쿼리문 만들기
        var sql = "select*from BoardTable"
        var idx = 0
        var cateData = ""
        var titleData = ""
        var contentData = ""
        var dateData = ""

        var c: Cursor = db.rawQuery(sql, null)
        var txt_show_cate = ""
        var txt_show_title = ""
        var txt_show_content = ""

        while (c.moveToNext()) {
            var idx_pos = c.getColumnIndex("idx")
            var cateData_pos = c.getColumnIndex("cateData")
            var titleData_pos = c.getColumnIndex("titleData")
            var dateData_pos = c.getColumnIndex("dateData")
            var contentData_pos = c.getColumnIndex("contentData")

            idx = c.getInt(idx_pos)
            cateData = c.getString(cateData_pos)
            titleData = c.getString(titleData_pos)
            dateData = c.getString(dateData_pos)
            contentData = c.getString(contentData_pos)

            txt_show_cate = cateData.toString()
            txt_show_title = titleData.toString()
            txt_show_content = contentData.toString()

            if (txt_show_cate == "링크") {
                val linearLayout = findViewById(R.id.linearLayout) as LinearLayout

                var button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                button.layoutParams.height = ListPopupWindow.WRAP_CONTENT
                button.layoutParams.width = ListPopupWindow.MATCH_PARENT
                button.text = txt_show_title

                button.textSize = 15f
                button.setPadding(50, 25, 0, 0)
                button.setBackgroundColor(0xFFFFFF)
                button.gravity = R.id.left
                button.background = ContextCompat.getDrawable(this, R.drawable.short_bottom)


                button.setOnClickListener {
                    var nextIntent = Intent(this, detail_text2::class.java)
                    nextIntent.putExtra("boardtitle", button.text as String) //idx보내기
                    startActivity(nextIntent)
                }
                linearLayout.addView(button)
            }
        }
        db.close()

        /////////////////////////////////////////////////////////////////////////////////////
        var btn_board_tap1_sh = findViewById<Button>(R.id.btn_board_tap1_sh)
        var btn_board_tap2_sh = findViewById<Button>(R.id.btn_board_tap2_sh)
        var btn_board_tap4_sh = findViewById<Button>(R.id.btn_board_tap4_sh)

        btn_board_tap1_sh.setOnClickListener {
            var nextIntent = Intent(this, board_notice::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)

        }
        btn_board_tap2_sh.setOnClickListener {
            var nextIntent = Intent(this, board_tip::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)

        }

        btn_board_tap4_sh.setOnClickListener {
            var nextIntent = Intent(this, board_help::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)
        }


        var imgbtnmap = findViewById<ImageButton>(R.id.imgbtn_map)
        var btnmap = findViewById<Button>(R.id.btn_map)

        var imgbtndanger = findViewById<ImageButton>(R.id.imgbtn_danger)
        var btndanger = findViewById<Button>(R.id.btn_danger)

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

        imgbtndanger.setOnClickListener {

            var dangerintent = Intent(this, act1_sh::class.java)
            startActivity(dangerintent)
            overridePendingTransition(0, 0)
        }
        btndanger.setOnClickListener {

            var dangerintent = Intent(this, act1_sh::class.java)
            startActivity(dangerintent)
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
    }


}
