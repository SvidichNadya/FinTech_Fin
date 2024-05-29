package ru.tinkoff.favouritepersons.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.tinkoff.favouritepersons.domain.PersonItem

@Database(entities = [PersonItem::class], version = 3, exportSchema = false)
abstract class PersonDataBase : RoomDatabase() {

    abstract fun personsDao() : PersonDao

    companion object {

        @Volatile
        private var INSTANCE: PersonDataBase? = null

        fun getDBClient(context: Context) : PersonDataBase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, PersonDataBase::class.java, "person_database.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                return INSTANCE!!

            }
        }

    }

}