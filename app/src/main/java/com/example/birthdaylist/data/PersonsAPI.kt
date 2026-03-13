package com.example.birthdaylist.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonsAPI {
    @GET("persons")
    suspend fun getPersons(): Response<List<Person>>

    @POST("persons")
    suspend fun addPerson(@Body book: Person): Response<Person>

    @DELETE("persons/{id}")
    suspend fun deletePerson(@Path("id") id: Int): Response<Person>

    @PUT("persons/{id}")
    suspend fun updatePerson(@Path("id") id: Int, @Body person: Person): Response<Person>
}