package com.nurrizkiadip_18102064.mygithubapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nurrizkiadip_18102064.mygithubapp.FollowerFragment
import com.nurrizkiadip_18102064.mygithubapp.FollowingFragment

class SectionPagerAdapter(listFragment: ArrayList<Fragment>, activity: AppCompatActivity)
    : FragmentStateAdapter(activity) {

    private val fragments = listFragment
    var username: String? = null

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = FollowerFragment()
                fragment.apply {
                    arguments = Bundle().apply {
                        putString(FollowerFragment.EXTRA_USERNAME_FOLLOWER, username)
                    }
                }
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.apply {
                    arguments = Bundle().apply {
                        putString(FollowingFragment.EXTRA_USERNAME_FOLLOWING, username)
                    }
                }
            }
        }

        return fragment!!
    }
}