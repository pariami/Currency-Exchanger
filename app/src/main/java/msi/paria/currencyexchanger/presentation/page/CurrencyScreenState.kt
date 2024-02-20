package msi.paria.currencyexchanger.presentation.page

import msi.paria.currencyexchanger.data.dto.CurrencyResponse
import msi.paria.currencyexchanger.util.Resource

data class CurrencyScreenState(
    val currencyResponse: Resource<CurrencyResponse> = Resource.Empty(),
    val rates : Map<String, Double> = emptyMap(),
    val ratesList : List<String> = emptyList(),
    val amount: String = "",
    val fromCurrency: String = "EUR",
    val toCurrency:String = "EUR",
    val commissionFee: Double = 0.0,
    val convertedAmount: Double = 0.0,
    val message:String = "") {
}