package ru.edu.mobile_programming_device_cursework

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import ru.edu.mobile_programming_device_cursework.database.DatabaseService
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Date


class MainActivity : AppCompatActivity() {
    private lateinit var authButton: Button
    private lateinit var username: TextView
    private lateinit var password: TextView
    private val databaseService = DatabaseService(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authButton = findViewById(R.id.button_auth)
        username = findViewById(R.id.username)
        password = findViewById(R.id.passwordDatabase)
    }


    private fun createDialog(text: String) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Warning")
            setMessage(text)
            setPositiveButton("OK") { dialog, i ->
                val myIntent = Intent(this@MainActivity, DatabaseListActivity::class.java)
                this@MainActivity.startActivity(myIntent)
            }
            builder.show()
        }
    }

    fun callPost(view: View) {
        val client = OkHttpClient()
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            "{\"email\":\"${username.text}\",\"password\":\"${password.text}\"}"
        )
        createDialog("Hello roman!")
//        val request: Request = Request.Builder()
//            .url("http://92.255.110.121:8080/api/auth/authentication")
//            .post(body)
//            .build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val temp = response.body.string()
//                println("TEMP: $temp")
//                databaseService.createConnection()
//                databaseService.insertToLoginTable(temp, Date())
//
//            }
//        })
//        Thread.sleep(1000)
//        databaseService.createConnection()
//        val inputAsString = databaseService.getResponseFromTable()
//        println("INPUTASSTRING: $inputAsString")
//        val jsonObject = JSONObject(inputAsString)
//        val user = jsonObject.getJSONObject("user")
//        createDialog("Hello ${user.getString("username")}!")
    }
}