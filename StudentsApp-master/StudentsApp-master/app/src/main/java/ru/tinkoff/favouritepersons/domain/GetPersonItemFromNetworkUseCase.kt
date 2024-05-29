package ru.tinkoff.favouritepersons.domain

import retrofit2.Response
import ru.tinkoff.favouritepersons.data.network.Persons

class GetPersonItemFromNetworkUseCase(private val personListRepository: PersonListRepository) {
    suspend fun getPersonItem() : Response<Persons> {
        return personListRepository.getRandomPerson()
    }
}