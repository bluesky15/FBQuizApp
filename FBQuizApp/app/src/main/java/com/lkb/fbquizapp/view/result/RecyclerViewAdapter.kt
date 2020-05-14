package com.lkb.fbquizapp.view.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lkb.fbquizapp.R
import com.lkb.fbquizapp.model.persistance.User

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var data: List<User> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, postion: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data.get(position).name
        holder.score.text = data.get(position).score.toString()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tvUserName)
        var score: TextView = itemView.findViewById(R.id.tvUserScore)
    }

    fun bindData(data: List<User>) {
        this.data = data
        notifyDataSetChanged()
    }
}