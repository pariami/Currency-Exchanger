package msi.paria.currencyexchanger.domain.usecase

import msi.paria.currencyexchanger.data.dto.db.Transaction
import msi.paria.currencyexchanger.domain.repository.TransactionRepository

class GetTransactions(val transactionRepository: TransactionRepository){
    suspend operator fun invoke(): List<Transaction> {
        return transactionRepository.getTransactions()
    }
}