package id.rettorio.luxmarket.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import id.rettorio.luxmarket.data.Application
import id.rettorio.luxmarket.model.discountPrice
import id.rettorio.luxmarket.ui.component.CartButton
import id.rettorio.luxmarket.ui.component.ComponentSize
import id.rettorio.luxmarket.ui.component.FavoriteButton
import id.rettorio.luxmarket.ui.theme.LuxmarketTheme
import id.rettorio.luxmarket.ui.theme.oldPriceLarge
import id.rettorio.luxmarket.ui.theme.saleAccent
import id.rettorio.luxmarket.ui.vm.ProductViewModel

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val product = uiState.product
//    val scope = rememberCoroutineScope()

    val productDescription = if(product.description == null) "-" else stringResource(product.description)

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(356.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            ConstraintLayout {
                val (productImages, favoriteButton, cartButton) = createRefs()
                if (product.images.large != null) {
                    Image(
                        painterResource(product.images.large),
                        null,
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(productImages) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }

                FavoriteButton(
                    onClick = viewModel::favoriteSwitch,
                    isFavorite = uiState.onFavorite,
                    size = ComponentSize.BIG,
                    modifier = Modifier.constrainAs(favoriteButton) {
                        end.linkTo(parent.end, 12.dp)
                        bottom.linkTo(cartButton.top, 6.dp)
                    }
                )

                CartButton(
                    onClick = { viewModel.cartSwitch(uiState.onCart) },
                    isInCart = uiState.onCart,
                    size = ComponentSize.BIG,
                    modifier = Modifier.constrainAs(cartButton) {
                        end.linkTo(parent.end, 12.dp)
                        bottom.linkTo(parent.bottom, 12.dp)
                    }
                )

            }
        }
        Spacer(Modifier.height(8.dp))
        Column(modifier = Modifier.padding(horizontal = 4.dp)) {
            if(product.discount != null)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("$" + product.discountPrice().toString(), style = MaterialTheme.typography.headlineLarge, color = saleAccent)
                    Text("$" + product.price.toString(), style = oldPriceLarge)
                } else {
                Text("$" + product.price.toString(), style = MaterialTheme.typography.headlineLarge)
            }
            Text(product.name, style = MaterialTheme.typography.headlineSmall, lineHeight = 22.sp)
            Spacer(Modifier.height(2.dp))
            Text(product.subtitle ?: "-", style = MaterialTheme.typography.labelLarge ,color = MaterialTheme.colorScheme.tertiaryContainer)
            Spacer(Modifier.height(16.dp))
            Text(productDescription, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.tertiaryContainer)
        }
    }
}

@Preview
@Composable
fun ProductPreview() {

    val product = Application.products[2]
    var isFavorite by remember { mutableStateOf(false) }
    var isInCart by remember { mutableStateOf(false) }
    val productDescription = if(product.description != null) stringResource(product.description) else "-"

    LuxmarketTheme {
        Scaffold(
            topBar = {
                BrowseAppBar(
                    searchQuery = product.type
                )
            },
            modifier = Modifier.height(800.dp)
        ) {innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
            ) {
                Spacer(Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(356.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    ConstraintLayout {
                        val (productImages, favoriteButton, cartButton) = createRefs()
                        if (product.images.large != null) {
                            Image(
                                painterResource(product.images.large),
                                null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .constrainAs(productImages) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                            )
                        }

                        FavoriteButton(
                            onClick = { isFavorite = !isFavorite },
                            isFavorite = isFavorite,
                            size = ComponentSize.BIG,
                            modifier = Modifier.constrainAs(favoriteButton) {
                                end.linkTo(parent.end, 12.dp)
                                bottom.linkTo(cartButton.top, 6.dp)
                            }
                        )

                        CartButton(
                            onClick = { isInCart = !isInCart },
                            isInCart = isInCart,
                            size = ComponentSize.BIG,
                            modifier = Modifier.constrainAs(cartButton) {
                                end.linkTo(parent.end, 12.dp)
                                bottom.linkTo(parent.bottom, 12.dp)
                            }
                        )

                    }
                }
                Spacer(Modifier.height(8.dp))
                Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                    if(product.discount != null)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("$" + product.discountPrice().toString(), style = MaterialTheme.typography.headlineLarge, color = saleAccent)
                        Text("$" + product.price.toString(), style = oldPriceLarge)
                    } else {
                        Text("$" + product.price.toString(), style = MaterialTheme.typography.headlineLarge)
                    }
                    Text(product.name, style = MaterialTheme.typography.headlineSmall, lineHeight = 22.sp)
                    Spacer(Modifier.height(2.dp))
                    Text(product.subtitle ?: "-", style = MaterialTheme.typography.labelLarge ,color = MaterialTheme.colorScheme.tertiaryContainer)
                    Spacer(Modifier.height(16.dp))
                    Text(productDescription, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.tertiaryContainer)
                }
            }
        }

    }
}