package com.example.user.timertutorial

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class First_logo : AppCompatActivity() {

    var start=1
    var FHandler:FirstHandler? = null
    var SHandler:SecondHandler? = null   //null: 할당도 되어있고 초기화도 되어있지만 모르는 값이 들어있는 것, 아직 사용 안 할 거니까 null이용
    //하지만 코틀린에서 null값 취급 안함 -> ?사용하여 허락받음
    var time: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_logo)

        time = 2
        FHandler = FirstHandler()
        FHandler?.sendEmptyMessage(0)
    }

    inner class FirstHandler : Handler(){ //class안에 존재(함수화?), 원랜 class안에 변수와 함수
        override fun handleMessage(msg: Message?) { //handle치고 엔터누르면 바로 아랫줄까지 나옴
            super.handleMessage(msg)
            val permissionCheck = ContextCompat.checkSelfPermission(this@First_logo,android.Manifest.permission.ACCESS_FINE_LOCATION)
            if(permissionCheck == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    this@First_logo,
                    arrayOf(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.CALL_PHONE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    1
                )
            }

            if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this@First_logo,
                    arrayOf(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.CALL_PHONE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    1
                )
                start=1
                SHandler = SecondHandler()
                SHandler?.sendEmptyMessage(0)
            }
            FHandler?.sendEmptyMessageDelayed(0,1000) //0.01초
        }
    }

    inner class SecondHandler : Handler(){ //class안에 존재(함수화?), 원랜 class안에 변수와 함수
        override fun handleMessage(msg: Message?) { //handle치고 엔터누르면 바로 아랫줄까지 나옴
            super.handleMessage(msg)
            if(start==1) {
                time--
            }
            if(time == 0){
                var intent = Intent(this@First_logo, MapsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                FHandler?.removeMessages(0)
                SHandler?.removeMessages(0)
            }
            SHandler?.sendEmptyMessageDelayed(0,1000) //0.01초
        }
    }
}
