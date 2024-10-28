package com.hieunt.gmail_recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items : ArrayList<ItemModel>) : RecyclerView.Adapter<MyAdapter.ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.ItemViewHolder, position: Int) {
        holder.avatar.setImageResource(items[position].avatar)
        holder.title.text = items[position].title
        holder.content.text = items[position].content
        holder.time.text = items[position].time
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class ItemViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar : ImageView
        val title : TextView
        val content : TextView
        val time : TextView
        init {
            avatar = itemView.findViewById(R.id.avatar)
            title = itemView.findViewById(R.id.title)
            content = itemView.findViewById(R.id.content)
            time = itemView.findViewById(R.id.time)
        }
    }
}