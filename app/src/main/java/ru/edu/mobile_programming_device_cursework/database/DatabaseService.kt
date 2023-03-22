package ru.edu.mobile_programming_device_cursework.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.Date

class DatabaseService(context: Context) {
    private val databaseHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase? = null

    fun createConnection() {
        database = databaseHelper.writableDatabase
    }

    fun insertToLoginTable(response: String, date: Date) {
        val values = ContentValues().apply {
            put(Database.COLUMN_NAME_RESPONSE, response)
            put(Database.COLUMN_NAME_DATE, date.toString())
        }
        database?.insert(Database.TABLE_NAME, null, values)
        database?.close()
    }

    @SuppressLint("Range")
    fun getResponseFromTable(): String {
        val cursor = database?.query(Database.TABLE_NAME, null, null, null, null, null, null)
        var response = ""

        while (cursor?.moveToNext()!!) {
            response = cursor.getString(cursor.getColumnIndex(Database.COLUMN_NAME_RESPONSE)).toString()
        }
        database?.close()
        return response
    }
}