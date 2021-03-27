package com.nurrizkiadip_18102064.mygithubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurrizkiadip_18102064.mygithubapp.R
import com.nurrizkiadip_18102064.mygithubapp.data.Users
import com.nurrizkiadip_18102064.mygithubapp.databinding.ItemFollowersBinding

class UserFollowersAdapter(private val context: FragmentActivity): RecyclerView.Adapter<UserFollowersAdapter.ViewHolder>() {
    private var userFollowers = arrayListOf<Users>()

    fun setFollowers(users: ArrayList<Users>){
        this.userFollowers = users
        this.notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemFollowersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFollowersBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userFollower = userFollowers[position]
        holder.binding.tvNameFollower.text = userFollower.username
        Glide.with(context)
            .load(userFollower.avatar_url)
            .placeholder(R.drawable.user2_placeholder)
            .into(holder.binding.imgFollower)
    }

    override fun getItemCount(): Int = userFollowers.size
}