package com.nurrizkiadip_18102064.mygithubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nurrizkiadip_18102064.mygithubapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ("Detail GitHub User")

        user = intent.getParcelableExtra<User>(EXTRA_DATA) as User

        attactData()
    }

    private fun attactData() {
        binding.circleImageView.setImageResource(user.avatar!!)
        binding.tvNameDetail.text = user.name
        binding.tvUsername.text = user.username
        binding.tvCompanyDetail.text = user.company
        binding.tvLocation.text = user.location
        binding.tvRepository.text = user.repository.toString()
        binding.tvFollowers.text = user.follower.toString()
        binding.tvFollowing.text = user.following.toString()
    }
}