package id.rettorio.luxmarket.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import id.rettorio.luxmarket.ui.screen.CartScreen
import id.rettorio.luxmarket.ui.vm.AppViewModelProvider
import kotlinx.serialization.Serializable

@Serializable
data object CartScreen

@Serializable
data object CartNavigation

fun NavGraphBuilder.cartNavigation(
    modifier: Modifier = Modifier
) {
    navigation<CartNavigation>(startDestination = CartScreen) {
        composable<CartScreen> {
            CartScreen(modifier, viewModel = viewModel(factory = AppViewModelProvider.Factory))
        }
    }
}