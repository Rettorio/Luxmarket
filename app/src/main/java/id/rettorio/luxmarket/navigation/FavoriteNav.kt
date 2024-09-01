package id.rettorio.luxmarket.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import id.rettorio.luxmarket.ui.screen.FavoritesScreen
import id.rettorio.luxmarket.ui.vm.AppViewModelProvider
import kotlinx.serialization.Serializable

@Serializable
data object FavoritesScreen

@Serializable
data object FavoritesNavigation

fun NavGraphBuilder.favoritesNavigation(
    modifier: Modifier = Modifier
) {
    navigation<FavoritesNavigation>(startDestination = FavoritesScreen) {
        composable<FavoritesScreen> {
            FavoritesScreen(modifier, viewModel = viewModel(factory = AppViewModelProvider.Factory))
        }
    }
}
