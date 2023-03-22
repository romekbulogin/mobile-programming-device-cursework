package ru.edu.mobile_programming_device_cursework.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperTableDatabases(context: Context) :
    SQLiteOpenHelper(context, Database.DATABASES_NAME, null, Database.DATABASE_VERSION) {
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(Database.CREATE_TABLE_DATABASES)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL(Database.SQL_DELETE_ENTRIES_DATABASES)
        onCreate(database)
    }
}