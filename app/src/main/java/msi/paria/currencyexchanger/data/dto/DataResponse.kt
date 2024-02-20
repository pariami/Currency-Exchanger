package msi.paria.currencyexchanger.data.dto

class DataResponse <T>(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: T,
    val total: Int
)