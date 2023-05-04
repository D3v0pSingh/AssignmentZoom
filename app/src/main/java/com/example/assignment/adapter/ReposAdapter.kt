package com.example.assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.assignment.R
import com.example.assignment.model.Repos

class ReposAdapter(private val context : Context, val onClicked:click): RecyclerView.Adapter<ReposAdapter.viewHolder>() {

    private val usingList = ArrayList<Repos>()

    inner class viewHolder(itemView: View): ViewHolder(itemView){
        val ownername: TextView = itemView.findViewById(R.id.owner_tv)
        val reponame :TextView = itemView.findViewById(R.id.REPOname_tv)
        val layout: CardView = itemView.findViewById(R.id.cardView)
        val description :TextView = itemView.findViewById(R.id.description_tv)
        val sharebtn : Button = itemView.findViewById(R.id.sharebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(context).inflate(R.layout.items,parent,false))
    }

    override fun getItemCount(): Int {
        return usingList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = usingList[position]
        holder.ownername.text = data.owner
        holder.reponame.text = data.name
        holder.description.text = data.description
        holder.layout.setOnClickListener {
            onClicked.onRepoClicked(usingList[holder.adapterPosition])
        }
        holder.sharebtn.setOnClickListener {
            onClicked.onBtnClicked(usingList[holder.adapterPosition])
        }
        holder.layout.setOnLongClickListener {
            onClicked.onLongClicked(usingList[holder.adapterPosition],holder.layout)
            true
        }
    }

    fun getLiveData(listitem:List<Repos>){
        usingList.clear()
        usingList.addAll(listitem)
        notifyDataSetChanged()
    }

    interface click{
        fun onRepoClicked(repos: Repos)
        fun onBtnClicked(repos: Repos)
        fun onLongClicked(repos:Repos,cardView: CardView)
    }
}