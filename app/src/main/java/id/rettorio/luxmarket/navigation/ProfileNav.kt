package id.rettorio.luxmarket.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import id.rettorio.luxmarket.ui.screen.ProfileScreen
import id.rettorio.luxmarket.ui.vm.AppViewModelProvider
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreen

@Serializable
data object  ProfileNavigation

fun NavGraphBuilder.profileNavigation(
    modifier: Modifier = Modifier
) {
    navigation<ProfileNavigation>(startDestination = ProfileScreen) {
        composable<ProfileScreen> {
            ProfileScreen(modifier, viewModel = viewModel(factory = AppViewModelProvider.Factory))
        }
    }
}