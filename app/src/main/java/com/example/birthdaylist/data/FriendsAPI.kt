package com.example.birthdaylist.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FriendsAPI {
    @GET("persons")
    suspend fun getFriends(): Response<List<Friend>>

    @POST("persons")
    suspend fun addFriend(@Body friend: Friend): Response<Friend>

    @DELETE("persons/{id}")
    suspend fun deleteFriend(@Path("id") id: Int): Response<Friend>

    @PUT("persons/{id}")
    suspend fun updateFriend(@Path("id") id: Int, @Body friend: Friend): Response<Friend>
}