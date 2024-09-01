package id.rettorio.luxmarket.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import id.rettorio.luxmarket.model.Product
import id.rettorio.luxmarket.model.discountPrice
import id.rettorio.luxmarket.ui.theme.discountPrice
import id.rettorio.luxmarket.ui.theme.oldPrice
import id.rettorio.luxmarket.ui.theme.saleAccent

@Composable
fun ProductCard(
    item: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onFavorite: (Product) -> Unit,
    isFavorite: Boolean
) {
    Column(modifier = modifier.width(154.dp)) {
        Card(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(154.dp),
            shape = RoundedCornerShape(18.dp),
        ) {
            ConstraintLayout {
                val (favoriteButton, productImage) = createRefs()

                FavoriteButton(
                    isFavorite = isFavorite,
                    onClick = { onFavorite(item) },
                    modifier = Modifier.constrainAs(favoriteButton) {
                        end.linkTo(parent.end, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                    }
                )

                if(item.images.medium != null) {
                    Image(
                        painter = painterResource(item.images.medium),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(productImage) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }

            }
        }
        Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            if(item.discount != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("$" + item.discountPrice().toString(), style = discountPrice, color = saleAccent)
                    Text("$" + item.price.toString(), style = oldPrice)
                }
            } else {
                Text("$" + item.price.toString(), style = MaterialTheme.typography.titleLarge)
            }
            Text(item.name, style = MaterialTheme.typography.titleSmall)
            Text(item.subtitle ?: "-", style = MaterialTheme.typography.labelSmall ,color = MaterialTheme.colorScheme.tertiary)
        }
    }
}
