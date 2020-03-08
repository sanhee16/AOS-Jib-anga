package com.example.user.timertutorial

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ImageButton
import android.widget.*
import kotlinx.android.synthetic.main.activity_act3_sh.*


class act3_sh : AppCompatActivity() {

    var textData = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act3_sh)


        get_text()
        var txt_text_send = findViewById<TextView>(R.id.txt_text_send)
        txt_text_send.text = textData


        var imgbtnmap = findViewById<ImageButton>(R.id.imgbtn_map)
        var btnmap = findViewById<Button>(R.id.btn_map)

        var imgbtnboard = findViewById<ImageButton>(R.id.imgbtn_board)
        var btnboard = findViewById<Button>(R.id.btn_board)

        var imgbtnalarm = findViewById<ImageButton>(R.id.imgbtn_alarm)
        var btnalarm = findViewById<Button>(R.id.btn_alarm)

        imgbtnmap.setOnClickListener {

            var mapintent = Intent(this, MapsActivity::class.java)
            startActivity(mapintent)
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
            var backintent = Intent(this, act2_1_sh::class.java)
            startActivity(backintent)
            overridePendingTransition(0, 0)
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var intent = getIntent()
        var getidx = intent.getStringExtra("phonenum")

        var btn_del_sh = findViewById<Button>(R.id.btn_del_sh)
        var btn_modify_sh = findViewById<Button>(R.id.btn_modify_sh)

        var go_idx  = 0

        var helper = DBphone(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        //쿼리문 만들기
        var sql = "select*from PhoneTable"

        var c: Cursor = db.rawQuery(sql, null)
        txt_show_name.text = " "
        txt_show_num.text =" "
        txt_show_relate.text =" "

        while(c.moveToNext()){
            var idx_pos = c.getColumnIndex("idx")
            var nameData_pos = c.getColumnIndex("nameData")
            var numberData_pos = c.getColumnIndex("numberData")
            var relateData_pos = c.getColumnIndex("relateData")

            var order_idx = c.getInt(idx_pos)

            var nameData = c.getString(nameData_pos)
            var numberData= c.getString(numberData_pos)
            var relateData= c.getString(relateData_pos)

            if(numberData==getidx) {
                go_idx = order_idx
                txt_show_name.text = nameData
                txt_show_num.text  = numberData
                txt_show_relate.text  = relateData
            }
        }
        db.close()


        btn_del_sh.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog1, null)

            builder.setView(dialogView)
                .setPositiveButton("삭제") { dialogInterface, i ->

                    var helper = DBphone(this)
                    var db = helper.writableDatabase
                    var sql2 = "delete from PhoneTable where idx=?"
                    var remove = arrayOf(go_idx.toString())

                    db.execSQL(sql2, remove)
                    db.close()
                    var nextIntent = Intent(this, act2_1_sh::class.java)
                    startActivity(nextIntent)
                    overridePendingTransition(0, 0)

                }
                .setNegativeButton("취소") { dialogInterface, i ->

                }
                .show()


        }

        var btn_call = findViewById<Button>(R.id.btn_call)

        btn_call.setOnClickListener {

            var tel = txt_show_num.text
            var intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:"+tel))
            try {
                this.startActivity(intent)
            }catch (e : Exception) {
                e.printStackTrace()
            }

        }


        btn_modify_sh.setOnClickListener {

            var nextIntent = Intent(this, act2_2_sh::class.java)
            nextIntent.putExtra("go_name", txt_show_name.text as String) //이름
            nextIntent.putExtra("go_number", txt_show_num.text as String) //번호
            nextIntent.putExtra("go_relate", txt_show_relate.text as String) //번호


            nextIntent.putExtra("go_index", go_idx) //index
            nextIntent.putExtra("check_key", 100)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)
        }
    }

    fun get_text(){

        var helper = DBsms(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        //쿼리문 만들기
        var sql = "select * from smsTable"

        var c: Cursor = db.rawQuery(sql, null)

        var count = 0
        while(c.moveToNext()) {
            var idx_pos = c.getColumnIndex("idx")
            var sms_text_pos = c.getColumnIndex("smsData")

            count++
            var idx = c.getInt(idx_pos)
            textData = c.getString(sms_text_pos)

            if(count==1)
                break
        }

        db.close()
    }
}
