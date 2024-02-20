package msi.paria.currencyexchanger.data.dto

import kotlinx.coroutines.flow.Flow

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
