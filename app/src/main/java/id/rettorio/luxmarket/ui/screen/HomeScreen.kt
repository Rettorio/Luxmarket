package id.rettorio.luxmarket.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.rettorio.luxmarket.data.Application
import id.rettorio.luxmarket.model.Product
import id.rettorio.luxmarket.model.discountPrice
import id.rettorio.luxmarket.ui.component.FavoriteButton
import id.rettorio.luxmarket.ui.component.LazyCarousel
import id.rettorio.luxmarket.ui.component.ProductCard
import id.rettorio.luxmarket.ui.theme.discountPrice
import id.rettorio.luxmarket.ui.theme.oldPrice
import id.rettorio.luxmarket.ui.theme.primaryLight
import id.rettorio.luxmarket.ui.theme.saleAccent
import id.rettorio.luxmarket.ui.vm.HomeViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onClickProduct: (Int) -> Unit,
    browseTag: List<String> = Application.browseTags
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val productData = uiState.products
    val highlightProducts = uiState.highlightProduct
    val user = viewModel.repository.getSelectedUser()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Text("Hello ${user.name.split(" ").first()}", style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(4.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            itemsIndexed(browseTag) { i,tag ->
                if(i > 0) {
                    Text(tag, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.tertiary)
                } else {
                    Text(tag, style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.drawBehind {
                            val strokeWidthPx = 2.5.dp.toPx()
                            val verticalOffset = size.height + 5.sp.toPx()
                            drawLine(
                                color = primaryLight,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Deals of the day", style = MaterialTheme.typography.headlineSmall)
            Text("See all", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
        }
        LazyCarousel(items = highlightProducts) { item ->
            CarouselCard(
                item = item,
                onFavorite = viewModel::favoriteSwitch,
                onClick = { onClickProduct(item.id) },
                modifier = Modifier
                    .height(155.dp)
                    .width(312.dp),
                isFavorite = uiState.favoriteIds.contains(item.id)
            )
        }
        Text("Recommended for you", style = MaterialTheme.typography.headlineSmall)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(
                productData,
                key = { it.id }
            ) { product ->
                ProductCard(
                    item = product,
                    onFavorite = viewModel::favoriteSwitch,
                    isFavorite = uiState.favoriteIds.contains(product.id),
                    onClick = { onClickProduct(product.id) }
                )
            }
        }
    }
}


@Composable
fun CarouselCard(
    modifier: Modifier = Modifier,
    item: Product,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavorite: (Product) -> Unit
) {
   Card(
       modifier = modifier,
       shape = RoundedCornerShape(16.dp)
   ) {
       ConstraintLayout {
           val (productImage, productDesc, favoriteButton) = createRefs()

           //productImage
           if(item.images.medium != null) {
               Image(
                   painterResource(item.images.medium),
                   contentDescription = null,
                   contentScale = ContentScale.FillHeight,
                   modifier = Modifier
                       .constrainAs(productImage) {
                           start.linkTo(parent.start)
                           top.linkTo(parent.top)
                           bottom.linkTo(parent.bottom)
                       }
                       .fillMaxHeight()
               )
           } else {
               Image(
                   painterResource(item.images.large!!),
                   contentDescription = null,
                   contentScale = ContentScale.FillHeight,
                   modifier = Modifier
                       .constrainAs(productImage) {
                           start.linkTo(parent.start)
                           top.linkTo(parent.top)
                           bottom.linkTo(parent.bottom)
                       }
                       .fillMaxHeight()
               )
           }

           FavoriteButton(
               isFavorite = isFavorite,
               onClick = { onFavorite(item) },
               modifier = Modifier.constrainAs(favoriteButton) {
                   end.linkTo(parent.end, 6.dp)
                   top.linkTo(parent.top, 6.dp)
               }
           )

           Column(
               modifier = Modifier
                   .fillMaxWidth(.5f)
                   .clickable { onClick() }
                   .constrainAs(productDesc) {
                       top.linkTo(parent.top, 24.dp)
                       start.linkTo(productImage.end, 4.dp)
                       bottom.linkTo(parent.bottom, 24.dp)
                   }
           ) {
               Text(item.type, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
               Spacer(Modifier.height(8.dp))
               Column(modifier = Modifier.fillMaxWidth(.9f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                   Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                       Text("$" + item.discountPrice().toString(), style = discountPrice, color = saleAccent)
                       Text("$" + item.price.toString(), style = oldPrice)
                   }
                   Text(item.name, style = MaterialTheme.typography.titleSmall)
                   if(item.subtitle != null) {
                       Text(item.subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.tertiary)
                   }
               }
           }
       }
   }
}









