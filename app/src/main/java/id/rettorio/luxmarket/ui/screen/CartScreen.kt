package id.rettorio.luxmarket.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.rettorio.luxmarket.R
import id.rettorio.luxmarket.model.toProduct
import id.rettorio.luxmarket.ui.theme.InterFont
import id.rettorio.luxmarket.ui.vm.CartViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartItems = uiState.cartItems
    val paymentMethod: List<Painter> = listOf(
        painterResource(R.drawable.paypal),
        painterResource(R.drawable.visa),
        painterResource(R.drawable.mastercard),
        painterResource(R.drawable.gpay),
        painterResource(R.drawable.apple_pay),
        painterResource(R.drawable.amex)
    )

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight(.75f)
        ) {
        Spacer(Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Cart", style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cartItems, key = { it.hashCode() }) {item ->
                HorizontalProduct(item = item.toProduct()) {
                    Button(
                        onClick = { viewModel.decQtyCartItem(item) },
                        enabled = item.qty > 1,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp),
                        modifier = Modifier.size(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Icon(painterResource(R.drawable.baseline_remove_24), null)
                    }
                    Spacer(Modifier.width(6.dp))
                    Text(item.qty.toString(), fontSize = 18.sp, fontFamily = InterFont, fontWeight = FontWeight.Normal)
                    Spacer(Modifier.width(6.dp))
                    Button(
                        onClick = { viewModel.incQtyCartItem(item)},
                        enabled = item.toProduct().stock > 1,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp),
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.Add, null)
                    }
                }
            }
        }
    }
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(thickness = 1.dp)
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Shipping", style = MaterialTheme.typography.titleSmall)
                    Text("$${uiState.shippingCost}", style = MaterialTheme.typography.titleSmall)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total", style = MaterialTheme.typography.titleSmall)
                    Text("$${uiState.totalPrice}", style = MaterialTheme.typography.titleSmall)
                }
                Button(
                    onClick = { viewModel.checkoutSwitch(uiState.isCheckout) },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Checkout")
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    paymentMethod.forEach {img ->
                        OutlinedCard(
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(.2.dp, MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Image(
                                img,
                                null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(5.dp)
                                    .width(42.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if(uiState.isCheckout) {
        CartDialog(
            onDismiss = { viewModel.checkoutSwitch(true) },
            onConfirm = viewModel::confirmCheckout,
            totalItem = uiState.cartItems.size,
            totalPrice = uiState.totalPrice
        )
    }
}

@Composable
fun CartDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    totalItem: Int,
    totalPrice: Double
) {
    AlertDialog(
        modifier = modifier,
        icon = {
           Icon(Icons.Outlined.CheckCircle, null)
        },
        text = {
           Text("Checkout $totalItem item with $$totalPrice  in total ?", style = MaterialTheme.typography.headlineSmall)
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}