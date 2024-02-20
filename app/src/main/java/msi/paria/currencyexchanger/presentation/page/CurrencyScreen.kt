package msi.paria.currencyexchanger.presentation.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import msi.paria.currencyexchanger.presentation.page.contract.CurrencyScreenEffect
import msi.paria.currencyexchanger.presentation.page.contract.CurrencyScreenEvent

@Composable
fun CurrencyScreen() {
    val viewModel: MainViewModel = viewModel()

    val state: CurrencyScreenState by viewModel.state.collectAsState()

    val showScreen = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel) {
        viewModel.effectFlow.collect { event ->
            when (event) {
                is CurrencyScreenEffect.OnRatesReceived -> {
                    showScreen.value = true
                }

                is CurrencyScreenEffect.ShowResult -> TODO()
            }
        }
    }
        CurrencyContent(
            onAmountValueEntered = { viewModel.onEvent(CurrencyScreenEvent.OnAmountValueEntered(it)) },
            onFromCurrencySelected = {
                viewModel.onEvent(
                    CurrencyScreenEvent.OnFromCurrencySelected(it)
                )
            },
            onToCurrencySelected = { viewModel.onEvent(CurrencyScreenEvent.OnToCurrencySelected(it)) },
            onSubmitButtonClicked = { viewModel.onEvent(CurrencyScreenEvent.OnSubmitButtonClicked) },
            if (state.rates.isEmpty()) state.ratesList else state.rates.keys.toList(),
            state.message
        )

}