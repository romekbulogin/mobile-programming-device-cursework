package ru.edu.mobile_programming_device_cursework.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.edu.mobile_programming_device_cursework.DatabaseListActivity
import ru.edu.mobile_programming_device_cursework.MainActivity
import ru.edu.mobile_programming_device_cursework.R
import ru.edu.mobile_programming_device_cursework.R.*
import ru.edu.mobile_programming_device_cursework.dto.Database
import ru.edu.mobile_programming_device_cursework.dto.DatabaseInfo

class DatabaseAdapter : RecyclerView.Adapter<DatabaseAdapter.ViewHolder>() {
    var databases = mutableListOf<Database>()
    var onItemClick: ((Database) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout.item_database, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(id.name_database).text = databases[position].dbms
        if (databases[position].dbms == "PostgreSQL")
            holder.itemView.findViewById<ImageView>(id.dbms_image).setImageResource(drawable.postgresicon)
        if (databases[position].dbms == "MSSQL")
            holder.itemView.findViewById<ImageView>(id.dbms_image)
                .setImageResource(drawable.e215fbdada42f37f18934d6566726813)
        if (databases[position].dbms == "MySQL")
            holder.itemView.findViewById<ImageView>(id.dbms_image).setImageResource(drawable.mysqlicon)
    }

    override fun getItemCount(): Int = databases.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<Database>) {
        this.databases = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameDbms: TextView = itemView.findViewById(id.name_database)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(databases[adapterPosition])
            }
        }
    }

}