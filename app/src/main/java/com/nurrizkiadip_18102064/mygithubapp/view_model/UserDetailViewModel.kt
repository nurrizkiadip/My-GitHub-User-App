package com.nurrizkiadip_18102064.mygithubapp.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.nurrizkiadip_18102064.mygithubapp.data.Users
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class UserDetailViewModel: ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<Users>>()
    private val listFollowings = MutableLiveData<ArrayList<Users>>()
    private val listUser = MutableLiveData<ArrayList<Users>>()

    companion object{
        //Untuk alasan keamaan kredensial, hindari menyematkan sebuah TOKEN API ke dalam sebuah kelas,
        // sebaiknya dipindahkan ke dalam berkas build.gradle
        private const val TOKEN_GITHUB = "fbf919c6fef2ff33ce88767033ab0779132b5742"



    }

    fun getDetailUser(context: Context, username: String?){
        Log.d(HomeUserViewModel.TAG, "Get GitHub Users: Mulai...")
        val listDetail = ArrayList<Users>()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $TOKEN_GITHUB")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$username"
        Log.d(HomeUserViewModel.TAG, "getUser: $url")

        if(username!!.isNotEmpty()) {
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>,
                        responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    Log.d(HomeUserViewModel.TAG, "Ini dia")
                    Log.d(HomeUserViewModel.TAG, result)

                    try {
                        val responseObject = JSONObject(result)
                        val user = Users()
                        user.id = responseObject.getString("id")
                        user.username = responseObject.getString("login")
                        user.avatar_url = responseObject.getString("avatar_url")
                        user.name = responseObject.getString("name")
                        user.company = responseObject.getString("company")
                        user.location = responseObject.getString("location")
                        user.numberOfRepository = responseObject.getInt("public_repos")
                        user.numberOfFollowers = responseObject.getInt("followers")
                        user.numberOfFollowing = responseObject.getInt("following")

                        listDetail.add(user)
                        listUser.postValue(listDetail)
                        Log.d(HomeUserViewModel.TAG, "OnSuccess Selesai")
                    } catch (e: Exception) {
                        Log.d(HomeUserViewModel.TAG, "onSuccess Gagal\nError: ${e.message}")
                    }
                }

                override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>,
                        responseBody: ByteArray,
                        error: Throwable
                ) {
                    Log.d(HomeUserViewModel.TAG, "onFailure: Gagal")
                    val errorMessage = when (statusCode) {
                        400 -> "Bad request"
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> "Unknown"
                    }

                    Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            listUser.postValue(arrayListOf())
        }

    }


    fun getFollowingUser(username: String?){
        val list = ArrayList<Users>()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $TOKEN_GITHUB")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$username/following"

        client.get(url, object: AsyncHttpResponseHandler(){

            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(HomeUserViewModel.TAG, result)

                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()){
                        val following = responseArray.getJSONObject(i)
                        val user = Users()
                        user.id = following.getString("id")
                        user.username = following.getString("login")
                        user.avatar_url = following.getString("avatar_url")

                        list.add(user)
                    }

                    listFollowings.postValue(list)

                    Log.d(HomeUserViewModel.TAG, "OnSuccess Selesai")
                } catch (e: Exception) {
                    Log.d(HomeUserViewModel.TAG, "onSuccess Gagal\nError: ${e.message}")
                }
            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray,
                    error: Throwable
            ) {
                Log.d(HomeUserViewModel.TAG, "onFailure: Gagal")
                val errorMessage = when (statusCode) {
                    400 -> "Bad request"
                    401 -> "Unauthorized"
                    403 -> "Forbidden"
                    404 -> "Not Found"
                    else -> "Unknown"
                }

                Log.d(HomeUserViewModel.TAG, "Error: $errorMessage")
            }
        })
    }

    fun getFollowerUser(username: String?){
        val list = arrayListOf<Users>()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $TOKEN_GITHUB")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$username/followers"
        Log.d(HomeUserViewModel.TAG, "getCurrentWeather: $url")

        client.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(HomeUserViewModel.TAG, "Ini dia")
                Log.d(HomeUserViewModel.TAG, result)

                try {
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()){
                        val follower = responseObject.getJSONObject(i)
                        val user = Users()
                        user.id = follower.getString("id")
                        user.username = follower.getString("login")
                        user.avatar_url = follower.getString("avatar_url")

                        list.add(user)
                    }

                    listFollowers.postValue(list)
                    Log.d(HomeUserViewModel.TAG, "OnSuccess Selesai")
                } catch (e: Exception) {
                    Log.d(HomeUserViewModel.TAG, "onSuccess Gagal\nError: ${e.message}")

                }
            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray,
                    error: Throwable
            ) {
                Log.d(HomeUserViewModel.TAG, "onFailure: Gagal")
                val errorMessage = when (statusCode) {
                    400 -> "Bad request"
                    401 -> "Unauthorized"
                    403 -> "Forbidden"
                    404 -> "Not Found"
                    else -> "Unknown"
                }
                Log.d(HomeUserViewModel.TAG, "Error: $errorMessage")            }
        })
    }

    fun getListOfFollower(): MutableLiveData<ArrayList<Users>> {
        return listFollowers
    }
    fun getListOfFollowing(): MutableLiveData<ArrayList<Users>> {
        return listFollowings
    }
    fun getUsers(): MutableLiveData<ArrayList<Users>>{
        return listUser
    }

}