package msi.paria.currencyexchanger.presentation.page

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import msi.paria.currencyexchanger.R
import msi.paria.currencyexchanger.data.dto.db.Transaction
import msi.paria.currencyexchanger.domain.usecase.UseCase
import msi.paria.currencyexchanger.presentation.page.contract.CurrencyScreenEffect
import msi.paria.currencyexchanger.presentation.page.contract.CurrencyScreenEvent
import msi.paria.currencyexchanger.util.Resource
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    val usecase: UseCase, @ApplicationContext val context: Context
) : ViewModel() {

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    private val _state = MutableStateFlow(CurrencyScreenState())
    val state: StateFlow<CurrencyScreenState> = _state.asStateFlow()

    private val _effectFlow = MutableSharedFlow<CurrencyScreenEffect>(replay = 1)
    val effectFlow = _effectFlow.asSharedFlow()

    init {
        _state.update { state ->
            state.copy(
                ratesList = context.resources.getStringArray(R.array.currency_codes).toList()
            )
        }

        startFetchingData()


    }

    fun onEvent(event: CurrencyScreenEvent) {
        when (event) {
            is CurrencyScreenEvent.OnFromCurrencySelected -> {
                _state.update { state -> state.copy(fromCurrency = event.currency) }
            }

            CurrencyScreenEvent.OnSubmitButtonClicked -> {
                getTransactions()
            }

            is CurrencyScreenEvent.OnToCurrencySelected -> {
                _state.update { state -> state.copy(toCurrency = event.currency) }
            }

            is CurrencyScreenEvent.OnAmountValueEntered -> {
                _state.update { state -> state.copy(amount = event.amount) }
            }
        }
    }

    private fun startFetchingData() {
        // Start a coroutine for fetching data every 5 seconds
        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = CurrencyEvent.Loading

            while (true) {
                usecase.getExchangeRates(_state.value.fromCurrency).also {
                    _state.update { state ->
                        state.copy(currencyResponse = it)
                    }
                    when (it) {
                        is Resource.Error -> _conversion.value = CurrencyEvent.Failure(it.message!!)

                        is Resource.Success -> {
                            val rates = it.data!!.rates
                            _state.update { state ->
                                state.copy(rates = rates)
                            }
                            _effectFlow.emit(CurrencyScreenEffect.OnRatesReceived)

                        }

                        else -> {
                            _conversion.value = CurrencyEvent.Failure("Unexpected error")
                        }
                    }
                }
                delay(5000) // Delay for 5 seconds
            }
        }
    }

    private fun getTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val transactions = usecase.getTransactions()
            Log.d("MainViewModel", "getTransactions:size: " + transactions.size)
            if (transactions.size < 5) {
                _state.update { state -> state.copy(commissionFee = 0.0) }
            } else {
                _state.update { state -> state.copy(commissionFee = 0.7) }
            }
            convert(
                _state.value.amount, _state.value.fromCurrency, _state.value.toCurrency
            )
        }
    }

    fun convert(
        amountStr: String, fromCurrency: String, toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val ratesResponse = _state.value.currencyResponse
            if (ratesResponse is Resource.Success) {
                val rates = _state.value.rates
                val rate: Double = getRateForCurrency(toCurrency, rates) as Double
                if (rate == null) {
                    _conversion.value = CurrencyEvent.Failure("Unexpected error")
                } else {
                    val convertedCurrency = round(fromAmount * rate * 100) / 100
                    _conversion.value = CurrencyEvent.Success(
                        "$fromAmount $fromCurrency = $convertedCurrency $toCurrency // ${(_state.value.commissionFee * _state.value.amount.toDouble() * 100) / 100} $fromCurrency commission fee"
                    )
                    _state.update { state -> state.copy(convertedAmount = convertedCurrency) }
                    insertTransaction()
                    Log.d("MainViewModel", "convert: " + _conversion.value)
                    _state.update { state -> state.copy(message = _conversion.value.toString()) }
                }
            }

        }
    }

    private suspend fun insertTransaction() {
        val transaction = Transaction(
            amount = _state.value.amount.toDouble(),
            fromCurrency = _state.value.fromCurrency,
            toCurrency = _state.value.toCurrency,
            convertedAmount = _state.value.convertedAmount,
            commissionFee = (_state.value.commissionFee * _state.value.amount.toDouble() * 100) / 100
        )
        usecase.insertTransaction(transaction)
        Log.d("MainViewModel", "getTransactions:commissionFee: " + transaction.commissionFee)
        Log.d("MainViewModel", "getTransactions:convertedAmount: " + transaction.convertedAmount)

    }

    fun getRateForCurrency(currency: String, rates: Map<String, Double>): Double? =
        when (currency) {
            "USD" -> rates["USD"]
            "AUD" -> rates["AUD"]
            "BGN" -> rates["BGN"]
            "BRL" -> rates["BRL"]
            "CAD" -> rates["CAD"]
            "CHF" -> rates["CHF"]
            "CNY" -> rates["CNY"]
            "CZK" -> rates["CZK"]
            "DKK" -> rates["DKK"]
            "EUR" -> rates["EUR"]
            "GBP" -> rates["GBP"]
            "HKD" -> rates["HKD"]
            "HRK" -> rates["HRK"]
            "HUF" -> rates["HUF"]
            "IDR" -> rates["IDR"]
            "ILS" -> rates["ILS"]
            "INR" -> rates["INR"]
            "ISK" -> rates["ISK"]
            "JPY" -> rates["JPY"]
            "KRW" -> rates["KRW"]
            "MXN" -> rates["MXN"]
            "MYR" -> rates["MYR"]
            "NOK" -> rates["NOK"]
            "NZD" -> rates["NZD"]
            "PHP" -> rates["PHP"]
            "PLN" -> rates["PLN"]
            "RON" -> rates["RON"]
            "RUB" -> rates["RUB"]
            "SEK" -> rates["SEK"]
            "SGD" -> rates["SGD"]
            "THB" -> rates["THB"]
            "TRY" -> rates["TRY"]
            "ZAR" -> rates["ZAR"]
            else -> 0.0
        }
}