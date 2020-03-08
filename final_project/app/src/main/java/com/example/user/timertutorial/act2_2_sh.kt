package com.example.user.timertutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView



class act2_2_sh : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act2_2_sh)

        val input_relate_store = findViewById<TextView>(R.id.input_relate_store) as TextView
        input_relate_store.text = " "
        val s = findViewById(R.id.spin_relation) as Spinner
        s.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long
            ) {
                input_relate_store.text = parent.getItemAtPosition(position) as CharSequence?
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
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

        var intent = getIntent()
        var getname = intent.getStringExtra("go_name")
        var getnum = intent.getStringExtra("go_number")
        var getrelate = intent.getStringExtra("go_relate")

        var getidx = intent.getIntExtra("go_index",1000)
        var check = intent.getIntExtra("check_key",1)


        var input_name_store = findViewById<EditText>(R.id.input_name_store)
        var input_num_store = findViewById<EditText>(R.id.input_num_store)

        var btn_numcancel_sh = findViewById<Button>(R.id.btn_numcancel_sh)
        var btn_numstore_sh = findViewById<Button>(R.id.btn_numstore_sh)


        if(check==100){
            input_name_store.setText(getname)
            input_num_store.setText(getnum)
            input_relate_store.setText(getrelate)
        }
        btn_numcancel_sh.setOnClickListener {
            //취소
            var nextIntent = Intent(this, act2_1_sh::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)
        }

        btn_numstore_sh.setOnClickListener {
            //text input
            var name = input_name_store.getText().toString()
            var number = input_num_store.getText().toString()
            var relate = input_relate_store.text

            var helper = DBphone(this)
            var db = helper.writableDatabase // 권한받기!! 꼭 해야함
            if(check==100){
                var sql = "update PhoneTable set nameData=? , numberData=? , relateData=? where idx=?"
                var mod = arrayOf(name,number,relate,getidx.toString())

                db.execSQL(sql,mod)
                db.close()
            }
            //저장
            //database
            else {
                var sql = "insert into PhoneTable (nameData, numberData, relateData)" + " values (?, ?, ?)"
                //쿼리문

                var name_arg = arrayOf(name, number, relate)
                db.execSQL(sql, name_arg)
                //insert버튼 누르면 arg1 arg2가 저장되어 있다.

                db.close()
                //database
            }

            var nextIntent = Intent(this, act2_1_sh::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show();
        }
    }
}
