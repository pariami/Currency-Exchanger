package msi.paria.currencyexchanger.data.repository

import msi.paria.currencyexchanger.data.datasource.db.TransactionDB
import msi.paria.currencyexchanger.data.datasource.db.TransactionDao
import msi.paria.currencyexchanger.data.dto.db.Transaction
import msi.paria.currencyexchanger.domain.repository.TransactionRepository

class TransactionRepositoryImp(val transactionDb: TransactionDB): TransactionRepository {
    override suspend fun getTransactions(): List<Transaction> {
        return transactionDb.transactionDao().getTransactions()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDb.transactionDao().insert(transaction)
    }
}