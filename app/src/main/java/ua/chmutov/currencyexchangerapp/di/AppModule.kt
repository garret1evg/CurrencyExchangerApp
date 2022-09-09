package ua.chmutov.currencyexchangerapp.di


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.chmutov.currencyexchangerapp.db.AppDatabase
import ua.chmutov.currencyexchangerapp.db.LocalDataSource
import ua.chmutov.currencyexchangerapp.network.CurrencyRatesApi
import ua.chmutov.currencyexchangerapp.network.NetworkDataSource
import ua.chmutov.currencyexchangerapp.repository.MainRepository
import javax.inject.Singleton

private const val CURRENCY_RATES_URL = "https://6319aede8e51a64d2be9aa01.mockapi.io/api/v1/"
private const val DATABASE_NAME = "app-db"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyRatesApi = Retrofit.Builder()
        .baseUrl(CURRENCY_RATES_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyRatesApi::class.java)

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        currencyApi: CurrencyRatesApi,
    ): NetworkDataSource =
        NetworkDataSource(currencyApi)

    @Singleton
    @Provides
    fun provideAccountDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideLocalDataSource(
        appDatabase: AppDatabase
    ): LocalDataSource = LocalDataSource(
        appDatabase
    )

    @Singleton
    @Provides
    fun provideMainRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource
    ) = MainRepository(networkDataSource, localDataSource)

}