package id.rettorio.luxmarket.model

import androidx.compose.ui.util.fastFirst
import id.rettorio.luxmarket.data.Application

data class CartItem(
    val productId: Int,
    var qty: Int
)

fun CartItem.toProduct() : Product {
    return Application.products.fastFirst { it.id == this.productId }
}