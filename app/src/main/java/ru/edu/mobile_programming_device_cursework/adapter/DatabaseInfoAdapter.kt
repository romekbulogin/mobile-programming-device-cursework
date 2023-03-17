package ru.edu.mobile_programming_device_cursework.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.edu.mobile_programming_device_cursework.R
import ru.edu.mobile_programming_device_cursework.dto.DatabaseInfo

class DatabaseInfoAdapter : RecyclerView.Adapter<DatabaseInfoAdapter.ViewHolder>() {
    private var databases = mutableListOf<DatabaseInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.database_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.dbmsTitle).text = databases[position].dbms
        holder.itemView.findViewById<TextView>(R.id.name_database).text = databases[position].url!!.split("/")[3]
        holder.itemView.findViewById<TextView>(R.id.url).text = databases[position].url
        holder.itemView.findViewById<TextView>(R.id.login).text = databases[position].username
        holder.itemView.findViewById<TextView>(R.id.passwordDatabase).text = databases[position].password
    }

    override fun getItemCount(): Int = databases.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<DatabaseInfo>) {
        this.databases = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}