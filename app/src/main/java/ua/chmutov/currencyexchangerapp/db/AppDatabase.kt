package ua.chmutov.currencyexchangerapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.chmutov.currencyexchangerapp.db.converters.DateTimeConverter
import ua.chmutov.currencyexchangerapp.db.dao.CurrencyDao
import ua.chmutov.currencyexchangerapp.db.dao.TransactionDao
import ua.chmutov.currencyexchangerapp.db.dao.UserDao
import ua.chmutov.currencyexchangerapp.db.dao.WalletDao
import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import ua.chmutov.currencyexchangerapp.db.model.TransactionModel
import ua.chmutov.currencyexchangerapp.db.model.UserModel
import ua.chmutov.currencyexchangerapp.db.model.WalletModel

@Database(
    entities = [
        CurrencyModel::class,
        UserModel::class,
        WalletModel::class,
        TransactionModel::class
    ],
    version = 1
)
@TypeConverters(
    DateTimeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun userDao(): UserDao
    abstract fun walletDao(): WalletDao
    abstract fun transactionDao(): TransactionDao
}