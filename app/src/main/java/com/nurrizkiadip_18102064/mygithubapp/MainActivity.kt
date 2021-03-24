package com.nurrizkiadip_18102064.mygithubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurrizkiadip_18102064.mygithubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.lvGithubUser
        userAdapter = UserAdapter(this)

        showListUser()
        getItem()
    }

    private fun showListUser() {
        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun setOnItemClicked(user: User) {
                val detail = Intent(this@MainActivity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DATA, user)
                startActivity(detail)
            }
        })
    }


    private fun getItem() {
        val username = resources.getStringArray(R.array.username)
        val name = resources.getStringArray(R.array.name)
        val avatar = resources.obtainTypedArray(R.array.avatar)
        val company = resources.getStringArray(R.array.company)
        val location = resources.getStringArray(R.array.location)
        val repository = resources.getStringArray(R.array.repository)
        val follower = resources.getStringArray(R.array.followers)
        val following = resources.getStringArray(R.array.following)


        for (position in username.indices){
            val user = User(
                username = username[position],
                name[position],
                avatar.getResourceId(position, -1),
                company = company[position],
                location[position],
                repository[position].toInt(),
                follower[position].toInt(),
                following[position].toInt()
            )
            users.add(user)
        }
        userAdapter.users = users
    }
}