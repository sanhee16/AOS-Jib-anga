package com.example.user.timertutorial

import android.app.Service
import android.content.Intent
import android.database.Cursor
import android.os.Handler
import android.os.IBinder
import android.telephony.SmsManager
import android.widget.Toast
import android.support.v4.media.VolumeProviderCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.MediaSessionCompat

class MainService : Service() {
    private var mediaSession: MediaSessionCompat? = null

    private var flag = true
    private val handler = Handler()
    internal var time = 0

    var textData = " "
    override fun onCreate() {
        super.onCreate()

        mediaSession = MediaSessionCompat(this, "MainService")
        mediaSession!!.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mediaSession!!.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0f) //you simulate a player which plays something.
                .build()
        )

        val myVolumeProvider = object : VolumeProviderCompat(
            VolumeProviderCompat.VOLUME_CONTROL_RELATIVE, /*max volume*/
            100, /*initial volume level*/
            0
        ) {
            override fun onAdjustVolume(direction: Int){
                if(direction==-1) {
                    if (flag) {
                        flag = false
                        handler.postDelayed({flag = true}, 1000)
                        time = time + 1
                        if(time == 3){
                            try {
                                get_text()
                                sendsms()
                                time = 0
                            } catch (e: Exception) {
                                Toast.makeText(getApplicationContext(), "전송 실패", Toast.LENGTH_LONG).show();
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        }
            mediaSession!!.setPlaybackToRemote(myVolumeProvider)
            mediaSession!!.isActive = true
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

    fun sendsms(){

        var helper = DBphone(this)
        var db = helper.writableDatabase // 권한받기!! 꼭 해야함
        var phoneNo = ""// 설정 값 불러오기

        //쿼리문 만들기
        var sql = "select*from PhoneTable"

        var c: Cursor = db.rawQuery(sql, null)

        while(c.moveToNext()){
            var idx_pos = c.getColumnIndex("idx")
            var nameData_pos = c.getColumnIndex("nameData")
            var numberData_pos = c.getColumnIndex("numberData")

            var order_idx = c.getInt(idx_pos)
            var nameData = c.getString(nameData_pos)
            var numberData= c.getString(numberData_pos)
            phoneNo = numberData


            var sms = textData
            Toast.makeText(applicationContext,phoneNo, Toast.LENGTH_LONG).show()
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNo, null, textData, null, null)
        }

        db.close()

    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession!!.release()
    }
}

