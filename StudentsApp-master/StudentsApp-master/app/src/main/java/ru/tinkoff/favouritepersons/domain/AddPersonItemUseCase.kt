package ru.tinkoff.favouritepersons.domain

class AddPersonItemUseCase(private val personListRepository: PersonListRepository) {
    suspend fun addPersonItem(personItem: PersonItem) {
        personListRepository.addPersonItem(personItem)
    }
}