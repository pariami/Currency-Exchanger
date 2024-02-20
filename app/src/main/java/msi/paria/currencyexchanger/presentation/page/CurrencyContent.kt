package msi.paria.currencyexchanger.presentation.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import msi.paria.currencyexchanger.presentation.page.component.CurrencySpinner

@Composable
fun CurrencyContent(
    onAmountValueEntered: (String) -> Unit,
    onFromCurrencySelected: (currency: String) -> Unit,
    onToCurrencySelected: (currency: String) -> Unit,
    onSubmitButtonClicked: () -> Unit,
    rates: List<String> = emptyList(),
    message: String
) {

    var selectedFromCurrencyIndex by remember { mutableStateOf(0) }
    var selectedToCurrencyIndex by remember { mutableStateOf(0) }

    var inputAmount by remember { mutableStateOf(TextFieldValue("")) }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(16.dp)) {
        TextField(value = inputAmount, onValueChange = { text ->
            inputAmount = text
            onAmountValueEntered(text.text)
        })
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)){
            CurrencySpinner(currencyCodes = rates,
                selectedCurrency = rates[selectedFromCurrencyIndex],
                onCurrencySelected = { index, currency ->
                    onFromCurrencySelected(currency)
                    selectedFromCurrencyIndex = index
                })
            CurrencySpinner(currencyCodes = rates,
                selectedCurrency = rates[selectedToCurrencyIndex],
                onCurrencySelected = { index, currency ->
                    onToCurrencySelected(currency)
                    selectedToCurrencyIndex = index
                },
               )
        }
        Button(onClick = { onSubmitButtonClicked() }) {
            Text(text = "Submit")
        }
        Text(text = message)

    }
}