package id.rettorio.luxmarket.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import id.rettorio.luxmarket.data.Application
import kotlinx.serialization.Serializable
import kotlin.math.roundToLong

@Serializable
data class ProductImages(
    @DrawableRes
    val small: Int?,
    @DrawableRes
    val medium: Int?,
    @DrawableRes
    val large: Int?
)

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val images: ProductImages,
    val price: Double,
    val stock: Int = 22,
    val discount: Double?,
    val type: String,
    val tags: List<String>,
    val subtitle: String?,
    @StringRes val description: Int?
)

fun Product.discountPrice() : Double {
    return if(this.discount != null) {
        val discountedAmount = this.price * this.discount
        val roundedResult = (price - discountedAmount) * 100
        roundedResult.roundToLong() / 100.0
    }
    else {
        this.price
    }
}

fun Product.toCartItem(): CartItem {
    return Application.cart.find { it.productId == this.id } ?: CartItem(this.id, 1)
}