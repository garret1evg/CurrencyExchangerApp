package ua.chmutov.currencyexchangerapp.db.dao

import androidx.room.*
import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import kotlinx.coroutines.flow.Flow
import ua.chmutov.currencyexchangerapp.db.model.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<UserModel>>

    @Query("SELECT * FROM user LIMIT 1")
    fun getFirstUser(): Flow<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserModel>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserModel)

    @Update
    suspend fun updateUser(user: UserModel)

}