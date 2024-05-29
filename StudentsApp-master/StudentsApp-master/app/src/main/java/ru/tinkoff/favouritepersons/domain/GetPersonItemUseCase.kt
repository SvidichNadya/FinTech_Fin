package ru.tinkoff.favouritepersons.domain

class GetPersonItemUseCase(private val personListRepository: PersonListRepository) {
    fun getPersonItem(id: Int) : PersonItem {
        return personListRepository.getPersonItem(id)
    }
}