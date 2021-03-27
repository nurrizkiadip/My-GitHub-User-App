package com.nurrizkiadip_18102064.mygithubapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurrizkiadip_18102064.mygithubapp.R
import com.nurrizkiadip_18102064.mygithubapp.data.Users
import com.nurrizkiadip_18102064.mygithubapp.databinding.ItemUserBinding

class UserAdapter (private val context: Context):
        RecyclerView.Adapter<UserAdapter.MyViewHolder>()
{
    private var users = arrayListOf<Users>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setDataUser(users: ArrayList<Users>){
        this.users = users
        this.notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun setOnItemClicked(username: String)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvNameUser.text = user.username
        Glide.with(context)
            .load(user.avatar_url)
            .placeholder(R.drawable.user2_placeholder)
            .into(holder.binding.imgUser)

        holder.itemView.setOnClickListener{
            onItemClickCallback.setOnItemClicked(users[holder.adapterPosition].username)
        }
    }

    override fun getItemCount(): Int = users.size

    inner class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}

