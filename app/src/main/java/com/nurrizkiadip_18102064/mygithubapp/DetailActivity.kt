package com.nurrizkiadip_18102064.mygithubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.nurrizkiadip_18102064.mygithubapp.adapter.SectionPagerAdapter
import com.nurrizkiadip_18102064.mygithubapp.databinding.ActivityDetailBinding
import com.nurrizkiadip_18102064.mygithubapp.view_model.UserDetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var binding: ActivityDetailBinding
    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private lateinit var userDetailViewModel: UserDetailViewModel

    companion object{
        const val EXTRA_DETAIL = "extra_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra(EXTRA_DETAIL).toString()
        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
            this.elevation = 0f
            this.title = "$username\'s Profile"
        }

        //ViewModel
        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserDetailViewModel::class.java)

        setUpTabLayout()
        getDataDetailUser()
    }

    private fun setUpTabLayout() {
        val fragmentList = arrayListOf(
                FollowerFragment(),
                FollowingFragment()
        )
        sectionPagerAdapter = SectionPagerAdapter(fragmentList, this@DetailActivity)
        binding.vpUserDetail.adapter = sectionPagerAdapter

        val tabLayoutMediator = TabLayoutMediator(binding.tlUserDetail, binding.vpUserDetail) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }
        tabLayoutMediator.attach()
        sectionPagerAdapter.username = username
    }

    private fun getDataDetailUser() {
        userDetailViewModel.getDetailUser(this@DetailActivity, username)
        userDetailViewModel.getUsers().observe(this){
            if (it != null){
                binding.tvUserNameDetail.text = it[0].name
                binding.tvUserCompanyDetail.text = it[0].company
                binding.tvUserLocationDetail.text = it[0].location
                binding.tvNumberRepository.text = it[0].numberOfRepository.toString()
                binding.tvNumberFollowers.text = it[0].numberOfFollowers.toString()
                binding.tvNumberFollowings.text = it[0].numberOfFollowing.toString()
                Glide.with(this)
                        .load(it[0].avatar_url)
                        .placeholder(R.drawable.user2_placeholder)
                        .into(binding.imgAvatarDetail)
            }
        }
    }
}