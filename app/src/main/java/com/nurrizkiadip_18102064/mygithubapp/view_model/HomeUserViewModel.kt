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
import org.json.JSONObject
import java.lang.Exception

class HomeUserViewModel: ViewModel() {
    private val listUser = MutableLiveData<ArrayList<Users>>()

    companion object{
        val TAG: String = HomeUserViewModel::class.java.simpleName
        //Untuk alasan keamaan kredensial, hindari menyematkan sebuah TOKEN API ke dalam sebuah kelas,
        // sebaiknya dipindahkan ke dalam berkas build.gradle
        private const val TOKEN_GITHUB = "fbf919c6fef2ff33ce88767033ab0779132b5742"

    }

    fun getSearchUser(context: Context, username: String?){
        val listItems = ArrayList<Users>()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $TOKEN_GITHUB")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=$username"
        Log.d(TAG, "getUser: $url")

        if(username!!.isNotEmpty()) {
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>,
                        responseBody: ByteArray
                ) {
                    val result = String(responseBody)

                    try {
                        val responseObject = JSONObject(result)
                        val items = responseObject.getJSONArray("items")

                        for (i in 0 until items.length()) {
                            val userData = items.getJSONObject(i)
                            val user = Users()
                            user.id = userData.getString("id")
                            user.username = userData.getString("login")
                            user.avatar_url = userData.getString("avatar_url")

                            listItems.add(user)
                        }
                        listUser.postValue(listItems)

                        Log.d(TAG, "OnSuccess Selesai")
                    } catch (e: Exception) {
                        Log.d(TAG, "onSuccess Gagal: ${e.message}")
                    }

                }

                override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>,
                        responseBody: ByteArray,
                        error: Throwable
                ) {
                    Log.d(TAG, "onFailure: Gagal\n${error.message}")
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

    fun getUsers(): MutableLiveData<ArrayList<Users>>{
        return listUser
    }
}