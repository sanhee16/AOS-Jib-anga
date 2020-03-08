package com.example.user.timertutorial

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView

class detail_text2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_text2)

        var txt_titile_detail_sh = findViewById<TextView>(R.id.txt_titile_detail_sh)
        var txt_content_detail_sh = findViewById<TextView>(R.id.txt_content_detail_sh)
        var txt_date = findViewById<TextView>(R.id.txt_date)

        var imgbtn_back = findViewById<ImageButton>(R.id.imgbtn_back)
        imgbtn_back.setOnClickListener{

            var backintent = Intent(this, board_link::class.java)
            startActivity(backintent)
            overridePendingTransition(0, 0)
        }

        //////////////////////
        var intent = getIntent()
        var get_title = intent.getStringExtra("boardtitle")

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

        while(c.moveToNext()){
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

            if(titleData==get_title) {
                txt_titile_detail_sh.text = titleData
                txt_content_detail_sh.text = contentData
                txt_date.text = dateData
            }
        }
        db.close()

    }
}
