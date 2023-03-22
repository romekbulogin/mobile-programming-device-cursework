package ru.edu.mobile_programming_device_cursework.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.json.JSONObject

class DatabaseServiceTableDatabases(context: Context) {
    private val databaseHelper = DatabaseHelperTableDatabases(context)
    private var database: SQLiteDatabase? = null

    fun createConnection() {
        database = databaseHelper.writableDatabase
    }

    fun insertToDatabaseTable(response: String) {
        val values = ContentValues().apply {
            put(Database.COLUMN_NAME_RESPONSE_DATABASES, response)
        }
        database?.insert(Database.TABLE_NAME_DATABASES, null, values)
        database?.close()
    }

    @SuppressLint("Range")
    fun getResponseFromDatabasesTable(): MutableList<JSONObject> {
        val cursor = database?.query(Database.TABLE_NAME_DATABASES, null, null, null, null, null, null)
        val response: MutableList<JSONObject> = mutableListOf()

        while (cursor?.moveToNext()!!) {
            response.add(
                JSONObject(
                    cursor.getString(cursor.getColumnIndex(Database.COLUMN_NAME_RESPONSE_DATABASES)).toString()
                )
            )
        }
        database?.close()
        return response
    }


    fun dropTable() {
        database?.execSQL("DELETE FROM databases")
        database?.close()
    }
}