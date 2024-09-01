package id.rettorio.luxmarket.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import id.rettorio.luxmarket.ui.screen.BrowseDetailDialog
import id.rettorio.luxmarket.ui.screen.BrowseDetailScreen
import id.rettorio.luxmarket.ui.screen.BrowseScreen
import id.rettorio.luxmarket.ui.screen.ProductScreen
import id.rettorio.luxmarket.ui.vm.AppViewModelProvider
import kotlinx.serialization.Serializable


@Serializable
data object BrowseScreen

@Serializable
data object BrowseNavigation

@Serializable
data class BrowseDetail(
    val tag: String
)

@Serializable
data class ProductDetail(
    val id: Int
)

@Serializable
data class SearchDialog(
    val searchQuery: String
)


fun NavGraphBuilder.browseNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    browseTag: List<String>
) {
    navigation<BrowseNavigation>(startDestination = BrowseScreen) {
        composable<BrowseScreen> {
            BrowseScreen(
                modifier = modifier,
                onClickTag = { navController.navigate( BrowseDetail(it) ) },
                tagList = browseTag
            )
        }
        composable<BrowseDetail> { backStackEntry ->
            val tag = backStackEntry.toRoute<BrowseDetail>().tag
            BrowseDetailScreen(
                modifier = modifier,
                viewModel = viewModel(factory = AppViewModelProvider.browseDetailFactory(tag)),
                onClickProduct = { navController.navigate(ProductDetail(it)) }
            )
        }
        composable<ProductDetail>{
            val id = it.toRoute<ProductDetail>().id
            ProductScreen(modifier = modifier, viewModel = viewModel(factory = AppViewModelProvider.productFactory(id)))
        }
        dialog<SearchDialog> {
            val tag = it.toRoute<SearchDialog>().searchQuery
            BrowseDetailDialog(
                modifier = modifier,
                onSearch = {query -> navController.navigate(BrowseDetail(query)) },
                onCancel = {  },
                searchQuery = tag
            )
        }
    }
}
