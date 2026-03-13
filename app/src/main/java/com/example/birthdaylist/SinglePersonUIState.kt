package com.example.birthdaylist

import com.example.birthdaylist.data.Person

data class SinglePersonUIState(
    val isLoading: Boolean = false,
    val book: Person? = null,
    val error: String? = null
)