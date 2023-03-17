package ru.edu.mobile_programming_device_cursework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import ru.edu.mobile_programming_device_cursework.adapter.DatabaseInfoAdapter
import ru.edu.mobile_programming_device_cursework.dto.DatabaseInfo
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class DatabaseInfoActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseInfoAdapter: DatabaseInfoAdapter
    private var databaseInfoList = mutableListOf<DatabaseInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_info)
        val value = intent.getStringExtra("dbms")
        println("CURRENT DBMS: $value")

        val client = OkHttpClient()
        val formBody = RequestBody.create(null, ByteArray(0))
        val request: Request = Request.Builder()
            .method("POST", formBody)
            .url("http://92.255.110.121:8084/api/instances/find_by_dbms/$value")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val letDirectory = File(filesDir, "instances")
                letDirectory.mkdirs()
                val file = File(letDirectory, "InstancesResponse.txt")
                file.appendText(response.body.string())
            }
        })
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.databaseInfo)
        recyclerView.layoutManager = layoutManager
        databaseInfoAdapter = DatabaseInfoAdapter()
        val inputAsString = FileInputStream(File(File(filesDir, "instances"), "InstancesResponse.txt")).bufferedReader()
            .use { it.readText() }
        val jsonObject = JSONObject(inputAsString)
        println(inputAsString)
        databaseInfoAdapter.setList(mutableListOf<DatabaseInfo>().apply {
            add(
                DatabaseInfo(
                    jsonObject.getString("url"),
                    jsonObject.getString("username"),
                    jsonObject.getString("password"),
                    jsonObject.getString("dbms")
                )
            )
        })
        recyclerView.adapter = databaseInfoAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        val letDirectory = File(filesDir, "instances")
        letDirectory.mkdirs()
        val file = File(letDirectory, "InstancesResponse.txt")
        file.delete()
    }

}