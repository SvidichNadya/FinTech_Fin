package ru.tinkoff.favouritepersons.data

import kotlinx.coroutines.flow.Flow
import ru.tinkoff.favouritepersons.data.network.RetrofitService
import ru.tinkoff.favouritepersons.domain.PersonItem
import ru.tinkoff.favouritepersons.domain.PersonListRepository
import ru.tinkoff.favouritepersons.room.PersonDao

class PersonListRepositoryImpl private constructor(
    private val personDao: PersonDao,
    private val personNetworkService : RetrofitService
    ): PersonListRepository {

    // get all the events
    override fun getPersonList(): Flow<List<PersonItem>> =  personDao.getAllPersons()


    // adds an event to our database.
    override suspend fun addPersonItem(personItem: PersonItem) {
        personDao.insertPerson(personItem)
    }

    override fun getPersonItem(id: Int): PersonItem {
        return personDao.getPersonById(id)
    }

    override suspend fun getRandomPerson() = personNetworkService.getPersons()

    // deletes an event from database.
    override suspend fun deletePersonItem(personItem: PersonItem) {
        personDao.deletePerson(personItem)
    }

    // updates an event from database.
    override suspend fun editPersonItem(personItem: PersonItem) {
        personDao.updatePerson(personItem)
    }


    companion object {

        @Volatile
        private var instance: PersonListRepositoryImpl? = null

        fun getInstance(personDao: PersonDao, personNetworkService: RetrofitService) =
            instance ?: synchronized(this) {
                instance ?: PersonListRepositoryImpl(personDao, personNetworkService).also { instance = it }
            }
    }

}