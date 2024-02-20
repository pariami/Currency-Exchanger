package msi.paria.currencyexchanger.data.repository

import msi.paria.currencyexchanger.data.datasource.api.ApiService
import msi.paria.currencyexchanger.data.dto.CurrencyResponse
import msi.paria.currencyexchanger.domain.repository.ExchangeRepository
import msi.paria.currencyexchanger.util.Resource
import javax.inject.Inject

class ExchangeRepositoryImp @Inject constructor(
    val apiService: ApiService
) : ExchangeRepository {
    override suspend fun getExchangeRates(base: String): Resource<CurrencyResponse> {
        try {
            val response = apiService.getExchangeRates(base)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                return Resource.Success(result)
            } else {
                return Resource.Error(response.message())
            }
        } catch (e: Exception) {
            return Resource.Error(e.message)
        }
    }
}