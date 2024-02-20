package msi.paria.currencyexchanger.domain.repository

import msi.paria.currencyexchanger.data.dto.db.Transaction

interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
    suspend fun insertTransaction(transaction: Transaction)
}