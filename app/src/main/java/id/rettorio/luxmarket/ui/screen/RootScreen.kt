package id.rettorio.luxmarket.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import id.rettorio.luxmarket.data.Application
import id.rettorio.luxmarket.navigation.BrowseDetail
import id.rettorio.luxmarket.navigation.BrowseNavigation
import id.rettorio.luxmarket.navigation.HomeNavigation
import id.rettorio.luxmarket.navigation.ProductDetail
import id.rettorio.luxmarket.navigation.SearchDialog
import id.rettorio.luxmarket.navigation.browseNavigation
import id.rettorio.luxmarket.navigation.cartNavigation
import id.rettorio.luxmarket.navigation.favoritesNavigation
import id.rettorio.luxmarket.navigation.homeNavigation
import id.rettorio.luxmarket.navigation.profileNavigation
import id.rettorio.luxmarket.ui.component.BottomNavBar
import id.rettorio.luxmarket.LuxmarketApplication


@Composable
fun RootAppScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val application = context.applicationContext as LuxmarketApplication
    val appData = Application


    Scaffold(
        topBar = {
            val route = backStackEntry?.destination?.route
            if(route != null && route.startsWith(BrowseDetail::class.qualifiedName!!)) {
                val tag = backStackEntry?.toRoute<BrowseDetail>()?.tag ?: ""
                BrowseAppBar(
                    searchQuery = tag,
                    onNavigation = { navController.navigate(BrowseNavigation) },
                    onSearchClick = { navController.navigate(SearchDialog(tag)) }
                )
            } else if (route != null && route.startsWith(ProductDetail::class.qualifiedName!!)) {
                val product = Application.products.find { it.id == backStackEntry?.toRoute<ProductDetail>()?.id }
                BrowseAppBar(
                    searchQuery = product!!.type,
                    onNavigation = { navController.popBackStack() },
                    onSearchClick = { navController.navigate(SearchDialog(product.type)) }
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                backStackEntry = backStackEntry,
                favoriteFlow = application.container.productRepository.sharedFavorites,
                cartFLow = application.container.productRepository.cartsCounter
            )
        }
    ) {paddingValue ->
        NavHost(navController = navController, startDestination = HomeNavigation, modifier = Modifier.padding(paddingValue)) {
            homeNavigation(navController = navController)
            browseNavigation(navController =  navController, browseTag =  appData.browseTags)
            favoritesNavigation()
            cartNavigation()
            profileNavigation()
        }
    }
}