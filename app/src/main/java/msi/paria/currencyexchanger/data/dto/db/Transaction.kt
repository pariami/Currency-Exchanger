package msi.paria.currencyexchanger.data.dto.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "fromCurrency")
    val fromCurrency: String,
    @ColumnInfo(name = "toCurrency")
    val toCurrency: String,
    @ColumnInfo(name = "convertedAmount")
    val convertedAmount: Double,
    @ColumnInfo(name = "commissionFee")
    val commissionFee: Double
)