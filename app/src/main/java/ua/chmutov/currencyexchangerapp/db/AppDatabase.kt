package ua.chmutov.currencyexchangerapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.chmutov.currencyexchangerapp.db.converters.DateTimeConverter
import ua.chmutov.currencyexchangerapp.db.dao.CurrencyDao
import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel

@Database(entities = [CurrencyModel::class], version = 1)
@TypeConverters(
    DateTimeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}