package msi.paria.currencyexchanger.util

import msi.paria.currencyexchanger.data.dto.CurrencyResponse

sealed class Resource<T>(val data: T?, val message: String?){
    class Success<T>(data: T): Resource<T>(data, null)
    class Error<T>(message: String?): Resource<T>(null, message)
    class Empty<T>(): Resource<T>(null, null)
}