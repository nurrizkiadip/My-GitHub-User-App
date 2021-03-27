package com.nurrizkiadip_18102064.mygithubapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurrizkiadip_18102064.mygithubapp.adapter.UserAdapter
import com.nurrizkiadip_18102064.mygithubapp.databinding.ActivityMainBinding
import com.nurrizkiadip_18102064.mygithubapp.view_model.HomeUserViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var homeUserViewModel: HomeUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvUser = binding.lvGithubUser
        userAdapter = UserAdapter(this)

        homeUserViewModel = ViewModelProvider(this@MainActivity, ViewModelProvider.NewInstanceFactory())
            .get(HomeUserViewModel::class.java)
        homeUserViewModel.getUsers().observe(this@MainActivity){
            if (it != null){
                if(it.isEmpty()){
                    showNotFound(true)
                    userAdapter.setDataUser(arrayListOf())
                } else showNotFound(false)
                userAdapter.setDataUser(it)
                showLoading(false)
            }
        }

        showListUser()
    }

    private fun showListUser() {
        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.adapter = userAdapter
        userAdapter.notifyDataSetChanged()

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun setOnItemClicked(username: String) {
                val detail = Intent(this@MainActivity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DETAIL, username)
                startActivity(detail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        //kode untuk menampilkan dalam bentuk search bar
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.searh_hint)
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_change_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*Gunakan method ini ketika searchview submit atau OK*/
    override fun onQueryTextSubmit(query: String): Boolean {
        showLoading(true)
        showNotFound(false)
        homeUserViewModel.getSearchUser(this@MainActivity, query)
        if(query.isEmpty()) {
            userAdapter.setDataUser(arrayListOf())
//            showNotFound(false)
        }
        return true
    }

    /*Gunakan method ini untuk search view merespon tiap perubahan huruf pada searchView*/
    override fun onQueryTextChange(newText: String): Boolean {
        showLoading(true)
        showNotFound(false)
        if(newText.isEmpty()) {
            userAdapter.setDataUser(arrayListOf())
//            showNotFound(false)
        }

        homeUserViewModel.getSearchUser(this@MainActivity, newText)
        return false
    }

    private fun showLoading(state: Boolean){
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun showNotFound(state: Boolean){
        if (state) binding.tvNofound.visibility = View.VISIBLE
        else binding.tvNofound.visibility = View.GONE
    }
}