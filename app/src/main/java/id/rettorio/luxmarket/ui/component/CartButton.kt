package id.rettorio.luxmarket.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartButton(
    onClick: () -> Unit,
    isInCart: Boolean,
    modifier: Modifier = Modifier,
    size: ComponentSize = ComponentSize.NORMAL
) {
    when(size) {
        ComponentSize.NORMAL -> {
            FilledTonalIconButton(
                modifier = modifier.size(size.value),
                onClick = onClick,
                shape = CircleShape,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if(isInCart) {
                    Icon(Icons.Filled.ShoppingCart, null, modifier = Modifier.fillMaxSize(.65f))
                } else {
                    Icon(Icons.Outlined.ShoppingCart, null, modifier = Modifier.fillMaxSize(.65f))
                }
            }
        }
        ComponentSize.BIG -> {
            FilledTonalIconButton(
                modifier = modifier.size(size.value),
                onClick = onClick,
                shape = CircleShape,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if(isInCart) {
                    Icon(Icons.Filled.ShoppingCart, null, modifier = Modifier.fillMaxSize(.65f))
                } else {
                    Icon(Icons.Outlined.ShoppingCart, null, modifier = Modifier.fillMaxSize(.65f))
                }
            }
        }
    }
}