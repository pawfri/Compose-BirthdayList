package com.example.birthdaylist

import com.example.birthdaylist.data.Person

data class PersonsUIState (
    val isLoading: Boolean = false,
    val books: List<Person> = emptyList(),
    val error: String? = null
)