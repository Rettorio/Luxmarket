package id.rettorio.luxmarket.ui.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rettorio.luxmarket.data.Repository
import id.rettorio.luxmarket.model.CartItem
import id.rettorio.luxmarket.model.discountPrice
import id.rettorio.luxmarket.model.toProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToLong

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val isCheckout: Boolean = false,
    val shippingCost: Double = 0.0
)

class CartViewModel(
    private val repository: Repository
) : ViewModel() {

    private val shippingCost = repository.getSelectedUser().shipping
    private val _uiState: MutableStateFlow<CartUiState> = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> get() = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        CartUiState()
    )

    init {
        viewModelScope.launch {
            repository.sharedCarts.collect { items ->
                val totalP = calcTotalPrice(items)
                _uiState.value = CartUiState(items, totalPrice = if(totalP > 0.0) totalP + shippingCost.roundToLong() else totalP,  shippingCost = shippingCost)
            }
        }
    }

    private fun calcTotalPrice(items: List<CartItem>): Double {
        return items.fold(0.0) {acc, cartItem -> (cartItem.toProduct().discountPrice() * cartItem.qty) + acc }
    }

    fun incQtyCartItem(item: CartItem) {
        val newQty: Int = item.qty.inc()
        val newCartItem = _uiState.value.cartItems.map { if(it.productId == item.productId) it.copy(qty = newQty) else it }
        _uiState.update { newState ->
            newState.copy(cartItems = newCartItem, totalPrice = calcTotalPrice(newCartItem))
        }
        viewModelScope.launch { repository.updateSharedCarts(newCartItem) }
    }

    fun decQtyCartItem(item: CartItem) {
        val newQty: Int = item.qty.dec()
        val newCartItem = _uiState.value.cartItems.map { if(it.productId == item.productId) it.copy(qty = newQty) else it }
        _uiState.update { newState ->
            newState.copy(cartItems = newCartItem, totalPrice = calcTotalPrice(newCartItem))
        }
        viewModelScope.launch { repository.updateSharedCarts(newCartItem) }
    }

    fun checkoutSwitch(currentState: Boolean) {
        _uiState.update {
            it.copy(
                isCheckout = !currentState
            )
        }
    }

    fun confirmCheckout() {
        _uiState.update {
            it.copy(cartItems = emptyList())
        }
        viewModelScope.launch {
            repository.updateSharedCarts(emptyList())
        }
    }

}