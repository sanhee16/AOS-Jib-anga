package com.example.user.timertutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class board_help : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_help)

        ///////////////////////////////내용 //////////////////////////////////////

        var txt_tip_title1 = findViewById<TextView>(R.id.txt_tip_title1)
        var txt_tip_con1 = findViewById<TextView>(R.id.txt_tip_con1)

        txt_tip_title1.text = "경찰청"
        txt_tip_con1.text =
                "전화번호: 112\n" +
                "홈페이지: https://minwon.police.go.kr/\n" +
                "접수내용: 범죄신고"

        var txt_tip_title2 = findViewById<TextView>(R.id.txt_tip_title2)
        var txt_tip_con2 = findViewById<TextView>(R.id.txt_tip_con2)

        txt_tip_title2.text = "여성긴급전화 1366"
        txt_tip_con2.text =
                "전화번호: 1366\n" +
                "홈페이지: https://www.women1366.kr\n" +
                "접수내용: 가정폭력, 성폭력, 성매매 긴급 전화상담 및 보호\n" +
                "관련기관: 한국여성인권진흥원"

        var txt_tip_title3 = findViewById<TextView>(R.id.txt_tip_title3)
        var txt_tip_con3 = findViewById<TextView>(R.id.txt_tip_con3)

        txt_tip_title3.text = "안전 Dream’ 아동·여성·장애인 경찰지원센터"
        txt_tip_con3.text = "전화번호: 117\n" +
                "홈페이지: http://www.safe182.go.kr\n" +
                "접수내용: 성폭력, 성매매, 학교, 가정폭력 상담·신고\n" +
                "관련기관: 아동·여성·장애인 경찰지원센터"

        var txt_tip_title4 = findViewById<TextView>(R.id.txt_tip_title4)
        var txt_tip_con4 = findViewById<TextView>(R.id.txt_tip_con4)

        txt_tip_title4.text = "한국 성폭력 상담소"
        txt_tip_con4.text =
                "홈페이지 : www.sisters.or.kr\n" +
                "내용: 성희롱 정의, 피해사례, 대응법, 법률 정보, 신문기사, 메일상담실 제공."

        var txt_tip_title5 = findViewById<TextView>(R.id.txt_tip_title5)
        var txt_tip_con5 = findViewById<TextView>(R.id.txt_tip_con5)

        txt_tip_title5.text = "성범죄 피해자 보호 지원 서비스"
        txt_tip_con5.text =
                "심리, 정서, 신체적으로 위기상태에 있는 성폭력 피해자 상담, 의료, 법률, 보호 등 서비스 제공을 통해 건강한 사회인으로 복귀하도록 지원\n" +
                "지원 대상: 성폭력 피해자 및 가족\n" +
                "지원 내용:\n" +
                "-성폭력 피해에 대한 상담(여성긴급전화 1366센터, 성폭력 상담소)\n" +
                "-성폭력 피해자 숙식제공 등 보호(성폭력 피해자 보호시설)\n" +
                "-성폭력 피해자 치료(응급키트, 의료비, 간병비 지원)\n" +
                "-성폭력 피해자 방문 상담 및 돌봄서비스 지원\n" +
                "-성폭력 피해자 피해회복 및 재발 방지\n" +
                "-성폭력 피해자 무료법률지원\n" +
                "신청방법: 여성긴급전화 문의 후 신청(1366)"

        var txt_tip_title6 = findViewById<TextView>(R.id.txt_tip_title6)
        var txt_tip_con6 = findViewById<TextView>(R.id.txt_tip_con6)

        txt_tip_title6.text = "해바라기센터(성폭력피해자통합지원센터)"
        txt_tip_con6.text =
                "전화번호: 1899-3075\n" +
                "홈페이지: http://www.mogef.go.kr/cs/wvs/cs_wvs_f005.do\n" +
                "성폭력·가정폭력·성매매 피해자 및 그 가족 대상 365일 24시간 상담지원, 의료지원, 법률·수사지원, 심리치료지원 등의 서비스를 통합적으로 제공 \n" +
                "피해자가 폭력피해로 인한 위기상황에 대처하고 2차 피해를 방지할 수 있도록 지원"

        var btn_board_tap1_sh = findViewById<Button>(R.id.btn_board_tap1_sh)
        var btn_board_tap2_sh = findViewById<Button>(R.id.btn_board_tap2_sh)
        var btn_board_tap3_sh = findViewById<Button>(R.id.btn_board_tap3_sh)

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

        btn_board_tap3_sh.setOnClickListener {
            var nextIntent = Intent(this, board_link::class.java)
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



