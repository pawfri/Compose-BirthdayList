package com.example.birthdaylist.data

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PersonsRepositoryImpl(
    private val personsAPI: PersonsAPI,
    private val dispatcher: CoroutineDispatcher
) : PersonsRepository {
    override suspend fun getPersons(): NetworkResult<List<Person>> {
        return withContext(dispatcher) {
            try {
                val response = personsAPI.getPersons()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        NetworkResult.Success(body)
                    } else {
                        NetworkResult.Error("Response body is null")
                    }
                } else {
                    NetworkResult.Error(response.message())
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    override suspend fun addPerson(person: Person): NetworkResult<Person> {
        return withContext(dispatcher) {
            try {
                val response = personsAPI.addPerson(person)
                if (response.isSuccessful)
                    if (response.body() != null)
                        NetworkResult.Success(response.body()!!)
                    else
                        NetworkResult.Error("Response body is null")
                else
                    NetworkResult.Error(response.message())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    override suspend fun deletePerson(id: Int): NetworkResult<Person> {
        return withContext(dispatcher) {
            try {
                val response = personsAPI.deletePerson(id)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null)
                        NetworkResult.Success(body)
                    else
                        NetworkResult.Error("Response body is null")
                } else
                    NetworkResult.Error(response.message())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }


    override suspend fun updatePerson(id: Int, data: Person): NetworkResult<Person> {
        return withContext(dispatcher) {
            try {
                val response = personsAPI.updatePerson(id, data)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        NetworkResult.Success(body)
                    } else {
                        NetworkResult.Error("Response body is null")
                    }
                    NetworkResult.Success(response.body()!!)
                } else
                    NetworkResult.Error(response.message())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}