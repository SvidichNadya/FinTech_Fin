package ru.tinkoff.favouritepersons.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class PersonItem(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "surname")
    val surname: String?,

    @ColumnInfo(name = "birthdate")
    val birthDate: String?,

    @ColumnInfo(name = "gender")
    val gender: Gender?,

    @ColumnInfo(name = "age")
    val age: Int?,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "rating")
    val rating: Int,

    @ColumnInfo(name = "image_link")
    val imageLink: String? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

)

const val DATE_FORMAT = "yyyy-MM-dd"

enum class Gender {
    MALE,
    FEMALE;

    override fun toString(): String {
        return when(this) {
            MALE -> "Male"
            FEMALE -> "Female"
        }
    }
}


enum class SCORES (val range : IntRange) {
    FAIL (1..24),
    BAD(25..49),
    NORMAL(50..79),
    PERFECT(80..100)
}
