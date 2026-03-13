package com.example.birthdaylist.data

interface PersonsRepository {
    suspend fun getPersons(): NetworkResult<List<Person>>
    suspend fun addPerson(person: Person): NetworkResult<Person>
    suspend fun deletePerson(id: Int): NetworkResult<Person>
    suspend fun updatePerson(id: Int, data: Person): NetworkResult<Person>
}