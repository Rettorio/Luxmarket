package id.rettorio.luxmarket.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.rettorio.luxmarket.R

@Preview
@Composable
fun BrowseDetailDialog(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = { },
    onCancel: () -> Unit = { },
    searchQuery: String = "Sony"
) {
    var stringQuery by remember { mutableStateOf(searchQuery) }

    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Box(modifier = Modifier.size(24.dp)) {
                Icon(painterResource(R.drawable.baseline_travel_explore_24), null, modifier = Modifier.fillMaxSize())
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSearch(stringQuery) },
                enabled = stringQuery.isNotEmpty() || stringQuery == searchQuery
            ) {
                Text("Search")
            }
        },
        dismissButton = {
            TextButton(onClick =  onCancel ) {
                Text("Cancel")
            }
        },
        title = {
            Text(
                "Search Product",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            OutlinedTextField(
                value = stringQuery,
                onValueChange = { stringQuery = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions(onSearch = { onSearch(stringQuery) } ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                )
            )
        }
    )

}