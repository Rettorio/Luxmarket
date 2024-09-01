package id.rettorio.luxmarket.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rettorio.luxmarket.data.Repository
import id.rettorio.luxmarket.model.CartItem
import id.rettorio.luxmarket.model.Product
import id.rettorio.luxmarket.model.toCartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class FavoriteUiState(
    val products: List<Product> = emptyList(),
    val cartIds: List<Int> = emptyList()
)

class FavoritesViewModel(
    private val repository: Repository
): ViewModel() {

    private val _mutableUiState: MutableStateFlow<FavoriteUiState> = MutableStateFlow(
        FavoriteUiState()
    )
    val uiState: StateFlow<FavoriteUiState> = combine(_mutableUiState, repository.sharedFavorites, repository.sharedCarts) { oldState, favorites, carts ->
        oldState.copy(
            products = favorites.map { repository.productById(it)!! },
            cartIds = carts.map { it.productId }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoriteUiState())



    fun unFavorite(product: Product) {
        val newList = uiState.value.products.toMutableList()
        if(newList.contains(product)) {
            newList.remove(product)
        }
        // reflect change to local uiState first to reflect changes instantly in the Ui
        _mutableUiState.update { it.copy(products =  newList) }
        // then post change to repository
        viewModelScope.launch {  repository.updateSharedFavorites(newList.map { it.id }) }
    }

    fun cartSwitch(product: Product) {
        val newList = uiState.value.cartIds.toMutableList()
        if(newList.contains(product.id)) {
            newList.remove(product.id)
        } else {
            newList.add(product.id)
        }
        _mutableUiState.update { it.copy(cartIds = newList) }

        val idsToCarts: List<CartItem> = newList.map {
            repository.productById(it)!!.toCartItem()
        }

        viewModelScope.launch { repository.updateSharedCarts(idsToCarts) }

    }
}