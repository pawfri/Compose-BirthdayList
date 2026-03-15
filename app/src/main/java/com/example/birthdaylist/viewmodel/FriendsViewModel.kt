package com.example.birthdaylist.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdaylist.data.Friend
import com.example.birthdaylist.data.FriendsRepository
import com.example.birthdaylist.data.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

data class FriendsUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val friends: List<Friend> = emptyList()
)

data class SingleFriendUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val friend: Friend? = null
)

class FriendsViewModel(
    private val friendsRepository: FriendsRepository // dependency injection
) : ViewModel() {

    private val _friendsUIState = MutableStateFlow(FriendsUIState())
    val friendsUIState: StateFlow<FriendsUIState> = _friendsUIState

    private val _singleFriendUIState = MutableStateFlow(SingleFriendUIState())

    private var originalFriendList: List<Friend> = emptyList()

    init {
        getFriends()
    }

    fun getFriends() {
        _friendsUIState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = friendsRepository.getFriends()) {
                is NetworkResult.Success -> {
                    originalFriendList = result.data
                    _friendsUIState.update { ui ->
                        ui.copy(isLoading = false, friends = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    _friendsUIState.update { ui ->
                        ui.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    fun addFriend(name: String, birthdayMillis: Long?) {
        _singleFriendUIState.update { it.copy(isLoading = true, error = null) }

        var birthYear: Int? = null
        var birthMonth: Int? = null
        var birthDay: Int? = null

        if (birthdayMillis != null) {
            val convert = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            convert.timeInMillis = birthdayMillis
            birthYear = convert.get(Calendar.YEAR)
            birthMonth = convert.get(Calendar.MONTH) + 1
            birthDay = convert.get(Calendar.DAY_OF_MONTH)
        }

        val friend = Friend(
            id = -1,
            userId = "Test@test.dk", //TODO: Create user logic instead of hardcoded
            name = name.trim(),
            birthYear = birthYear,
            birthMonth = birthMonth,
            birthDayOfMonth = birthDay,
            remarks = null,
            age = null,
        )

        viewModelScope.launch {
            when (val result = friendsRepository.addFriend(friend)) {
                is NetworkResult.Success -> {
                    _singleFriendUIState.update { it.copy(isLoading = false, friend = result.data) }
                    getFriends()
                }
                is NetworkResult.Error -> {
                    _singleFriendUIState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

    fun deleteFriend(id: Int) {
        _singleFriendUIState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = friendsRepository.deleteFriend(id)) {
                is NetworkResult.Success -> {
                    _singleFriendUIState.update { it.copy(isLoading = false, friend = result.data) }
                    getFriends()
                }
                is NetworkResult.Error -> {
                    _singleFriendUIState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

    fun updateFriend(id: Int, friend: Friend) {
        _singleFriendUIState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = friendsRepository.updateFriend(id, friend)) {
                is NetworkResult.Success -> {
                    _singleFriendUIState.update { it.copy(isLoading = false, friend = result.data) }
                    getFriends()
                }
                is NetworkResult.Error -> {
                    _singleFriendUIState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

    /** Filtering: name contains */
    fun filterByName(nameFragment: String) {
        if (nameFragment.isBlank()) {
            _friendsUIState.update { it.copy(friends = originalFriendList) }
        } else {
            _friendsUIState.update { ui ->
                ui.copy(
                    friends = ui.friends.filter { p ->
                        p.name.contains(nameFragment, ignoreCase = true)
                    }
                )
            }
        }
    }

    /** Sorting: name */
    fun sortByName(ascending: Boolean) {
        _friendsUIState.update { ui ->
            ui.copy(
                friends = if (ascending)
                    ui.friends.sortedBy { it.name }
                else
                    ui.friends.sortedByDescending { it.name }
            )
        }
    }

    /** Sorting: age */
    fun sortByAge(ascending: Boolean) {
        _friendsUIState.update { ui ->
            ui.copy(
                friends = if (ascending)
                    ui.friends.sortedWith(compareBy(nullsLast()) { it.age })
                else
                    ui.friends.sortedWith(compareBy(nullsLast<Int>()) { it.age }).reversed()
            )
        }
    }
}

