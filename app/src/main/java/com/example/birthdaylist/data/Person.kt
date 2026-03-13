package com.example.birthdaylist.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    @SerialName("id")
    // TODO this is kotlinx annotation
    val id: Int = -1,
    @SerialName("name")
    val name: String,
    @SerialName("birthday")
    val birthday: String,
    @SerialName("age")
    val age: Int? = null
)