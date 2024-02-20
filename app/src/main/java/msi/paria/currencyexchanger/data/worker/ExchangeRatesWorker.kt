package msi.paria.currencyexchanger.data.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import msi.paria.currencyexchanger.domain.repository.ExchangeRepository
import msi.paria.currencyexchanger.util.Resource

// ExchangeRatesWorker.kt
class ExchangeRatesWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: ExchangeRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            val result = runBlocking { repository.getExchangeRates("USD") } // Example: Base currency is USD
            if (result is Resource.Success) {
                // Handle successful response
                // You may want to update your UI or perform any necessary operations
            } else if (result is Resource.Error) {
                // Handle error
                Log.e("ExchangeRatesWorker", "Error: ${result.message}")
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("ExchangeRatesWorker", "Error: ${e.message}")
            Result.failure()
        }
    }
}
