package com.example.birthdaylist

import com.example.birthdaylist.data.Friend

data class SingleFriendUIState(
    val isLoading: Boolean = false,
    val book: Friend? = null,
    val error: String? = null
)