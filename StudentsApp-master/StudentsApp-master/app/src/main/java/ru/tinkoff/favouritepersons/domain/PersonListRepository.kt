package ru.tinkoff.favouritepersons.domain

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.tinkoff.favouritepersons.data.network.Persons

interface PersonListRepository {
    suspend fun addPersonItem(personItem: PersonItem)

    suspend fun editPersonItem(personItem: PersonItem)

    suspend fun deletePersonItem(personItem: PersonItem)

    fun getPersonItem(id: Int): PersonItem

    suspend fun getRandomPerson(): Response<Persons>

    fun getPersonList(): Flow<List<PersonItem>>

}
