package id.rettorio.luxmarket.ui.vm


import androidx.compose.ui.util.fastFirst
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rettorio.luxmarket.data.Application
import id.rettorio.luxmarket.data.Repository
import id.rettorio.luxmarket.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProductUiState(
    val product: Product = Application.products[0],
    val onFavorite: Boolean = false,
    val onCart: Boolean = false
)


class ProductViewModel(
    private val _productId: Int,
    private val repository: Repository
): ViewModel() {


    private val _mutableUiState = MutableStateFlow(ProductUiState(product = Application.products.fastFirst { it.id == _productId }))
    val uiState: StateFlow<ProductUiState> = combine(_mutableUiState, repository.sharedCarts, repository.sharedFavorites) { oldState, carts, favorites ->
        oldState.copy(
            onFavorite = favorites.contains(_productId),
            onCart = carts.firstOrNull { it.productId == _productId } != null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ProductUiState())


    fun favoriteSwitch() {
       if(uiState.value.onFavorite) {
           viewModelScope.launch {
               repository.removeToSharedFavorites(uiState.value.product)
           }
       } else {
           viewModelScope.launch {
                repository.addToSharedFavorites(uiState.value.product)
           }
       }
    }

    fun cartSwitch(onCart: Boolean) {
        if(onCart) {
            viewModelScope.launch {
                repository.removeToSharedCarts(uiState.value.product)
            }
        } else {
            viewModelScope.launch {
                repository.addToSharedCarts(uiState.value.product)
            }
        }
    }

}