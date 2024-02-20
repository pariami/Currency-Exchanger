package msi.paria.currencyexchanger.data.datasource.api

import msi.paria.currencyexchanger.data.dto.BaseResponse
import msi.paria.currencyexchanger.data.dto.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("currency-exchange-rates")
    suspend fun getExchangeRates(@Query("base") base:String): Response<CurrencyResponse>
}