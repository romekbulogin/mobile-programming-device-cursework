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
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var authButton: Button
    private lateinit var username: TextView
    private lateinit var password: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authButton = findViewById(R.id.button_auth)
        username = findViewById(R.id.username)
        password = findViewById(R.id.passwordDatabase)

    }

    override fun onDestroy() {
        super.onDestroy()
        val letDirectory = File(filesDir, "LET")
        letDirectory.mkdirs()
        val file = File(letDirectory, "AuthenticationResponse.txt")
        file.delete()
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
        val temp = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            "{\"email\":\"${username.text}\",\"password\":\"${password.text}\"}"
        )
        val request: Request = Request.Builder()
            .url("http://92.255.110.121:8080/api/auth/authentication")
            .post(temp)
            .build()

        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) {
                            throw IOException(
                                "Запрос к серверу не был успешен:" +
                                        " ${response.code} ${response.message}"
                            )
                            createDialog(
                                "Запрос к серверу не был успешен:" +
                                        " ${response.code} ${response.message}"
                            )
                        }
                        val letDirectory = File(filesDir, "LET")
                        letDirectory.mkdirs()
                        val file = File(letDirectory, "AuthenticationResponse.txt")
                        file.appendText(response.body.string())
                    }
                }
            })
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        val inputAsString = FileInputStream(File(File(filesDir, "LET"), "AuthenticationResponse.txt")).bufferedReader()
            .use { it.readText() }
        val jsonObject = JSONObject(inputAsString)
        val user = jsonObject.getJSONObject("user")
        createDialog("Hello ${user.getString("username")}!")
    }
}