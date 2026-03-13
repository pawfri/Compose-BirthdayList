package com.example.birthdaylist.dependencyinjection

import com.example.birthdaylist.data.PersonsAPI
import com.example.birthdaylist.data.PersonsRepository
import com.example.birthdaylist.data.PersonsRepositoryImpl
import com.example.birthdaylist.viewmodel.PersonsViewModel
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
    single<PersonsRepository> { PersonsRepositoryImpl(get(), get()) }
    single { Dispatchers.IO }
    single { PersonsViewModel(get()) }
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
    single { get<Retrofit>().create(PersonsAPI::class.java) }
}