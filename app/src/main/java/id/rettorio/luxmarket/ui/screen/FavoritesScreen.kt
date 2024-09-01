package id.rettorio.luxmarket.ui.screen

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
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.rettorio.luxmarket.R
import id.rettorio.luxmarket.model.Product
import id.rettorio.luxmarket.model.discountPrice
import id.rettorio.luxmarket.ui.theme.discountPrice
import id.rettorio.luxmarket.ui.theme.oldPrice
import id.rettorio.luxmarket.ui.theme.saleAccent
import id.rettorio.luxmarket.ui.vm.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val data = uiState.products
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Favorites", style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(Modifier.height(20.dp))
        if(data.isNotEmpty()) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(data, key = { it.hashCode() }) { item ->
                    HorizontalProduct(
                        item = item
                    ) {
                        Button(
                            onClick = { viewModel.cartSwitch(item) },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 6.dp),
                            modifier = Modifier.size(32.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if(uiState.cartIds.contains(item.id)) MaterialTheme.colorScheme.primaryContainer  else  Color.Transparent,
                                contentColor = if(uiState.cartIds.contains(item.id)) Color.White else MaterialTheme.colorScheme.primary ,
                            )
                        ) {
                            Icon(Icons.Outlined.ShoppingCart, null)
                        }
                        Spacer(Modifier.width(6.dp))
                        Button(
                            onClick = { viewModel.unFavorite(item)},
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Icon(painterResource(R.drawable.baseline_remove_24), null)
                        }
                    }
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No product here!", style = MaterialTheme.typography.headlineSmall)
                Text("Start adding product")
            }
        }
    }
}

@Composable
fun HorizontalProduct(
    item: Product,
    trailingButton: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp),
            shape = RoundedCornerShape(18.dp)
        ) {
            Image(painterResource(item.images.medium ?: R.drawable.medium_shure_microphone), contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        Spacer(Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .padding(4.dp)
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            if(item.discount != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("$" + item.discountPrice().toString(), style = discountPrice, color = saleAccent)
                    Text("$" + item.price.toString(), style = oldPrice)
                }
            } else {
                Text("$" + item.discountPrice().toString(), style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.height(3.dp))
            Text(item.name, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(3.dp))
            Text(item.subtitle ?: "-", style = MaterialTheme.typography.labelSmall ,color = MaterialTheme.colorScheme.tertiary)
        }
        Spacer(Modifier.width(12.dp))
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp)
        ) {
            trailingButton()
        }
    }
}