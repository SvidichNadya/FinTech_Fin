package ru.tinkoff.favouritepersons.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tinkoff.favouritepersons.data.PersonListRepositoryImpl
import ru.tinkoff.favouritepersons.data.network.RetrofitService
import ru.tinkoff.favouritepersons.domain.AddPersonItemUseCase
import ru.tinkoff.favouritepersons.domain.DATE_FORMAT
import ru.tinkoff.favouritepersons.domain.EditPersonItemUseCase
import ru.tinkoff.favouritepersons.domain.Gender
import ru.tinkoff.favouritepersons.domain.GetPersonItemUseCase
import ru.tinkoff.favouritepersons.domain.PersonItem
import ru.tinkoff.favouritepersons.presentation.PersonErrorMessages
import ru.tinkoff.favouritepersons.room.PersonDataBase
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

class PersonItemViewModel(application: Application) : AndroidViewModel(application) {

    val errorMap: LiveData<MutableMap<PersonErrorMessages, Boolean>>
        get() = _errorMap

    private val _errorMap = MutableLiveData(
        mutableMapOf(
            PersonErrorMessages.NAME to true,
            PersonErrorMessages.SURNAME to true,
            PersonErrorMessages.BIRTHDATE to true,
            PersonErrorMessages.GENDER to true,
            PersonErrorMessages.EMAIL to true,
            PersonErrorMessages.PHONE to true,
            PersonErrorMessages.ADDRESS to true,
            PersonErrorMessages.RATING to true,
            PersonErrorMessages.IMAGE_LINK to true,
        )
    )


    private val _personItem = MutableLiveData<PersonItem>()
    val personItem: LiveData<PersonItem>
        get() = _personItem

    private val _shouldCloseScreen = MutableLiveData<Boolean>()
    val shouldCloseScreen: LiveData<Boolean>
        get() = _shouldCloseScreen

    private val editPersonItemUseCase: EditPersonItemUseCase
    private val addPersonItemUseCase: AddPersonItemUseCase
    private val getPersonItemUseCase: GetPersonItemUseCase

    init {
        val randomUserApiService = RetrofitService.getInstance("")
        val dao = PersonDataBase.getDBClient(application).personsDao()
        val repository = PersonListRepositoryImpl.getInstance(dao, randomUserApiService)
        editPersonItemUseCase = EditPersonItemUseCase(repository)
        addPersonItemUseCase = AddPersonItemUseCase(repository)
        getPersonItemUseCase = GetPersonItemUseCase(repository)
    }

