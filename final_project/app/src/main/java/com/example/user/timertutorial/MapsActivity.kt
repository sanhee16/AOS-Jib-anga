package com.example.user.timertutorial

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.bojeong.*
import kotlinx.android.synthetic.main.guseong.*
import kotlinx.android.synthetic.main.policelocation.*
import kotlinx.android.synthetic.main.suji.*
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    override fun onMarkerClick(p0: Marker?)=false
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var currentLatLng: LatLng
    var next_dis = 0.0
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    val police1 = LatLng(37.3196, 127.1159)
    val police2 = LatLng(37.3249, 127.1019)
    val police3 = LatLng(37.3096, 127.1061)
    val police4 = LatLng(37.2946, 127.1168)

    override fun onCreate(savedInstanceState: Bundle?) {

        CheckAppFirstExecute2()

        startService(Intent(this, MainService::class.java))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var btn_dh8 = findViewById<ImageButton>(R.id.btn_dh8)
        btn_dh8.setOnClickListener {
            loadPlacePicker()
            overridePendingTransition(0, 0)
        }

        var btn_dh7 = findViewById<LinearLayout>(R.id.btn_dh7)
        btn_dh7.setOnClickListener {
            loadPlacePicker()
            overridePendingTransition(0, 0)
        }

        var imgbtnboard = findViewById<ImageButton>(R.id.imgbtn_board)
        var btnboard = findViewById<Button>(R.id.btn_board)

        var imgbtndanger = findViewById<ImageButton>(R.id.imgbtn_danger)
        var btndanger = findViewById<Button>(R.id.btn_danger)

        var imgbtnalarm = findViewById<ImageButton>(R.id.imgbtn_alarm)
        var btnalarm = findViewById<Button>(R.id.btn_alarm)

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

        imgbtnalarm.setOnClickListener {
            var alarmintent = Intent(this, Main_dh::class.java)
            alarmintent.putExtra("distance_map", next_dis) //idx보내기
            startActivity(alarmintent)
            overridePendingTransition(0, 0)
        }
        btnalarm.setOnClickListener {
            var alarmintent = Intent(this, Main_dh::class.java)
            startActivity(alarmintent)
            overridePendingTransition(0, 0)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var a = 0


        var imgbtn_police = findViewById<LinearLayout>(R.id.imgbtn_police)
        imgbtn_police.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            var dialogView = layoutInflater.inflate(R.layout.police,null)

            var pref = getSharedPreferences("pref",Context.MODE_PRIVATE)
            var editor = pref.edit()
            var sw = pref.getInt("key", 0)
            var dialogswitch = dialogView.findViewById<Switch>(R.id.sw)

            if(sw == 1){
                dialogswitch.setChecked(true)
            }
            else{
                dialogswitch.setChecked(false)
            }

            dialogswitch.setOnCheckedChangeListener {_,isChecked->
                if(isChecked){
                    a = 0
                }

                else {
                    a = 1
                }

            }

            builder.setView(dialogView)
                .setPositiveButton("확인"){dialogInterface, i ->
                    if(a == 0){
                        editor.putInt("key", 1)
                        editor.commit()
                        mMap.addMarker(MarkerOptions().position(police1).title("용인경찰서 보정지구대").snippet("주소 및 전화번호 보기"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.3097,127.1094)))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.3097,127.1094), 13.8f))
                        mMap.addMarker(MarkerOptions().position(police2).title("용인서부경찰서 수지지구대").snippet("주소 및 전화번호 보기"))
                        mMap.addMarker(MarkerOptions().position(police3).title("용인서부경찰서").snippet("주소 및 전화번호 보기"))
                        mMap.addMarker(MarkerOptions().position(police4).title("구성파출소").snippet("주소 및 전화번호 보기"))
                    }
                    else{
                        editor.putInt("key",  0)
                        editor.commit()
                        mMap.clear()
                    }
                }
                .setNegativeButton("취소"){dialogInterface, i ->

                }
                .show()
        }

        var btn_police = findViewById<ImageButton>(R.id.imgbtn_police1)
        btn_police.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            var dialogView = layoutInflater.inflate(R.layout.police,null)

            //var sw = pref.getInt("key", 0)

            var pref = getSharedPreferences("pref",Context.MODE_PRIVATE)
            var editor = pref.edit()
            var sw = pref.getInt("key", 0)
            var dialogswitch = dialogView.findViewById<Switch>(R.id.sw)

            if(sw == 1){
                dialogswitch.setChecked(true)
            }
            else{
                dialogswitch.setChecked(false)
            }

            dialogswitch.setOnCheckedChangeListener {_,isChecked->
                if(isChecked){
                    a = 0
                }

                else {
                    a = 1
                }

            }

            builder.setView(dialogView)
                .setPositiveButton("확인"){dialogInterface, i ->
                    if(a == 0){
                        editor.putInt("key", 1)
                        editor.commit()
                        mMap.addMarker(MarkerOptions().position(police1).title("용인경찰서 보정지구대").snippet("주소 및 전화번호 보기"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.3097,127.1094)))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.3097,127.1094), 13.8f))
                        mMap.addMarker(MarkerOptions().position(police2).title("용인서부경찰서 수지지구대").snippet("주소 및 전화번호 보기"))
                        mMap.addMarker(MarkerOptions().position(police3).title("용인서부경찰서").snippet("주소 및 전화번호 보기"))
                        mMap.addMarker(MarkerOptions().position(police4).title("구성파출소").snippet("주소 및 전화번호 보기"))
                    }
                    else{
                        editor.putInt("key",  0)
                        editor.commit()
                        mMap.clear()
                    }
                }
                .setNegativeButton("취소"){dialogInterface, i ->

                }
                .show()
        }


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
                //placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }

        createLocationRequest()
    }

    fun CheckAppFirstExecute2(): Boolean {
        val pref = getSharedPreferences("IsFirst", Activity.MODE_PRIVATE)
        val isFirst = pref.getBoolean("isFirst", false)

        if (!isFirst) { //최초 실행시 true 저장\

            var helper = DBsms(this)
            var db = helper.writableDatabase // 권한받기!! 꼭 해야함
            var sql = "insert into smsTable (smsData)" +
                    "values (?)"
            //쿼리문
            var arg = arrayOf("집에 도착하지 못했습니다")
            db.execSQL(sql,arg)
            db.close()

            add_notice()
            add_list()

            val editor = pref.edit()
            editor.putBoolean("isFirst", true)
            editor.commit()
            //Toast.makeText(applicationContext, "do MAP", Toast.LENGTH_LONG).show()
        }

        return !isFirst
    }

    fun add_notice(){

        var helper = DBboard(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        //쿼리문 만들기
        var sql = "insert into BoardTable(cateData, titleData , dateData , contentData)" +
                "values(?, ?, ?, ?)"

        var arg_notice1 = arrayOf(
            "공지", "1.5.0 업데이트", "2018.12.12",
            " \n - 버그수정 \n" + " 문자 전송 여러개 안되는 것 수정 \n" +
                    " 전화 발신 오류 수정 \n"

        )

        var arg_notice2 = arrayOf(
            "공지", "1.4.0 업데이트", "2018.12.7",
            "\n - 기능 업데이트 \n" + " 문자 문구 입력 추가"
        )

        var arg_notice3 = arrayOf(
            "공지", "1.1.7 업데이트", "2018.11.30",
            " \n - 버그수정 \n" + "  \n" +
                    " 전화 발신 오류 수정 \n" +

                    " \n - 기능 삭제 \n" +
                    "링크 기능 삭제\n" +
                    "테스트 버전 삭제 \n"
        )


        var arg_notice4 = arrayOf(
            "공지", "1.1.5 업데이트", "2018.11.18",

            " \n - 버그수정 \n" + "  \n" +
                    " 전화 발신 오류 수정 \n" +
                    "\n - 기능 수정 \n" +
                    "로고 수정 \n"+
                    " 타이머 기능 수정\n"
        )


        var arg_notice5 = arrayOf(
            "공지", "1.1.0 업데이트", "2018.11.5",

            " \n - 기능 추가 \n" +
                    " 서비스 추가 \n" +
                    " 내위치 기능 추가 \n"
        )

        var arg_notice6 = arrayOf(
            "공지", "1.0.0 업데이트", "2018.11.3",

            " \n - 기능 수정 \n" +
                    " 전화번호부 수정\n" +
                    " \n - 버그수정 \n" + " 링크 기능 수정\n" +
                    " \n - 기능 추가 \n" +
                    " 음량키 동작 추가 \n" +
                    " 전화 발신 추가 \n"
        )

        var arg_notice7 = arrayOf(
            "공지", "1.0.3 업데이트", "2018.11.1",
            " \n - 버그수정 \n" + " 문자 전송 여러개 안되는 것 수정 \n" +
                    " 전화 발신 오류 수정 \n"

        )

        var arg_notice8 = arrayOf(
            "공지", "9.2 업데이트", "2018.10.28",
            " \n - 버그수정 \n" + " 문자 전송 여러개 안되는 것 수정 \n" +
                    " 전화 발신 오류 수정 \n"

        )

        var arg_notice9 = arrayOf(
            "공지", "7.1 업데이트", "2018.10.27",
            " \n - 버그수정 \n" + " 문자 전송 여러개 안되는 것 수정 \n" +
                    " 전화 발신 오류 수정 \n"

        )

        var arg_notice10 = arrayOf(
            "공지", "6.0 업데이트", "2018.10.15",
            " \n - 버그수정 \n" + " 문자 전송 여러개 안되는 것 수정 \n" +
                    " 전화 발신 오류 수정 \n"

        )

        var arg_notice11 = arrayOf(
            "공지", "5.0 업데이트", "2018.10.8",
            " \n - 버그수정 \n" + " 문자 전송 여러개 안되는 것 수정 \n" +
                    " 전화 발신 오류 수정 \n"

        )

        var arg_notice12 = arrayOf(
            "공지", "4.0 업데이트", "2018.10.3",
            " \n - 버그수정 \n" + " 문자 전송 여러개 안되는 것 수정 \n" +
                    " 전화 발신 오류 수정 \n"

        )


        db.execSQL(sql,arg_notice1)
        db.execSQL(sql,arg_notice2)
        db.execSQL(sql,arg_notice3)
        db.execSQL(sql,arg_notice4)
        db.execSQL(sql,arg_notice5)
        db.execSQL(sql,arg_notice6)
        db.execSQL(sql,arg_notice7)
        db.execSQL(sql,arg_notice8)
        db.execSQL(sql,arg_notice9)
        db.execSQL(sql,arg_notice10)
        db.execSQL(sql,arg_notice11)
        db.execSQL(sql,arg_notice12)
        db.close()
    }
    fun add_list(){

        var helper = DBboard(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        //쿼리문 만들기
        var sql = "insert into BoardTable(cateData, titleData , dateData , contentData)" +
                "values(?, ?, ?, ?)"
        var arg_link1 = arrayOf(
            "링크", "서울 강남구, 여성 노리는 범죄 OUT!", "2018.12.12",
            "[시민일보=이대우 기자] 여성이 안전한 도시를 꿈꾸는 서울 강남구(구청장 정순균)는 다양한 사업을 통해 ‘여성안심도시’ 만들기에 박차를 가하고 있다.\n" +
                    " \n" +
                    "구는 최근 ▲ ‘가·나·다 여성안심길’ 사업을 완료했으며 ▲ 여성안심스카우트 ▲여성안심지킴이 등 여성안전을 위한 다양한 사업도 함께 진행중이다.\n" +
                    "\n" +
                    "특히 주민들로 구성된 자율방범대원들과 경찰관들이 어두운 골목길과 1인 여성 거주지 밀집 지역 등을 순찰하는 등 여성·주민들의 안전 귀가도 지원해 왔다.\n" +
                    "\n" +
                    "이에 <시민일보>는 구가 그동안 진행해 온 여성관련 사업에 대해 자세히 살펴봤다.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "■주민과 함께하는 ‘가·나·다 여성안심길’ 사업 완료\n" +
                    "\n" +
                    "구는 지난 8월부터 진행해온 ‘가·나·다 여성안심길’ 사업을 최근 완료했다.\n" +
                    "\n" +
                    "‘가·나·다 여성안심길’은 지역주민이 함께 ‘가꾸고’ 서로 ‘나누며’ 안심하고 ‘다니는’ 길이라는 뜻으로, 지역내 29개인 여성안심귀갓길 중 환경개선이 필요한 곳을 선정, 강남·수서 경찰서와 함께 주민주도 여성안심길을 조성했다.\n" +
                    "\n" +
                    "방죽마을어린이공원 앞 외 3곳에는 안심길을 알리는 홍보 로고젝터(LED 경관조명)를 설치하고, 다가구빌라 밀집구간인 도산대로54길 10~55 일대는 이면도로 노후 차선 도색과 낮에 태양열을 축적해 밤에 LED빛을 발광하는 바닥 조명 ‘쏠라표지병(태양광 바닥조명)’ 80개 설치로 거리환경을 개선했다.\n" +
                    "\n" +
                    "특히 주민 통행량이 많지만 시설이 낡고 어두운 도산대로54길에 태양광 보조조명과 논슬립 장치를 설치하고, 밝은 디자인의 벽화를 그려 야간 통행시 불편함 해소와 심리적 안정감을 주는 여성안심계단을 조성했다.\n" +
                    "\n" +
                    "\n" +
                    "■ 여성안심 행복마을 사업\n" +
                    "\n" +
                    "‘여성안심 행복마을 사업’은 여성 대상 강력범죄 예방을 위해 여성안심길 주변 다가구 밀집지역의 건물들에 특수 형광물질을 도포를 통한 ‘바른 주거지’ 제공과 주민 체감안전도 증진·주거침입·절도 등 범죄 발생시 증거 확보 등을 위해 실시되는 사업이다.\n" +
                    "\n" +
                    "건물에 도포되는 특수 형광물질은 눈에 보이지 않는 액체형태의 물질로 육안 식별은 곤란하고, 자외선 활용 특수장비에서만 형광색 현출되며, 가스배관·방범창 등 도포시 6개월 이상 전이성이 유지된다.\n" +
                    "\n" +
                    "\n" +
                    " 또 ▲신체·의류·신발 등 접촉 후 잘 지워지지 않아 지문 판독·족적 확인 등 활용 가능하며, 범죄 예방 뿐만 아니라 검거 효과에도 도움이 된다.\n" +
                    "\n" +
                    "구는 역삼1동, 논현1·2동, 청담동내 침입절도 발생 우려가 있는 취약건물 628곳에 특수형광물질을 도포했다.\n" +
                    "\n" +
                    "이밖에도 여성안심길 37곳의 112 신고위치 표지판을 정비하고, 5곳 노면에 안심길을 표시했다. 비상벨이 멀리서도 잘 보이도록 비상벨 안내판을 2곳에 설치했다.\n" +
                    "\n" +
                    "이선형 구 보육지원과장은 “함께 참여하고 성장하는 성평등 사회 실현을 위해 여성친화도시를 만들고 여성안전 인프라를 구축하겠다”면서 “‘기분 좋은 변화’를 통해 여성과 아이들이 행복할 수 있는 ‘품격 있는 강남’을 만드는 데 주력하겠다”고 말했다.\n" +
                    "\n" +
                    "\n" +
                    "■ 여성안심귀가스카우트\n" +
                    "\n" +
                    "구는 늦은 밤 귀가하는 여성·청소년의 안전한 귀가 지원을 위해 ‘여성안심귀가스카우트’를 운영 중이다.\n" +
                    "\n" +
                    "‘여성안심귀가스카우트’는 총 11명(남자1명, 여자 10명)이 활동하며, 주 5일 총 14시간 운영된다.\n" +
                    "월요일은 오후 10시 2시간, 화∼금요일은 오후 10시 3시간 운영된다.\n" +
                    "\n" +
                    "활동지역은 논현1동, 대치4동, 역삼1·2동, 일원1동이며, 구청 종합상황실 또는 120으로 신청하면 된다.\n" +
                    "\n" +
                    "활동지역 외에는 112로 전화해 ‘경찰 안심 귀가 서비스’를 신청·이용하면 된다.\n" +
                    "\n" +
                    "\n" +
                    "■ 몰카촬영·택배범죄 예방도\n" +
                    "\n" +
                    "구는 여성을 대상으로 한 범죄 예방을 위해 ▲여성안심보안관과 ▲여성안심택배 ▲여성안심지킴이집도 운영하고 있다.\n" +
                    "\n" +
                    "‘여성안심보안관’은 공공기관·민간개방 화장실 점검을 통해 최근 급증하는 몰래 카메라 촬영 범죄로부터 여성 불안감 해소·범죄 예방을 위해 실시하는 것으로, 주 3일 오전 10시~오후 5시 2인1조로 운영된다.\n" +
                    "\n" +
                    "‘여성안심택배’는 여성 1인 가구나 맞벌이가구 등 택배수령이 어려운 주민들이 낯선 사람을 대면하지 않고 택배 수령을 가능하게 함으로써 안심하고 택배 이용을 가능하게 하는 무인택배서비스로, 지역내 설치된 무인택배함에서 365일 24시간 언제나 수령이 가능하다.\n" +
                    "\n" +
                    "이용요금은 무료이며, 물품보관시간이 48시간 초과될 경우 하루에 1000원 과금된다.\n" +
                    "\n" +
                    "‘여성안심지킴이집’은 여성이 위급한 상황에서 긴급 대피하고 안전하게 보호받을 수 있도록 24시간 편의점을 활용한 사업으로, 지역내 총 146개 편의점이 ‘여성안심지킴이집’으로 등록돼 있다.\n" +
                    " \n" +
                    "특정 위기상황시 지정된 지킴이집으로 긴급대피를 하면, 안심지킴이의 112 핫라인 신고시스템 (비상벨 설치)을 통해 경찰에 즉시 신고가 가능하다. 또 안전귀가 지원서비스도 함께 제공된다."
        )
        var arg_link2 = arrayOf(
            "링크", "두려움에 떠는 ‘나홀로 여성들'","2018.12.9",
            "[천지일보=임혜지 기자] “혼자 사니까 무섭고 불안한 부분이 한 두 개가 아니에요. 한 번은 정체모를 남자가 밤 12시가 넘었는데 초인종을 미친 듯이 누르다 가더라고요. 그때만 생각하면 아직도 심장이 두근거려요.”\n" +
                    "\n" +
                    "경기도 의정부에 살고 있는 유은선(23, 여)씨는 과거 이 같은 기억을 떠올리며 몸서리쳤다. 혼자 산지 ‘1년차’, 유씨는 혼자 산다는 말이 동네에 돌면 괜한 해코지를 당할까 투명 인간처럼 지내고 있다고 했다. 24시간 암막커튼을 치고 배달음식도 일절 시켜먹지 않는다.\n" +
                    "\n" +
                    "그는 “사람들을 피하게 되고 집 근처에선 ‘좀 심하다’ 싶을 정도로 주위를 살피게 된다”며 “1년이 지났지만 불안감은 줄지 않고 매일 커지는 것 같다”고 말했다.\n" +
                    "\n" +
                    "이렇듯 가장 안전한 공간이 돼야할 ‘집’마저도 혼자 사는 여성들에게 불안한 공간이 되고 있다. ‘여성이 혼자 산다’는 이유만으로 언제 어디서 범죄의 대상이 될지 매순간 불안함을 느끼기 때문이다. 혼자 사는 여성들에 대한 범죄 예방 정책도 시행 중이나, 여성들이 체감하고 있는 공포를 해결하기엔 역부족이라는 지적도 나온다.\n" +
                    "\n" +
                    "혼자 사는 여성의 수는 매년 증가하고 있는 추세다. 통계청과 여성가족부에 따르면 올해 여성 1인 가구 수는 전체 1인 가구 수의 절반에 가까운 284만 3000가구(49.5%)였다. 지난 2005년 175만 3000명에 불과하던 혼자 사는 여성의 숫자는 2015년 261만명, 2016년 276만 6000명까지 증가했다. 혼자 사는 여성의 숫자는 매년 증가해 오는 2025년에는 혼자 사는 여성이 323만 4000명까지 늘어날 것으로 통계청은 전망했다.\n" +
                    "\n" +
                    "문제는 많은 여성들이 범죄에 대한 두려움을 안고 살아가고 있다는 것이다. 통계청 조사 결과를 보면 지난 2016년 기준 여성의 50.9%는 사회 안전에 대해 불안하다고 느끼는 것으로 나타났다.\n" +
                    "\n" +
                    "\n" +
                    "연령별로는 20대가 62.8%로 가장 많은 불안함을 느끼고 있었다. 그 뒤를 이어 30대가 59.7%로 많은 불안함을 느끼고 있었다. 13~19세 여성은 43.2%로 상대적으로 불안함을 덜 느끼는 것으로 조사됐다. 주된 불안 요인으로는 범죄 발생이 37.3%로 가장 높았다.\n" +
                    "\n" +
                    "특히 20대~30대 혼자 사는 여성들이 느끼는 불안감은 클 수밖에 없다. 때문에 이들은 남성용 운동화, 구두를 신발장에 두는 등 각종 범죄로부터 자신을 지키기 위한 저마다의 방법을 찾아 하루하루를 살아가고 있다. 심지어는 집안 가족 중 남성의 목소리를 녹음해 틀어놓기도 한다.\n" +
                    "\n" +
                    "온라인 커뮤니티 등에도 ‘혼자 살기 시작했는데 안전을 위해 반드시 갖춰야 할 것은 무엇이냐’는 여성들의 게시글을 쉽게 찾을 수 있다.\n" +
                    "\n" +
                    "하지만 이마저도 한계에 부딪힌다고 여성들은 말한다. 한 여성은 “남자 배달원들이 잠금장치 여러 개 달아놓는 집을 ‘여자 혼자 사는 집’이라고 보고 따로 관리한다는 얘길 들었다”며 “아무리 남자 신발이나 물건들을 갖다놔봤자 소용없다”고 토로하기도 했다.\n" +
                    "\n" +
                    "서울시는 이러한 여성 대상 범죄를 예방하고자 지난 2014년부터 여성 대상 범죄 취약지에 있는 편의점 1,000여 개소를 여성 안심 지킴이 집으로 지정, 이를 통해 위협을 느낀 여성이 대피해왔을 때 매장 직원이 핫라인으로 연결된 경찰에 출동을 요청할 수 있도록 하고 있다.\n" +
                    "\n" +
                    "또 여성을 버스정류장이나 지하철역에서 집 앞까지 동행하는 여성 안심 귀가스카우트도 운영 중이다.\n" +
                    "\n" +
                    "경찰은 특정 지역을 ‘여성 안심 귀갓길’로 지정해 폐쇄회로(CC)TV, 비상벨을 설치하거나 순찰을 강화하는 대책을 시행하고 있다.\n" +
                    "\n" +
                    "하지만 이는 모두 집 밖의 이야기일 뿐, 여성들이 집 안에서 느껴지는 막연한 공포나 두려움에 대해서까지 정책이 스며들지 못하고 있다는 지적이 나온다.\n" +
                    "\n" +
                    "전문가는 우선 ‘혼자 사는 여성 범죄’에 대해 심각성을 인지하고 이에 맞는 범죄 예방 대책을 세워야 한다고 강조했다.\n" +
                    "\n" +
                    "공정식 경기대 범죄심리학 교수는 “사회적으로 취약한 혼자 사는 여성이 범죄의 대상이 되는 경우가 많다”며 “이러한 1인 가구 여성이 살고 있는 곳엔 CCTV 설치를 대폭 늘리는 등 치안을 더욱 강화하는 대책을 세워야 한다”고 말했다. \n" +
                    "\n" +
                    "출처 : 천지일보(http://www.newscj.com)"
        )
        var arg_link3 = arrayOf(
            "링크", "갈피 못 잡는 범죄자 얼굴 공개",  "2018.12.5",
            "강력범죄가 잇따라 발생하면서 피의자의 신원 공개 여부가 뜨거운 논쟁거리가 되고 있다. 비슷한 범죄를 저질러도 상황에 따라 공개 기준이 제각각이라 더욱 혼란스럽다. 또한, 피의자가 신원 공개를 요청해도 거부당하고, 여론에 밀려 억지로 공개하는 경우도 종종 일어난다. \n" +
                    "\n" +
                    "전 세계에서 피의자에게 모자나 마스크를 씌우는 나라는 우리나라밖에 없다. 심지어 피의자 얼굴은 모자이크까지 하며 가려주면서 경찰이나 수사 관계자 등 얼굴이 공개되는 아이러니한 상황도 발생한다. 강력범죄 피의자 신상 공개, 어떻게 해야 할까? \n" +
                    "\n" +
                    "■ 같은 듯 다른 강남역 화장실 살인 사건과 강서구 PC방 살인 사건 \n" +
                    "\n" +
                    "2016년 5월 강남역 부근 공용 화장실에서 모르는 여성을 살해한 김모씨는 신원이 공개되지 않았다. 그러나 올해 10월 강서구 PC방에서 알바생을 살해한 김성수는 얼굴이 공개됐다. \n" +
                    "\n" +
                    "두 사건은 비슷하지만 강남역 살인사건 김모씨의 신원이 비공개된 이유는 조현병에 의한 심신미약이 인정됐기 때문이다. 계획적인 ‘성혐오 범죄’가 아닌 우발적인 ‘묻지마 범죄’에 가깝다는 판단을 내린 것이다. 그러나 김성수는 우울증 치료 전력이 있지만 분노 충동조절 실패로 보고 경찰이 신원 공개 조건을 충족했다고 판단해서 공개했다. 비슷한 사건에 신원 공개 여부가 달라지자 국민들의 공분을 샀고, 파장이 만만치 않았다. \n" +
                    "\n" +
                    "이외에도 강력범죄에 대한 피의자의 신상 공개는 계속 엇갈리고 있다. 서울대공원 토막 살인사건 피의자 변경석은 실명과 얼굴 공개됐으나 폐지를 줍던 50대 여성을 살해한 거제도 살인사건 피의자는 살인죄가 아닌 상해치사가 적용돼 얼굴이 비공개되며 논란을 가중시켰다. \n" +
                    "\n" +
                    "■ 2010년 특정강력범죄 처벌 특례법 개정.. 신원 공개 기준은? \n" +
                    "\n" +
                    "강력범죄가 발생하면 피의자 신상 공개를 두고 갑론을박이 벌어진다. 신상 공개 기준은 어떻게 결정되는 것일까? \n" +
                    "\n" +
                    "지난 2010년 4월 ‘연쇄 살인마’ 강호순 사건을 계기로 특정강력범죄의 처벌에 관한 특례법이 개정되면서 강력범죄 피의자의 신상 공개가 선별적으로 허용됐다. \n" +
                    "\n" +
                    "특정강력범죄의 처벌에 관한 특례법 제8조 2항에 따르면 ▲범행 수단이 잔인하고 중대한 피해가 발생한 특정강력범죄 사건 ▲피의자가 그 죄를 범한 충분한 증거가 있는 경우 ▲국민 알 권리 보장, 피의자 재범방지 등 공공의 이익을 위할 것 ▲피의자가 청소년(만 19세 미만)이 아닌 경우, 피의자의 신상을 공개할 수 있다. \n" +
                    "\n" +
                    "경찰은 지난 2016년 6월부터 40개의 세부 기준을 따져 신원 공개를 결정하고 있다. 신상 공개 심의위원회는 경찰 위원 3명, 변호사, 의사, 교수 등 외부 전문가 4명으로 구성되어 있다. \n" +
                    "\n" +
                    "그러나 공개 기준이 모호하다는 지적이 계속되고 있다. 상황에 따라 혹은 여론에 밀려 공개·비공개 여부가 결정되는 일이 빈번하게 발생되고 있기 때문이다. 결국 피의자의 신원 공개 여부는 수사당국의 재량에 달려 있는 셈이다. 피의자의 인권과 국민의 알 권리를 비교하고 고민해 검찰·경찰이 스스로 판단하는 것이다. \n" +
                    "\n" +
                    "■ 잔인해도 미성년자는 제외·피의자가 원해도 신원 공개 불가능 \n" +
                    "\n" +
                    "지난해 인천에서 초등생을 잔인하게 살해하고 신체를 훼손한 뒤 유기한 범인들은 사회적인 충격이 컸지만 신상이 공개되지 않았다. 경찰 수사 당시 피의자의 나이가 각각 만 17세, 18세로 미성년자였기 때문이다. 현행법상 ‘청소년(만 19세 미만)’은 얼굴, 성명, 나이 등 신상정보를 공개할 수 없다. \n" +
                    "\n" +
                    "지난 2016년 어버이날 친부를 무참히 살해한 40대 남매는 신상 공개를 원했지만 경찰이 마스크와 모자를 제공해 얼굴을 가렸다. 이동하던 남매가 마스크를 벗고 \"얼굴 가리지 않겠다\", \"신상을 공개해도 괜찮다\"고 완강하게 나와 얼굴이 그대로 노출됐지만 경찰이 취재진에게 얼굴 모자이크 처리를 부탁하기도 했다. \n" +
                    "\n" +
                    "해외 사례를 살펴보면, 미국, 일본, 유럽은 범죄자의 인권보다 범죄 재발 방지와 국민의 알 권리를 더욱 중요하게 판단해 신상을 공개하는 것으로 알려졌다. \n" +
                    "\n" +
                    "■ “피해자 인권보호” vs “범죄 예방 효과 없어”.. 당신의 선택은? \n" +
                    "\n" +
                    "강력범죄 피의자의 신상 공개를 찬성하는 사람들은 범죄자의 인권보호보다 피해자의 인권보호가 더 중요하다고 주장한다. 강간, 살인, 사체 훼손·유기 등 범행 수법이 갈수록 잔혹해지는 상황에서 신원 공개는 꼭 필요하다고 판단한 것이다. \n" +
                    "\n" +
                    "또한, 신원을 공개하면 목격자와 제보 확보 등 수사에 도움을 줄 수도 있고, 엉뚱한 사람이 범죄자로 오해를 받는 피해를 막을 수 있다. 끝으로 국민의 알 권리 보장 및 강력범죄에 대한 사회적 경각심 고취라는 공익 목적을 달성할 수 있다. \n" +
                    "\n" +
                    "반면 반대 측은 판결 전에 신상 공개를 하면 무죄 추정의 원칙에 위배되고, 공정한 재판을 받을 수 없다고 주장한다. 또한, 국민의 관심이 최고조에 달한 시점에서 피의자 신상이 공개되면 피의자 가족이나 친구 등 주변인들의 신상정보까지 노출되기 때문에 2차 피해의 우려가 크다고 봤다. 덧붙여 신상 공개로 얻는 범죄 예방의 효과 등 실익이 없다고 판단했다. \n" +
                    "\n" +
                    "강력범죄 피의자의 신상 공개에 대한 설문조사는 찬성 쪽이 압도적으로 우세했다. 2016년 여론조사 전문기관 리얼미터 조사에 따르면 응답자 87.4%가 ‘강력범죄 피의자 신상 공개에 찬성한다’라고 응답했다. 시간이 지나도 상황은 별반 다르지 않았다. 리얼미터는 지난달 CBS 의뢰로 출소가 2년 남은 조두순의 얼굴 공개 여부를 조사했다. 조사 결과, ‘또 다른 추가 범죄 가능성을 막기 위해 공개해야 한다’는 의견이 91.6%로 집계됐다. \n" +
                    "\n" +
                    "피의자에 대한 신상 공개가 오락가락하고 형평성에 어긋난다는 주장이 제기되는 상황이다. 이에 전문가들은 신상 공개를 자의적인 판단에 맡기지 말고 구체적인 가이드라인을 만들어야 한다고 지적했다. 또한, 별개의 외부 기구를 만들 필요성도 언급했다. \n" +
                    "\n" +
                    "한편, 조경태 자유한국당 의원은 지난 2016년 7월에 ‘특정강력범죄 처벌에 관한 특례법’ 개정안을 발의했다. 현재 \"공개할 수 있다\"로 돼 있는 문구를 \"공개하여야 한다\"로 바꾸는 내용이다. 하지만 이 법안은 국회서 논의되지 못하고 여전히 표류 중이다. \n" +
                    "\n" +
                    "hyuk7179@fnnews.com 이혁 기자 "
        )
        var arg_link4 = arrayOf(
            "링크", "용인시, 범죄예방 위해 140여 편의점에 ‘풋SOS 비상벨’설치",  "2018.12.4",
            "용인시는 24시간 편의점의 절도‧강도 등 범죄를 예방하기 위해 발로 누르면 관할 경찰서로 자동 신고되는 ‘풋SOS비상벨’을 140여곳에 설치했다고 11일 밝혔다. \n" +
                    "\n" +
                    "'풋SOS비상벨'은 편의점 카운터 아래 설치돼 3초만 누르면 관할 경찰서 상황실로 자동신고가 돼 가까운 지구대에서 긴급 출동하게 된다. 경기도가 기기 및 설치비의 일부를 지원한 것으로 올해 처음 도입됐다. \n" +
                    "\n" +
                    "설치지역은 관내 원룸‧빌라단지‧학교‧어린이집 주변 등 여성안심귀갓길 인근 편의점으로 처인구 48곳, 기흥구 42곳, 수지구 50곳으로 시 관계자는 “내년에도 100여곳의 편의점이나 여성 1인 사업장에 풋SOS비상벨을 꾸준히 설치해 안전한 도시를 만들어 나갈 계획”이라고 말했다. "
        )
        var arg_link5 = arrayOf(
            "링크", "최근 3년 간 '주거침입성범죄' 1000건 육박…매일 1건 꼴",  "2018.12.1",

            "(서울=뉴스1) 민선희 기자 = 정당한 이유 없이 남의 집에 침입해 성범죄를 저지르는 '주거침입성범죄'가 최근 3년 간 1000건에 육박하는 것으로 드러났다. \n" +
                    "\n" +
                    "9일 국회 행정안전위원회 소속 소병훈 더불어민주당 의원은 지난 2015년부터 2017년까지 경찰청 범죄통계를 분석한 결과 최근 3년 간 981건의 주거침입성범죄가 발생했다고 밝혔다. \n" +
                    "\n" +
                    "주거침입성범죄는 성폭력범죄의 처벌 등에 관한 특례법에 의거, 무기징역 또는 5년 이상의 징역에 해당하는 중범죄다.\n" +
                    "\n" +
                    "경찰청 범죄통계상 주거침입성범죄는 ‘주거침입강간’, ‘주거침입유사강간’, ‘주거침입강제추행’, ‘주거침입강간등’의 4개 유형으로 분류되는데, 유형 중에서는 주거침입강제추행이 483건으로 가장 높은 비율(49.2%)을 차지했다. 주거침입강간(335건, 34.1%), 주거침입강간등(118건, 12.0%), 주거침입유사강간(45건, 4.6%)이 그 뒤를 이었다.\n" +
                    "\n" +
                    "주거침입성범죄가 가장 많이 발생한 지역은 경기(201건)와 서울(178건)로, 두 지역이 전체의 38.6%를 차지했다. 한편 광주와 충남은 2015년 이후 매년 발생건수가 증가하고 있어 관심이 필요하다는 지적이 제기됐다.\n" +
                    "\n" +
                    "소 의원은 \"최근 셉테드(CPTED, 범죄예방환경설계) 등 귀갓길, 감시사각지대의 안전을 확보하기 위한 방안은 다각도로 논의되고 있는 반면 집에 침입해 발생하는 성범죄에 대한 관심과 대책은 상대적으로 부족했다\"며 \"주거침입성범죄는 피해자에게 극도의 불안과 트라우마로 남게 될 뿐만 아니라 성범죄 이후 더 큰 강력범죄로 이어질 가능성이 높은 만큼 특단의 예방대책을 마련해야 한다\"고 강조했다.\n"
        )
        var arg_link6 = arrayOf(
            "링크", "영등포구, 범죄예방 빅데이터 플랫폼 구축", "2018.11.26",
            "[아시아경제 박종일 기자] 1인 여성가구수가 전체가구의 20%(3만2481가구)나 차지하는 영등포구가 빅데이터를 활용해 더욱 스마트한 여성범죄 예방정책 마련에 나섰다.\n" +
                    "\n" +
                    "영등포구(구청장 채현일)는 여성이 더욱 안전하고 살기 좋은 도시를 만들기 위해 전국 최초로 ‘여성안심 빅데이터 CPTED 협업플랫폼’을 구축했다.\n" +
                    "\n" +
                    "셉테드(CPTED)는 범죄예방을 목적으로 도시환경을 재설계, 삶의 질을 향상시키는 종합적인 범죄예방 전략을 말한다.\n" +
                    "\n" +
                    "그동안 구는 △스파이더 범죄예방마을 조성 △긴급출동 비상벨 △CCTV 관제센터 24시간 운영 △여성안심귀갓길 조성 △여성안심 택배함 △여성안심벨 △여성안심 지킴이집 △여성안심 스카우트 △자율방범대 등 여성 안전을 위한 다양한 정책을 펼치며 범죄예방에 노력해왔다.\n" +
                    "\n" +
                    "이런 노력에도 지속적으로 증가하는 여성 범죄의 불안감을 해소하기 위해 보다 과학적인 접근이 필요했고 이에 구는 과학적인 빅데이터 기반의 CPTED 플랫폼을 구축하게 됐다.\n" +
                    "\n" +
                    "4차 산업혁명의 핵심 기술인 빅데이터 분석을 더해 한층 업그레이드된 여성범죄 예방 정책을 만들 수 있게 된 것이다.\n" +
                    "\n" +
                    "‘여성안심 빅데이터 CPTED 협업플랫폼’은 영등포경찰서, KT 등으로부터 △범죄 데이터 △야간 여성 유동인구 데이터 △여성 1인 가구 데이터 △여성안심 스카우트 경로 데이터 △여성안심 시설물 정보 △기존 정책데이터 등 다양한 데이터를 수집?활용한다.\n" +
                    "원본보기\n" +
                    "\n" +
                    "\n" +
                    "이를 입체적으로 분석(머신러닝기법)해 주요 관리지역 및 안전지역을 도출하고 보다 세밀한 범죄 예방 정책 및 구민체감 정책을 수립한다.\n" +
                    "\n" +
                    "예를 들면 여성의 이동 패턴 및 범죄 이력을 기반으로 구청에서 운영하는 △여성안심귀갓길 최적화 △여성안심 택배함 최적지 도출 △여성안심 지킴이집 최적지 △범죄예방 순찰경로 최적화 △CCTV 최적화 등 보다 섬세한 주민밀착형 행정서비스가 가능해진다.\n" +
                    "\n" +
                    "아울러 침입범죄 다발지인 여성 1인 가구 중심으로 주요 대상지를 선정해 중점 관리하고 ‘IoT기반 문열림 센서’ 를 보급해(영등포경찰서 시범적용) 안전성을 높이는 정책을 확대 추진할 계획이다.\n" +
                    "\n" +
                    "과학적 행정을 위한 빅데이터 분석은 이제 영등포 주요 정책 추진의 원동력으로 자리잡고 있다. 빅데이터 CPTED 플랫폼 개발을 통한 실질적인 범죄예방 정책으로 불필요한 인력, 예산 낭비를 막고 관계기관 협업으로 지역사회 안전문제를 해결하는 등 다른 지자체로도 확산 가능할 것으로 기대하고 있다.\n" +
                    "\n" +
                    "구는 지난 22일 영등포경찰서와의 업무협의 간담회에서 구민 생활 안전을 위한 협력을 강화할 것을 약속하고 여성범죄를 획기적으로 예방해 나가기로 했다.\n" +
                    "\n" +
                    "채현일 영등포구청장은 “4차 산업혁명 시대에 맞는 과학행정으로 주민밀착형 치안을 강화해 나가겠다”며 “여성범죄 예방에서 나아가 영등포 주민 모두가 만족할 수 있는 안전도시 만들기에 박차를 가하겠다”고 전했다.\n\n"
        )
        var arg_link7 = arrayOf(
            "링크", "여성안심귀갓길..범죄 예방 효과 '톡톡'", "2018.11.18",
            "[앵커]\n" +
                    "속초 경찰이 지난해 하반기부터 우범지역에 경찰 로고를 비추고, 순찰을 강화하는 '여성안심귀갓길' 제도를 운영하고 있는데요.\n" +
                    "\n" +
                    "여성을 대상으로 하는 범죄 발생율이 크게 줄어든 것으로 나타나면서, 다른 지역의 벤치마킹이 잇따르고 있습니다. \n" +
                    "조기현 기자가 보도합니다. \n" +
                    "\n" +
                    "[리포터]\n" +
                    "고등학교 3학년에 올라가는 이선 양은 평소 학교를 마치고 학원에 갈 때마다 걱정이 많았습니다.\n" +
                    "\n" +
                    "학교에서 학원으로 가는 길이 인적이 드물고 어둡기 때문입니다.\n" +
                    "\n" +
                    "하지만, 요즘엔 걱정을 덜었습니다.\n" +
                    "\n" +
                    "하교 시간에 맞춰, 경찰들이 순찰차량을 이용해 골목길을 수시로 순찰하고 있기 때문입니다.\n" +
                    "\n" +
                    "[인터뷰]\n" +
                    "\"아무래도 학교가 끝나면 어두워서 하교를 하거나 학원에 갈 때 무서웠었는데, 이게 설치되고 나서 경찰 분들이 옆에 있다는 생각에 안심이 되기도 했습니다\"\n" +
                    "\n" +
                    "속초경찰서가 전국 처음으로 시행한 여성안심귀갓길 제도가 큰 효과를 거두고 있습니다.\n" +
                    "\n" +
                    "지난해 8월부터 여성 범죄가 많이 발생하는 지역을 중심으로 LED 조명을 바닥에 비추는 로고젝터를 설치하고, 순찰을 강화하기 시작했습니다.\n" +
                    "\n" +
                    "그 결과, 제도 시행 반년이 지난 현재, 전년 같은 기간에 비해, 성폭력 범죄는 66.7%, 절도는 35.3%가 감소했습니다.\n" +
                    "\n" +
                    "이 같은 사실이 알려지면서, 정선군은 물론, 대구 수성구와 서울 도봉구에서도 여성안심귀갓길 제도를 도입했습니다.\n" +
                    "\n" +
                    "[인터뷰]\n" +
                    "\"3대를 설치했는데 지역 주민들의 반응이 매우 좋았고, 범죄 예방효과가 큰 것으로 나타났습니다. 속초시와 시의회의 적극적인 지원을 받아 20개소에 추가 설치할 계획입니다\"\n" +
                    "\n" +
                    "시민 중심의 경찰 행정이 범죄를 줄이고, 치안 만족도를 높이는 효과가 입증된 셈입니다." +
                    "\n\n"
        )

        db.execSQL(sql,arg_link1)
        db.execSQL(sql,arg_link2)
        db.execSQL(sql,arg_link3)
        db.execSQL(sql,arg_link4)
        db.execSQL(sql,arg_link5)
        db.execSQL(sql,arg_link6)
        db.execSQL(sql,arg_link7)
        db.close()

    }


    override fun onInfoWindowClick(marker: Marker) {

        if(marker.position.latitude == 37.3196){ //police1, 용인경찰서 보정지구대

            val builder_location = AlertDialog.Builder(this)
            val policelocation = layoutInflater.inflate(R.layout.bojeong,null)
            val dg = builder_location.setView(policelocation).create()
            dg.show()

            var dialogcancel = policelocation.findViewById<ImageButton>(R.id.btn_cancel)
            var dialogcall = policelocation.findViewById<ImageButton>(R.id.btn_call)


            var display1 = getWindowManager().defaultDisplay
            var size1 = Point()
            display1.getSize(size1)

            var window1 = dg.getWindow()

            var x = size1.x * 0.75f
            var y = size1.y * 0.25f

            window1.setLayout(x.toInt(),y.toInt())


            dialogcall.setOnClickListener {

                var tel = dg.phonenumber1.text.toString()
                var intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:"+tel))
                try {
                    this.startActivity(intent)
                }catch (e : Exception) {
                    e.printStackTrace()
                }

            }
            dialogcancel.setOnClickListener {
                dg.dismiss()
            }
        }
        else if(marker.position.latitude == 37.3249){ //police2, 수지지구대

            val builder_location = AlertDialog.Builder(this)
            val policelocation = layoutInflater.inflate(R.layout.suji,null)
            val dg = builder_location.setView(policelocation).create()
            dg.show()

            var dialogcancel = policelocation.findViewById<ImageButton>(R.id.btn_cancel)
            var dialogcall = policelocation.findViewById<ImageButton>(R.id.btn_call)
            dg.show()

            var display = getWindowManager().defaultDisplay
            var size = Point()
            display.getSize(size)

            var window = dg.getWindow()

            var x = size.x * 0.78f
            var y = size.y * 0.30f

            window.setLayout(x.toInt(),y.toInt())

            dialogcall.setOnClickListener {

                var tel = dg.phonenumber4.text.toString()
                var intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:"+tel))
                try {
                    this.startActivity(intent)
                }catch (e : Exception) {
                    e.printStackTrace()
                }

            }
            dialogcancel.setOnClickListener {
                dg.dismiss()
            }
        }
        else if(marker.position.latitude == 37.3096){ //police3, 용인서부경찰서
            val builder_location = AlertDialog.Builder(this)
            val policelocation = layoutInflater.inflate(R.layout.policelocation,null)
            val dg = builder_location.setView(policelocation).create()
            dg.show()

            var display = getWindowManager().defaultDisplay
            var size = Point()
            display.getSize(size)

            var window = dg.getWindow()

            var x = size.x * 0.78f
            var y = size.y * 0.25f

            window.setLayout(x.toInt(),y.toInt())

            var dialogcancel = policelocation.findViewById<ImageButton>(R.id.btn_cancel)
            var dialogcall = policelocation.findViewById<ImageButton>(R.id.btn_call)
            dg.show()
            dialogcall.setOnClickListener {

                var tel = dg.phonenumber3.text.toString()
                var intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:"+tel))
                try {
                    this.startActivity(intent)
                }catch (e : Exception) {
                    e.printStackTrace()
                }

            }
            dialogcancel.setOnClickListener {
                dg.dismiss()
            }
        }
        else if(marker.position.latitude == 37.2946){ //police4, 구성파출소
            val builder_location = AlertDialog.Builder(this)
            val policelocation = layoutInflater.inflate(R.layout.guseong,null)
            val dg = builder_location.setView(policelocation).create()
            dg.show()

            var display = getWindowManager().defaultDisplay
            var size = Point()
            display.getSize(size)

            var window = dg.getWindow()

            var x = size.x * 0.75f
            var y = size.y * 0.25f

            window.setLayout(x.toInt(),y.toInt())

            var dialogcancel = policelocation.findViewById<ImageButton>(R.id.btn_cancel)
            var dialogcall = policelocation.findViewById<ImageButton>(R.id.btn_call)
            dg.show()
            dialogcall.setOnClickListener {

                var tel = dg.phonenumber2.text.toString()
                var intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:"+tel))
                try {
                    this.startActivity(intent)
                }catch (e : Exception) {
                    e.printStackTrace()
                }

            }
            dialogcancel.setOnClickListener {
                dg.dismiss()
            }
        }
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {

        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(this@MapsActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                var addressText = place.name.toString()
                addressText += "\n" + place.address.toString()
                mMap.clear()

                placeMarkerOnMap(place.latLng)
                placeMarkerOnMap(currentLatLng)

                mMap.addPolyline(PolylineOptions().add(currentLatLng).add(place.latLng).width(8f).color(Color.RED))
                var distance1 = SphericalUtil.computeDistanceBetween(currentLatLng, place.latLng)
                //Toast.makeText(applicationContext, "거리 : "+distance1+"m", Toast.LENGTH_LONG).show()
                next_dis = distance1
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        //구글 맵 객체를 불러온다.
        mMap = googleMap
        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        //mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true

        mMap.setOnMarkerClickListener(this)
        mMap.setOnInfoWindowClickListener(this)

        setUpMap()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
    }

    private fun setUpMap() {
        /*if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(this,
                //arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }*/

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                currentLatLng = LatLng(location.latitude, location.longitude)
                //placeMarkerOnMap(currentLatLng)
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15.0f))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15.0f))
            }
        }

    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

        mMap.addMarker(markerOptions)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)
        mMap.addMarker(markerOptions)
    }  // 내위치 마커로 표시

    private fun getAddress(latLng: LatLng): String {
        // 1, Creates a Geocoder object to turn a latitude and longitude coordinate
        // into an address and vice versa.
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(this@MapsActivity), PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }
}
