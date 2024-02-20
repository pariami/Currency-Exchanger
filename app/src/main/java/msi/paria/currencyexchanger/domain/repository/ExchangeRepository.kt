package msi.paria.currencyexchanger.domain.repository

import msi.paria.currencyexchanger.data.dto.CurrencyResponse
import msi.paria.currencyexchanger.util.Resource

interface ExchangeRepository {
    suspend fun getExchangeRates(base:String): Resource<CurrencyResponse>
}