package com.example.user.timertutorial

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBboard(context: Context) : SQLiteOpenHelper(context, "board.db", null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "create table BoardTable( " +
                "idx integer primary key autoincrement, " +
                "cateData text not null, " +
                "titleData text not null, " +
                "dateData text not null, " +
                "contentData text not null " +
                ")"
        db?.execSQL(sql)
        // text,int,float,날짜 값을 넣는다. 내가 필요한 부분은 여기서 바꾸면 됨. ex. date->otherthing
        // null이라는 것을 허용하지 않는다.
        // table완성
        //helper의 역할 -> 앱이 처음 설치될 때 한번만 oncreate가 실행된다. 두번 다시 실행되는 것은 아님
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //다루지는 않을 것
    }

}
