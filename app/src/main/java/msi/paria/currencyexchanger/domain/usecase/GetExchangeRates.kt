package msi.paria.currencyexchanger.domain.usecase

import msi.paria.currencyexchanger.domain.repository.ExchangeRepository

class GetExchangeRates(private val exchangeRepository: ExchangeRepository) {
    suspend operator fun invoke(base: String) = exchangeRepository.getExchangeRates(base)
}