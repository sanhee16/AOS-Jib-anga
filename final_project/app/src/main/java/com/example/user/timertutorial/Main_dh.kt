package com.example.user.timertutorial

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_main_dh.*

class Main_dh : AppCompatActivity() {

    var dHandler:DisplayHandler? = null   //null: 할당도 되어있고 초기화도 되어있지만 모르는 값이 들어있는 것, 아직 사용 안 할 거니까 null이용
    //하지만 코틀린에서 null값 취급 안함 -> ?사용하여 허락받음
    var pHandler:ImageHandler? = null
    var time: Int = 0
    var tick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dh)

        var get_distance = intent.getDoubleExtra("distance_map",0.0)
        //Toast.makeText(applicationContext, "거리 : "+get_distance+"m", Toast.LENGTH_LONG).show()
        time = get_distance.toInt()


        dHandler = DisplayHandler()
        pHandler = ImageHandler()

        var txt_time =findViewById<TextView>(R.id.txt_time)
        var btn_start =findViewById<Button>(R.id.btn_start)
        var btn_end =findViewById<Button>(R.id.btn_end)
        var img_clock = findViewById<ImageView>(R.id.img_clock)

        if(time > 0){
            btn_start.setOnClickListener { //start
                dHandler?.sendEmptyMessage(1) //null값이 들어오거나 값을 잊어버릴 수 있는데 이를 허락해달라는 의미로 ?사용
                pHandler?.sendEmptyMessage(2)
            }
        }
        else if(time == 0){
            btn_start.setOnClickListener {
                dHandler?.removeMessages(1)
                pHandler?.removeMessages(2)
            }
        }

        btn_end.setOnClickListener { //pause
            time = 0
            dHandler?.removeMessages(1)
            pHandler?.removeMessages(2)

            val builder = AlertDialog. Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_dh3, null)
            val showdg = builder.setView(dialogView).create()

            var btn3_dh = dialogView.findViewById<Button>(R.id.btn3_dh)
            showdg.show()

            btn3_dh.setOnClickListener {
                var intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }

        var imgbtnmap = findViewById<ImageButton>(R.id.imgbtn_map)
        var btnmap = findViewById<Button>(R.id.btn_map)

        var imgbtnboard = findViewById<ImageButton>(R.id.imgbtn_board)
        var btnboard = findViewById<Button>(R.id.btn_board)

        var imgbtndanger = findViewById<ImageButton>(R.id.imgbtn_danger)
        var btndanger = findViewById<Button>(R.id.btn_danger)

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
    }

    inner class DisplayHandler : Handler(){ //class안에 존재(함수화?), 원랜 class안에 변수와 함수
        override fun handleMessage(msg: Message?) { //handle치고 엔터누르면 바로 아랫줄까지 나옴
            super.handleMessage(msg)

            var hour = time / 3600
            var minute = (time % 3600) / 60
            var second = time % 60
            txt_time.text = minute.toString().padStart(2, '0')
            txt_time1.text = second.toString().padStart(2, '0')
            txt_time2.text = hour.toString().padStart(2, '0')
            time--
            if(time == 0){

                try {
                    //전송
                    sendsms()
                    Toast.makeText(applicationContext, "전송 완료", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "전송 실패", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }

                var intent = Intent(this@Main_dh, dh4::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }
            dHandler?.sendEmptyMessageDelayed(1,1000) //0.01초
        }
    }

    inner class ImageHandler : Handler() {
        override fun handleMessage(msg: Message?) { //handle치고 엔터누르면 바로 아랫줄까지 나옴
            super.handleMessage(msg)

            if((tick%6)==0) {
                img_clock.setImageResource(R.drawable.walk1)
            }
            else if((tick%6)==1){
                img_clock.setImageResource(R.drawable.walk2)
            }
            else if((tick%6)==2){
                img_clock.setImageResource(R.drawable.walk3)
            }
            else if((tick%6)==3){
                img_clock.setImageResource(R.drawable.walk4)
            }
            else if((tick%6)==4){
                img_clock.setImageResource(R.drawable.walk5)
            }
            else if((tick%6)==5){
                img_clock.setImageResource(R.drawable.walk6)
            }
            tick ++
            pHandler?.sendEmptyMessageDelayed(2, 100)
        }
    }

    fun sendsms() {

        var helper = DBphone(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        var phoneNo = ""
        val sms = "집에 도착하지 않았습니다." // db로 하고 싶음

        //쿼리문 만들기
        var sql = "select*from PhoneTable"

        var c: Cursor = db.rawQuery(sql, null)

        while (c.moveToNext()) {
            var idx_pos = c.getColumnIndex("idx")
            var nameData_pos = c.getColumnIndex("nameData")
            var numberData_pos = c.getColumnIndex("numberData")

            var order_idx = c.getInt(idx_pos)
            var nameData = c.getString(nameData_pos)
            var numberData = c.getString(numberData_pos)
            phoneNo = numberData

            Toast.makeText(applicationContext, phoneNo, Toast.LENGTH_LONG).show()
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNo, null, sms, null, null)
        }
    }
}