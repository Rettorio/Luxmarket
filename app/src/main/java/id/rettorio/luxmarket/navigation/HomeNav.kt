package id.rettorio.luxmarket.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import id.rettorio.luxmarket.ui.screen.HomeScreen
import id.rettorio.luxmarket.ui.vm.AppViewModelProvider
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen

@Serializable
data object HomeNavigation


fun NavGraphBuilder.homeNavigation(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    navigation<HomeNavigation>(startDestination = HomeScreen) {
        composable<HomeScreen> {
            HomeScreen(modifier = modifier, viewModel = viewModel(factory = AppViewModelProvider.Factory), onClickProduct = { navController.navigate(ProductDetail(it)) })
        }
    }

}