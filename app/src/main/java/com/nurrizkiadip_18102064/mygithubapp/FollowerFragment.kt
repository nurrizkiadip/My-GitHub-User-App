package com.nurrizkiadip_18102064.mygithubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurrizkiadip_18102064.mygithubapp.adapter.UserFollowersAdapter
import com.nurrizkiadip_18102064.mygithubapp.databinding.FragmentFollowerBinding
import com.nurrizkiadip_18102064.mygithubapp.view_model.UserDetailViewModel

class FollowerFragment : Fragment() {

    private val userDetailViewModel: UserDetailViewModel by activityViewModels()
    private lateinit var userFollowersAdapter: UserFollowersAdapter

    companion object{
        val TAG: String = FollowerFragment::class.java.simpleName
        const val EXTRA_USERNAME_FOLLOWER = "username_followers"
    }

    private var _binding: FragmentFollowerBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        binding.pbFollower.visibility = View.VISIBLE
        userFollowersAdapter = UserFollowersAdapter(requireActivity())
        binding.rvFollowing.adapter = userFollowersAdapter
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Follower fragment running...")
        val username = arguments?.getString(EXTRA_USERNAME_FOLLOWER)

        userDetailViewModel.getFollowerUser(username)
        userDetailViewModel.getListOfFollower().observe(viewLifecycleOwner){ users ->
            if (users != null){
                if (users.isEmpty()) showNotFound(true)
                else showNotFound(false)
                userFollowersAdapter.setFollowers(users)
                userFollowersAdapter.notifyDataSetChanged()
                binding.pbFollower.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showNotFound(state: Boolean){
        if (state) binding.tvNofound.visibility = View.VISIBLE
        else binding.tvNofound.visibility = View.GONE
    }

}