package com.example.birthdaylist.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdaylist.data.Person
import com.example.birthdaylist.data.PersonsRepository
import com.example.birthdaylist.data.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PersonsUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val persons: List<Person> = emptyList()
)

data class SinglePersonUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val person: Person? = null
)

class PersonsViewModel(
    private val personsRepository: PersonsRepository // dependency injection
) : ViewModel() {

    private val _personsUIState = MutableStateFlow(PersonsUIState())
    val personsUIState: StateFlow<PersonsUIState> = _personsUIState

    private val _singlePersonUIState = MutableStateFlow(SinglePersonUIState())

    private var originalPersonList: List<Person> = emptyList()

    init {
        getPersons()
    }

    fun getPersons() {
        _personsUIState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = personsRepository.getPersons()) {
                is NetworkResult.Success -> {
                    originalPersonList = result.data
                    _personsUIState.update { ui ->
                        ui.copy(isLoading = false, persons = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    _personsUIState.update { ui ->
                        ui.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    fun addPerson(person: Person) {
        _singlePersonUIState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = personsRepository.addPerson(person)) {
                is NetworkResult.Success -> {
                    _singlePersonUIState.update { it.copy(isLoading = false, person = result.data) }
                    getPersons() // refresh list after mutation
                }
                is NetworkResult.Error -> {
                    _singlePersonUIState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

    fun deletePerson(personId: Int) {
        _singlePersonUIState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = personsRepository.deletePerson(personId)) {
                is NetworkResult.Success -> {
                    _singlePersonUIState.update { it.copy(isLoading = false, person = result.data) }
                    getPersons()
                }
                is NetworkResult.Error -> {
                    _singlePersonUIState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

    fun updatePerson(personId: Int, person: Person) {
        _singlePersonUIState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = personsRepository.updatePerson(personId, person)) {
                is NetworkResult.Success -> {
                    _singlePersonUIState.update { it.copy(isLoading = false, person = result.data) }
                    getPersons()
                }
                is NetworkResult.Error -> {
                    _singlePersonUIState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

    /** Filtering: name contains */
    fun filterByName(nameFragment: String) {
        if (nameFragment.isBlank()) {
            _personsUIState.update { it.copy(persons = originalPersonList) }
        } else {
            _personsUIState.update { ui ->
                ui.copy(
                    persons = ui.persons.filter { p ->
                        p.name.contains(nameFragment, ignoreCase = true)
                    }
                )
            }
        }
    }

    /** Sorting: name */
    fun sortByName(ascending: Boolean) {
        _personsUIState.update { ui ->
            ui.copy(
                persons = if (ascending)
                    ui.persons.sortedBy { it.name }
                else
                    ui.persons.sortedByDescending { it.name }
            )
        }
    }

    /** Sorting: age */
    fun sortByAge(ascending: Boolean) {
        _personsUIState.update { ui ->
            ui.copy(
                persons = if (ascending)
                    ui.persons.sortedWith(compareBy(nullsLast()) { it.age })
                else
                    ui.persons.sortedWith(compareBy(nullsLast<Int>()) { it.age }).reversed()
            )
        }
    }
}