    fun addPersonItem(
        name: String?,
        surname: String?,
        birthDate: String?,
        gender: String?,
        email: String?,
        phone: String?,
        address: String?,
        rating: String?,
        imageLink: String?
    ) {
        if (validateName(
                name = parseInputString(name),
                surname = parseInputString(surname),
                birthDate = parseInputString(birthDate),
                gender = parseInputString(gender),
                email = parseInputString(email),
                phone = parseInputString(phone),
                address = parseInputString(address),
                rating = parseInputString(rating),
                imageLink = parseInputString(imageLink)
            )
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                addPersonItemUseCase.addPersonItem(
                    PersonItem(
                        name = parseInputString(name),
                        surname = parseInputString(surname),
                        birthDate = parseInputString(birthDate),
                        gender = parseGender(gender),
                        age = computeAgeFromBirthDate(parseInputString(birthDate)),
                        email = parseInputString(email),
                        phone = parseInputString(phone),
                        address = parseInputString(address),
                        rating = parseNumber(rating),
                        imageLink = parseInputString(imageLink),
                    )
                )
                _shouldCloseScreen.postValue(true)
            }

        }
    }


    fun editPersonItem(
        name: String?,
        surname: String?,
        birthDate: String?,
        gender: String?,
        email: String?,
        phone: String?,
        address: String?,
        rating: String?,
        imageLink: String?
    ) {
        if (validateName(
                name = parseInputString(name),
                surname = parseInputString(surname),
                birthDate = parseInputString(birthDate),
                gender = parseInputString(gender),
                email = parseInputString(email),
                phone = parseInputString(phone),
                address = parseInputString(address),
                rating = parseInputString(rating),
                imageLink = parseInputString(imageLink)
            )
        ) {
            val person = PersonItem(
                name = parseInputString(name),
                surname = parseInputString(surname),
                birthDate = parseInputString(birthDate),
                gender = parseGender(gender),
                age = computeAgeFromBirthDate(parseInputString(birthDate)),
                email = parseInputString(email),
                phone = parseInputString(phone),
                address = parseInputString(address),
                rating = parseNumber(rating),
                imageLink = parseInputString(imageLink),
                id = _personItem.value?.id
            )
            viewModelScope.launch(Dispatchers.IO) {
                editPersonItemUseCase.editPersonItem(person)
            }
            _shouldCloseScreen.value = true
        }
    }

    fun getPersonItem(personItemId: Int) {
        _personItem.value = getPersonItemUseCase.getPersonItem(personItemId)
    }

    private fun parseInputString(inputString: String?) = inputString?.trim() ?: ""
    private fun parseNumber(inputAge: String?) = inputAge?.trim()?.toInt() ?: -1

    private fun parseGender(gender: String?) = if (gender?.contains(Regex("[MmМм]")) == true) Gender.MALE else Gender.FEMALE
    private fun computeAgeFromBirthDate(birthdate: String?, datePattern: String = DATE_FORMAT) =
        Period.between(
            LocalDate.parse(
                birthdate,
                DateTimeFormatter.ofPattern(datePattern).withLocale(Locale.US)
            ), LocalDate.now()
        ).years

    private fun validateName(
        name: String,
        surname: String,
        birthDate: String,
        gender: String,
        email: String,
        phone: String,
        address: String,
        rating: String,
        imageLink: String
    ): Boolean {
        var isValid = true
        val localMap = _errorMap.value?.toMutableMap()

        if (name.isBlank()) {
            isValid = false
            localMap?.set(PersonErrorMessages.NAME, false)
        } else
            localMap?.set(PersonErrorMessages.NAME, true)

        if (surname.isBlank()) {
            isValid = false
            localMap?.set(PersonErrorMessages.SURNAME, false)
        } else
            localMap?.set(PersonErrorMessages.SURNAME, true)

        if (birthDate.isBlank() ||
            !birthDate.contains(Regex("^(19|20)?[0-9]{2}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$"))
        ) {
            localMap?.set(PersonErrorMessages.BIRTHDATE, false)
            isValid = false
        } else
            localMap?.set(PersonErrorMessages.BIRTHDATE, true)


        if (gender.isBlank() || !gender.contains(regex = Regex("[MmFfМмЖж]"))) {
            localMap?.set(PersonErrorMessages.GENDER, false)
            isValid = false
        } else
            localMap?.set(PersonErrorMessages.GENDER, true)

        if (email.isBlank() || !email.contains(regex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"))) {
            localMap?.set(PersonErrorMessages.EMAIL, false)
            isValid = false
        } else
            localMap?.set(PersonErrorMessages.EMAIL, true)

        if (phone.isBlank()) {
            localMap?.set(PersonErrorMessages.PHONE, false)
            isValid = false
        } else
            localMap?.set(PersonErrorMessages.PHONE, true)

        if (address.isBlank()) {
            localMap?.set(PersonErrorMessages.ADDRESS, false)
            isValid = false
        } else
            localMap?.set(PersonErrorMessages.ADDRESS, true)

        if (rating.isBlank() || rating.toIntOrNull() == null || rating.toInt() > 100) {
            localMap?.set(PersonErrorMessages.RATING, false)
            isValid = false
        } else
            localMap?.set(PersonErrorMessages.RATING, true)

        if (imageLink.isBlank()) {
            localMap?.set(PersonErrorMessages.IMAGE_LINK, false)
            isValid = false
        } else
            localMap?.set(PersonErrorMessages.IMAGE_LINK, true)
        _errorMap.postValue( localMap)
        return isValid
    }


    fun clearValidationOnField(field: PersonErrorMessages) {
        val localMap = _errorMap.value

        when (field) {
            PersonErrorMessages.NAME -> _errorMap.value?.set(PersonErrorMessages.NAME, true)
            PersonErrorMessages.SURNAME -> _errorMap.value?.set(PersonErrorMessages.SURNAME, true)
            PersonErrorMessages.BIRTHDATE -> _errorMap.value?.set(PersonErrorMessages.BIRTHDATE, true)
            PersonErrorMessages.GENDER -> _errorMap.value?.set(PersonErrorMessages.GENDER, true)
            PersonErrorMessages.EMAIL -> _errorMap.value?.set(PersonErrorMessages.EMAIL, true)
            PersonErrorMessages.PHONE-> _errorMap.value?.set(PersonErrorMessages.PHONE, true)
            PersonErrorMessages.ADDRESS-> _errorMap.value?.set(PersonErrorMessages.ADDRESS, true)
            PersonErrorMessages.RATING-> _errorMap.value?.set(PersonErrorMessages.RATING, true)
            PersonErrorMessages.IMAGE_LINK-> _errorMap.value?.set(PersonErrorMessages.IMAGE_LINK, true)
        }

        _errorMap.postValue( localMap)
    }

}