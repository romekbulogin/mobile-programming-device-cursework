package ru.edu.mobile_programming_device_cursework.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, Database.DATABASE_NAME, null, Database.DATABASE_VERSION) {
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(Database.CREATE_TABLE)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL(Database.SQL_DELETE_ENTRIES)
        onCreate(database)
    }
}