package com.example.birthdaylist

import com.example.birthdaylist.data.Friend

data class FriendsUIState (
    val isLoading: Boolean = false,
    val books: List<Friend> = emptyList(),
    val error: String? = null
)