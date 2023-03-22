package ru.edu.mobile_programming_device_cursework.database

import android.provider.BaseColumns

object Database : BaseColumns {
    const val TABLE_NAME = "login"
    const val TABLE_NAME_DATABASES = "databases"
    const val COLUMN_NAME_RESPONSE_DATABASES = "database"
    const val COLUMN_NAME_RESPONSE = "username"
    const val COLUMN_NAME_DATE = "login_date"


    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "LoginsDatabase.db"
    const val DATABASES_NAME = "Databases.db"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_RESPONSE TEXT, " +
            "$COLUMN_NAME_DATE TEXT)"
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    const val CREATE_TABLE_DATABASES = "CREATE TABLE $TABLE_NAME_DATABASES (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_RESPONSE_DATABASES TEXT)"

    const val SQL_DELETE_ENTRIES_DATABASES = "DROP TABLE IF EXISTS $TABLE_NAME_DATABASES"
}