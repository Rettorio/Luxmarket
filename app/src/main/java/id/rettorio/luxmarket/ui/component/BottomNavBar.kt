package id.rettorio.luxmarket.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import id.rettorio.luxmarket.navigation.HomeNavigation
import id.rettorio.luxmarket.navigation.ProfileNavigation
import id.rettorio.luxmarket.navigation.BrowseNavigation
import id.rettorio.luxmarket.navigation.CartNavigation
import id.rettorio.luxmarket.navigation.FavoritesNavigation
import kotlinx.coroutines.flow.SharedFlow


data class NavItem(
    val icon: ImageVector,
    val onClick: () -> Unit,
)

private fun rootNavFinder(nav: String?): String  {
    return when(nav) {
        FavoritesNavigation::class.qualifiedName -> "Favorites"
        CartNavigation::class.qualifiedName -> "Cart"
        ProfileNavigation::class.qualifiedName -> "Profile"
        HomeNavigation::class.qualifiedName -> "Home"
        BrowseNavigation::class.qualifiedName -> "Browse"
        else -> "Home"
    }
}


@Composable
fun BottomNavBar(
    navController: NavController,
    backStackEntry: NavBackStackEntry?,
    cartFLow: SharedFlow<Int>,
    favoriteFlow: SharedFlow<List<Int>>
) {
    var currentScreen by remember { mutableStateOf("Home") }
    val cartBadges by  cartFLow.collectAsStateWithLifecycle(initialValue = 0)
    val favoriteBadges by favoriteFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    val routesMap: Map<String, NavItem> = mapOf(
        "Home" to NavItem(
            icon = Icons.Outlined.Home,
            onClick = { navController.navigate(HomeNavigation) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = false
            } }
        ),
        "Browse" to NavItem(
            icon = Icons.Outlined.Search,
            onClick = { navController.navigate(BrowseNavigation) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            } }
        ),
        "Favorites" to NavItem(
            icon = Icons.Outlined.FavoriteBorder,
            onClick = { navController.navigate(FavoritesNavigation) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = false
            } }
        ),
        "Cart" to NavItem(
            icon = Icons.Outlined.ShoppingCart,
            onClick = { navController.navigate(CartNavigation) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = false
            } }
        ),
        "Profile" to NavItem(
            icon = Icons.Outlined.Person,
            onClick = { navController.navigate(ProfileNavigation) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = false
            } }
        )
    )

    BottomAppBar(
        actions = {
            routesMap.forEach { (label, item) ->
                NavigationBarItem(
                    selected = currentScreen == label,
                    onClick = item.onClick ,
                    icon = {
                        when(label) {
                            "Favorites" -> {
                                BadgedBox(
                                    badge = {
                                        if(favoriteBadges.isNotEmpty()) {
                                            Badge(
                                                containerColor =  if(currentScreen == label) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiaryContainer,
                                                contentColor = MaterialTheme.colorScheme.background,
                                                modifier = Modifier.padding(end = 16.dp, bottom = 4.dp)
                                            ) {
                                                Text(favoriteBadges.size.toString(), modifier = Modifier.padding(horizontal = 4.dp))
                                            }
                                        }
                                    }
                                ) {
                                    Icon(imageVector = item.icon, contentDescription = null)
                                }
                            }
                            "Cart" -> {
                                BadgedBox(
                                    badge = {
                                        if(cartBadges > 0) {
                                            Badge(
                                                containerColor =  if(currentScreen == label) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiaryContainer,
                                                contentColor = MaterialTheme.colorScheme.background,
                                                modifier = Modifier.padding(end = 16.dp, bottom = 4.dp)
                                            ) {
                                                Text(cartBadges.toString(), modifier = Modifier.padding(horizontal = 4.dp))
                                            }
                                        }
                                    }
                                ) {
                                    Icon(imageVector = item.icon, contentDescription = null)
                                }
                            }
                            else -> {
                                Icon(imageVector = item.icon, contentDescription = null)
                            }
                        }
                    },
                    enabled = currentScreen != label,
                    label = { Text(label, style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = Color.Unspecified,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unselectedTextColor = MaterialTheme.colorScheme.tertiaryContainer,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = Color.Unspecified,
                        disabledIconColor = Color.Unspecified
                    )
                )
            }
        },
        containerColor = Color.White
    )

    LaunchedEffect(backStackEntry?.destination) {
        currentScreen = rootNavFinder(backStackEntry?.destination?.parent?.route)
    }
}

@Composable
fun BottomNavBarPreview(
    currentScreen: String = "Home"
) {

    val routesMap: Map<String, NavItem> = mapOf(
        "Home" to NavItem(icon = Icons.Outlined.Home, onClick = {  }),
        "Browse" to NavItem(icon = Icons.Outlined.Search, onClick = {  }),
        "Favorites" to NavItem(icon = Icons.Outlined.FavoriteBorder, onClick = {  }),
        "Cart" to NavItem(icon = Icons.Outlined.ShoppingCart, onClick = {  }),
        "Profile" to NavItem(icon = Icons.Outlined.Person, onClick = {  })
    )

    BottomAppBar(
        actions = {
            routesMap.forEach { (label, item) ->
                NavigationBarItem(
                    selected = currentScreen == label,
                    onClick = item.onClick ,
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                    },
                    enabled = currentScreen != label,
                    label = { Text(label, style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = Color.Unspecified,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unselectedTextColor = MaterialTheme.colorScheme.tertiaryContainer,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = Color.Unspecified,
                        disabledIconColor = Color.Unspecified
                    )
                )
            }
        },
        containerColor = Color.White
    )
}