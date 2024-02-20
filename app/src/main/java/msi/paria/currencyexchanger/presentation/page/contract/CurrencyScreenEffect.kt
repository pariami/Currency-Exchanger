package msi.paria.currencyexchanger.presentation.page.contract

sealed class CurrencyScreenEffect {
    data class ShowResult(val message: String): CurrencyScreenEffect()
    data object OnRatesReceived: CurrencyScreenEffect()
}