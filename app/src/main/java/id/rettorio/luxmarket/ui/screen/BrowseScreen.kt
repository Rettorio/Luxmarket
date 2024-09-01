package id.rettorio.luxmarket.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.rettorio.luxmarket.R
import id.rettorio.luxmarket.ui.component.ProductCard
import id.rettorio.luxmarket.ui.theme.appBarTitle
import id.rettorio.luxmarket.ui.theme.textFieldLabel
import id.rettorio.luxmarket.ui.theme.textFieldStyle
import id.rettorio.luxmarket.ui.vm.BrowseDetailViewModel

@Composable
fun BrowseScreen(
    modifier: Modifier = Modifier,
    onClickTag: (String) -> Unit = {},
    tagList: List<String>
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Spacer(Modifier.height(28.dp))
        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            singleLine = true,
            textStyle = textFieldStyle,
            decorationBox = {innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (searchQuery.isEmpty()) {
                            Text("Search", style = textFieldLabel)
                        }
                        innerTextField()
                    }
                    Icon(imageVector = Icons.Outlined.Search, null, tint = MaterialTheme.colorScheme.tertiaryContainer)
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onClickTag(searchQuery)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(tagList) { tag ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 14.dp)
                        .clickable { onClickTag(tag) },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(tag, style = MaterialTheme.typography.headlineSmall)
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, modifier = Modifier.size(32.dp))
                }
                HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}


@Composable
fun BrowseDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BrowseDetailViewModel,
    onClickProduct: (Int) -> Unit
) {
    val result = viewModel.data
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle(initialValue = emptyList())
    val filterMenu = listOf("Category", "Brand", "Price", "Offers")

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(painterResource(R.drawable.outline_discover_tune_24), null, modifier = Modifier.rotate(90f))
                }
            }
            items(filterMenu) {menu ->
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text(menu, style = MaterialTheme.typography.titleMedium)
                    Icon(Icons.Outlined.KeyboardArrowDown, null, modifier = Modifier.size(22.dp))
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("${result.size} Products", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Sort by ", style = MaterialTheme.typography.titleSmall)
                Text("Relevance", style = MaterialTheme.typography.titleMedium)
                Icon(Icons.Outlined.KeyboardArrowDown, null)
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(result) { product ->
                ProductCard(item = product, onFavorite = viewModel::addToFavorites, onClick = { onClickProduct(product.id) }, isFavorite = favoriteIds.contains(product.id))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseAppBar(
    onNavigation: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    searchQuery: String = "Headphones"
) {
    CenterAlignedTopAppBar(
        title = { Text(searchQuery, style = appBarTitle) },
        navigationIcon = {
            IconButton(onClick = onNavigation) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null)
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Outlined.Search, null)
            }
        }
    )
}












