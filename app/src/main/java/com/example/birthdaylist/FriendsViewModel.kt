package com.example.birthdaylist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Friend(val name: String, val birthday: String)

class FriendsViewModel : ViewModel() {

    private val _friends = mutableStateListOf<Friend>()
    val friends: List<Friend> get() = _friends

    init {
        _friends.addAll(
            listOf(
                Friend("Anna", "12.03.1998"),
                Friend("Peter", "08.07.1997"),
                Friend("Maria", "25.11.1999")
            )
        )
    }

    fun addFriend(friend: Friend) {
        _friends.add(friend)
    }

    fun deleteFriend(friend: Friend) {
        _friends.remove(friend)
    }

    fun updateFriend(oldFriend: Friend, newFriend: Friend) {
        val index = _friends.indexOf(oldFriend)
        if (index != -1) _friends[index] = newFriend
    }
}