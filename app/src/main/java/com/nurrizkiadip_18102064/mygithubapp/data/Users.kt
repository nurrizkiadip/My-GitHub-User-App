package com.nurrizkiadip_18102064.mygithubapp.data

data class Users(
        var id: String = "",
        var username: String = "",
        var avatar_url: String = "",
        var name: String = "",
        var company: String = "",
        var location: String = "",
        var numberOfRepository: Int = 0,
        var numberOfFollowers: Int = 0,
        var numberOfFollowing: Int = 0,
)
