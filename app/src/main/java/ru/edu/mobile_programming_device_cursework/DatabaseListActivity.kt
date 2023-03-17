package ru.edu.mobile_programming_device_cursework

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import ru.edu.mobile_programming_device_cursework.adapter.DatabaseAdapter
import ru.edu.mobile_programming_device_cursework.dto.Database

class DatabaseListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseAdapter: DatabaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_list)

        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.databaseList)
        recyclerView.layoutManager = layoutManager
        databaseAdapter = DatabaseAdapter()
        databaseAdapter.setList(mutableListOf<Database>().apply {
            add(Database("PostgreSQL"))
            add(Database("MySQL"))
            add(Database("MSSQL"))
        })
        recyclerView.adapter = databaseAdapter
        databaseAdapter.onItemClick = {
            val myIntent = Intent(this@DatabaseListActivity, DatabaseInfoActivity::class.java)
            myIntent.putExtra("dbms", it.dbms)
            this@DatabaseListActivity.startActivity(myIntent)
        }
    }

}