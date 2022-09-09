package ua.chmutov.currencyexchangerapp.network

import retrofit2.Response
import retrofit2.http.GET
import ua.chmutov.currencyexchangerapp.network.model.CurrencyRatesResponse

interface CurrencyRatesApi {

    @GET ("/currency")
    suspend fun getCurrencyRates () : Response<CurrencyRatesResponse>

}