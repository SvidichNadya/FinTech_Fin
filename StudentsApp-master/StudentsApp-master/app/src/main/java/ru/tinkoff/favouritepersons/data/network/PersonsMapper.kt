package ru.tinkoff.favouritepersons.data.network

import ru.tinkoff.favouritepersons.domain.Gender
import ru.tinkoff.favouritepersons.domain.PersonItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

object PersonsMapper {

    fun personToPersonItem(person: PersonDto?) : List<PersonItem> {
        if (person == null) {
            return listOf()
        }
        else
        {
            return listOf(PersonItem(
                name = person.name.first,
                surname = person.name.last,
                birthDate = dobDateToBirthDate(person.dob.date),
                age = person.dob.age.toInt(),
                gender = if (person.gender == "female") Gender.FEMALE else Gender.MALE,
                email = person.email,
                phone = person.phone,
                address = "${person.location.state}, ${person.location.city}, ${person.location.street.name} - ${person.location.street.number}, ${person.location.country}",
                rating = Random.nextInt(1, 100),
                imageLink = person.picture.medium
            )
            )
        }
    }

    private fun dobDateToBirthDate(dobDate: String): String {
        return LocalDateTime.parse(dobDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withLocale(Locale.US)).toLocalDate().toString()
    }

}
