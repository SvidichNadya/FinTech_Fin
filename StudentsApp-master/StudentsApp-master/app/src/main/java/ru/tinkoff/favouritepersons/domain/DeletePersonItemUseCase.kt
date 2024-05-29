package ru.tinkoff.favouritepersons.domain

class DeletePersonItemUseCase(private val personListRepository: PersonListRepository) {
    suspend fun deletePersonItem(personItem: PersonItem) {
        personListRepository.deletePersonItem(personItem)
    }
}