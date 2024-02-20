package msi.paria.currencyexchanger.domain.usecase

import msi.paria.currencyexchanger.data.dto.db.Transaction
import msi.paria.currencyexchanger.domain.repository.TransactionRepository

class InsertTransaction(val transactionRepository: TransactionRepository){
    suspend operator fun invoke(transaction: Transaction){
        transactionRepository.insertTransaction(transaction)
    }
}