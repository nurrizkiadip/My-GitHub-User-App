package com.nurrizkiadip_18102064.mygithubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurrizkiadip_18102064.mygithubapp.R
import com.nurrizkiadip_18102064.mygithubapp.data.Users
import com.nurrizkiadip_18102064.mygithubapp.databinding.ItemFollowingsBinding

class UserFollowingsAdapter(private val context: FragmentActivity): RecyclerView.Adapter<UserFollowingsAdapter.ViewHolder>() {
    private var userFollowings = arrayListOf<Users>()

    fun setFollowings(users: ArrayList<Users>){
        this.userFollowings = users
        this.notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemFollowingsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFollowingsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userFollowing = userFollowings[position]
        holder.binding.tvNameFollowing.text = userFollowing.username
        Glide.with(context)
            .load(userFollowing.avatar_url)
            .placeholder(R.drawable.user2_placeholder)
            .into(holder.binding.imgFollowing)
    }

    override fun getItemCount(): Int = userFollowings.size
}