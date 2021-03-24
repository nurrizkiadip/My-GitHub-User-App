package com.nurrizkiadip_18102064.mygithubapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nurrizkiadip_18102064.mygithubapp.databinding.ItemUserBinding

class UserAdapter (private val context: Context): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    var users = arrayListOf<User>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback{
        fun setOnItemClicked(user: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent, false))
        return view
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = users[position]
        holder.binding.imgUser.setImageResource(user.avatar!!)
        holder.binding.tvNameUser.text = user.name
        holder.binding.tvCompanyUser.text = user.company
        holder.binding.tvUserFollowers.text = user.follower.toString()
        holder.binding.tvUserFollowing.text = user.following.toString()

        holder.itemView.setOnClickListener{
            onItemClickCallback.setOnItemClicked(users[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = users.size
}

