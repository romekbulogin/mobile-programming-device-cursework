package ru.edu.mobile_programming_device_cursework

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import ru.edu.mobile_programming_device_cursework.adapter.DatabaseInfoAdapter
import ru.edu.mobile_programming_device_cursework.database.DatabaseService
import ru.edu.mobile_programming_device_cursework.database.DatabaseServiceTableDatabases
import ru.edu.mobile_programming_device_cursework.dto.DatabaseInfo
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*


class DatabaseInfoActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseInfoAdapter: DatabaseInfoAdapter
    private var databaseInfoList = mutableListOf<DatabaseInfo>()
    private val databaseService = DatabaseServiceTableDatabases(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_info)
        val value = intent.getStringExtra("dbms")
        println("CURRENT DBMS: $value")
        databaseService.createConnection()
        databaseService.dropTable()
        val client = OkHttpClient()
        val formBody = RequestBody.create(null, ByteArray(0))
        val request: Request = Request.Builder()
            .method("POST", formBody)
            .url("http://92.255.110.121:8084/api/instances/find_by_dbms/$value")
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()

                }

                override fun onResponse(call: Call, response: Response) {
                    val temp = response.body.string()
                    println("TEMP: $temp")
                    if (temp != "{\"error\":\"Result must not be null\"}") {
                        databaseService.createConnection()
                        databaseService.insertToDatabaseTable(temp)
                    }
                }
            })
        } catch (ex: Exception) {
            createDialog("Database in DBMS $value not found")
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.databaseInfo)
        recyclerView.layoutManager = layoutManager
        databaseInfoAdapter = DatabaseInfoAdapter()
        Thread.sleep(2000)
        databaseService.createConnection()
        val inputAsString = databaseService.getResponseFromDatabasesTable()
        val jsonObject = JSONArray(inputAsString)
        if (inputAsString.size > 0) {
            println("SIZE DATABASES: " + inputAsString.size)
            for (i in 0 until jsonObject.length()) {
                databaseInfoList.add(
                    DatabaseInfo(
                        jsonObject.getJSONObject(i).getString("url"),
                        jsonObject.getJSONObject(i).getString("username"),
                        jsonObject.getJSONObject(i).getString("password"),
                        jsonObject.getJSONObject(i).getString("dbms")
                    )
                )
            }
            databaseInfoAdapter.setList(databaseInfoList)
            recyclerView.adapter = databaseInfoAdapter
        } else {
            createDialog("Database in DBMS $value not found")
        }

    }

    private fun createDialog(text: String) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Warning")
            setMessage(text)
            setPositiveButton("OK") { dialog, i ->
            }
            builder.show()
        }
    }
}