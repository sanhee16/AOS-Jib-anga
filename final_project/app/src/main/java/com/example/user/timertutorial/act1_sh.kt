package com.example.user.timertutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class act1_sh : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms)
        var check_opt1 = findViewById<CheckBox>(R.id.check_opt1)
        var check_opt2 = findViewById<CheckBox>(R.id.check_opt2)
        var check_opt3 = findViewById<CheckBox>(R.id.check_opt3)
        var check_opt4 = findViewById<CheckBox>(R.id.check_opt4)

        var txt_opt1 = findViewById<TextView>(R.id.txt_opt1)
        var txt_opt2 = findViewById<TextView>(R.id.txt_opt2)
        var txt_opt3 = findViewById<TextView>(R.id.txt_opt3)
        //var txt_opt4 = findViewById<TextView>(R.id.txt_opt4)

        var btn_cancel_sms = findViewById<Button>(R.id.btn_cancel_sms)
        var btn_store_sms = findViewById<Button>(R.id.btn_store_sms)

        var editTextSMS = findViewById<EditText>(R.id.edittxt_sms)

        var store_sms_text=" "

        check_opt1.setOnClickListener {
            check_opt3.setChecked(false);
            check_opt2.setChecked(false);
            check_opt4.setChecked(false);
            store_sms_text = txt_opt1.text as String
        }

        check_opt2.setOnClickListener {
            check_opt1.setChecked(false);
            check_opt3.setChecked(false);
            check_opt4.setChecked(false);
            store_sms_text = txt_opt2.text as String

        }

        check_opt3.setOnClickListener {
            check_opt1.setChecked(false);
            check_opt2.setChecked(false);
            check_opt4.setChecked(false);

            store_sms_text = txt_opt3.text as String
        }
        var check4 = 0

        check_opt4.setOnClickListener {
            check_opt1.setChecked(false);
            check_opt2.setChecked(false);
            check_opt3.setChecked(false);

            check4=1
        }

        btn_store_sms.setOnClickListener {
            if(check4==1) {
                store_sms_text = editTextSMS.text.toString()
                if(store_sms_text==""){
                    store_sms_text = "집에 도착하지 않았습니다."
                }
            }

            var helper = DBsms(this)
            Toast.makeText(applicationContext, "update " + store_sms_text, Toast.LENGTH_LONG).show()
            var db = helper.writableDatabase // 권한받기!! 꼭 해야함
            var sql = "update smsTable set smsData=? where idx=?"
            var args = arrayOf(store_sms_text,"1")
            //쿼리문
            db.execSQL(sql,args)


            var nextIntent = Intent(this, act1_sh::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)

            // 설정 값 저장하기

        }


        btn_cancel_sms.setOnClickListener {
            var nextIntent = Intent(this, act1_sh::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)
        }



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

        var btn_phone_start_sh = findViewById<Button>(R.id.btn_phone_start_sh2)
        btn_phone_start_sh.setOnClickListener {
            var nextIntent = Intent(this, act2_1_sh::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)

        }

    }
}
