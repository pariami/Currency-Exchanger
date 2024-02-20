package msi.paria.currencyexchanger.data.dto

class BaseResponse<T>(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: DataResponse<T>,
    val status: String
)