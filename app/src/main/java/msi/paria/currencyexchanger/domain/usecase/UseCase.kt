package msi.paria.currencyexchanger.domain.usecase

import javax.inject.Inject

data class UseCase @Inject constructor(
    val getExchangeRates: GetExchangeRates,
    val insertTransaction: InsertTransaction,
    val getTransactions: GetTransactions
)