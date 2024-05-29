package ru.tinkoff.favouritepersons.presentation

enum class PersonFields(val field: String){
    BY_SURNAME("surname"),
    BY_RATING("rating"),
    BY_AGE("age"),
    NO_METHOD("id")
}

enum class PersonErrorMessages(val errorMessage: String) {
    NAME("Поле должно быть заполнено!"),
    SURNAME("Поле должно быть заполнено!"),
    BIRTHDATE("Поле должно быть заполнено в формате 1990-12-31"),
    GENDER(("Поле должно быть заполнено буквами М или Ж")),
    EMAIL("Поле должно быть заполнено в формате mail@gmail.com"),
    PHONE("Поле должно быть заполнено!"),
    ADDRESS("Поле должно быть заполнено!"),
    RATING("Поле должно быть заполнено двузначным числом"),
    IMAGE_LINK("Поле должно быть заполнено!")
}