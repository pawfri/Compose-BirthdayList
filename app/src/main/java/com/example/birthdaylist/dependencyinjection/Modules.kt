package com.example.birthdaylist.dependencyinjection

import com.example.birthdaylist.data.FriendsAPI
import com.example.birthdaylist.data.FriendsRepository
import com.example.birthdaylist.data.FriendsRepositoryImpl
import com.example.birthdaylist.viewmodel.FriendsViewModel
import kotlinx.coroutines.Dispatchers
//import kotlinx.serialization.json.Json
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// jakewarton is now official retrofit

/*private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}*/

val appModules = module {
    single<FriendsRepository> { FriendsRepositoryImpl(get(), get()) }
    single { Dispatchers.IO }
    single { FriendsViewModel(get()) }
    single {
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
                //json.asConverterFactory("application/json; charset=UTF8".toMediaType())
                // retrofit converter not working, using Gson
            )
            .baseUrl("https://birthdaysrest.azurewebsites.net/api/")
            .build()
    }
    single { get<Retrofit>().create(FriendsAPI::class.java) }
}