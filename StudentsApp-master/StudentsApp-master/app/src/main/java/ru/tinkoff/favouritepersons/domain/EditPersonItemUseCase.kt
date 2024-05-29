package ru.tinkoff.favouritepersons.domain

class EditPersonItemUseCase(private val personListRepository: PersonListRepository) {
    suspend fun editPersonItem (personItem: PersonItem) {
        personListRepository.editPersonItem(personItem)
    }
}