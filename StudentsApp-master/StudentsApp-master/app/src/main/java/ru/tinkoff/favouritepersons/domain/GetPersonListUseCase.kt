package ru.tinkoff.favouritepersons.domain

import kotlinx.coroutines.flow.Flow


class GetPersonListUseCase(private val personListRepository: PersonListRepository) {

    fun getPersonList(): Flow<List<PersonItem>> {
        return personListRepository.getPersonList()
    }

}