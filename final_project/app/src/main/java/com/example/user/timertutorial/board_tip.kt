package com.example.user.timertutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class board_tip : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_list)

        ///////////////////////////////내용 //////////////////////////////////////
        var txt_tip_title1 = findViewById<TextView>(R.id.txt_tip_title1)
        var txt_tip_con1 = findViewById<TextView>(R.id.txt_tip_con1)

        txt_tip_title1.text = "여성 안심 귀가 서비스"
        txt_tip_con1.text =
                "밤 10시부터 새벽 1시까지 서비스를 신청한 여성과 함께 주거지까지 동행해 주는 서비스. \n" +
                "지하철역이나 버스정류장 도착 30분 전에 120 다산콜센터로 전화해 신청하면 거주 자치구 구청 상황실로 바로 연결돼 신청자와 만날 2인 1조 스카우트 이름 정보를 확인하게 된다. \n" +
                "약속된 장소에서 만나 스카우트의 신분증을 확인한 뒤 집까지 함께 귀가한다.\n" +
                "(단, 서울시에서만 시행 중)\n"

        var txt_tip_title2 = findViewById<TextView>(R.id.txt_tip_title2)
        var txt_tip_con2 = findViewById<TextView>(R.id.txt_tip_con2)

        txt_tip_title2.text = "택시 안심 알리미"
        txt_tip_con2.text ="택시를 이용하기 전 ‘택시 안심 알리미 홈페이지’를 통해 회원가입 후 요금 지불용 카드 번호와 보호자의 연락처를 등록한다. \n" +
                "정보 등록이 끝나고 택시에 탑승해 기사에게 선승인 결제를 요청 후 티머니 결제 패드에 등록한 카드를 찍으면, 탑승자의 승·하차 정보 및 택시정보를 보호자의 휴대폰 문자로 받아 볼 수 있다. \n" +
                "스마트폰의 NFC(Near Field Communication) 기능과 애플리케이션을 이용해 간편하게 이용이 가능하다.\n"

        var txt_tip_title3 = findViewById<TextView>(R.id.txt_tip_title3)
        var txt_tip_con3 = findViewById<TextView>(R.id.txt_tip_con3)

        txt_tip_title3.text = "기타 스마트폰 앱"
        txt_tip_con3.text =  "스마트 안심귀가\n" +
                "다운로드 -  https://www.onestore.co.kr/userpoc/apps/view?pid=0000300136\n" +
                "이동 중인 나의 현재 위치가 보호자에게 전송된다. \n"

        var txt_tip_title4 = findViewById<TextView>(R.id.txt_tip_title4)
        var txt_tip_con4 = findViewById<TextView>(R.id.txt_tip_con4)

        txt_tip_title4.text = "여성 안심 귀가 서비스"
        txt_tip_con4.text =
                "밤 10시부터 새벽 1시까지 서비스를 신청한 여성과 함께 주거지까지 동행해 주는 서비스. \n" +
                "지하철역이나 버스정류장 도착 30분 전에 120 다산콜센터로 전화해 신청하면 거주 자치구 구청 상황실로 바로 연결돼 신청자와 만날 2인 1조 스카우트 이름 정보를 확인하게 된다. \n" +
                "" +
                "약속된 장소에서 만나 스카우트의 신분증을 확인한 뒤 집까지 함께 귀가한다.\n" +
                "(단, 서울시에서만 시행 중)\n"
        /////////////////////////////////////////////////////////////////////////////////////

        var btn_board_tap1_sh = findViewById<Button>(R.id.btn_board_tap1_sh)
        var btn_board_tap3_sh = findViewById<Button>(R.id.btn_board_tap3_sh)
        var btn_board_tap4_sh = findViewById<Button>(R.id.btn_board_tap4_sh)


        btn_board_tap1_sh.setOnClickListener {
            var nextIntent = Intent(this, board_notice::class.java)
            startActivity(nextIntent)
            overridePendingTransition(0, 0)
        }

        btn_board_tap3_sh.setOnClickListener {
            var nextIntent = Intent(this, board_link::class.java)
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
