package id.rettorio.luxmarket.ui.vm

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



data class HomeUiState(
    val products: List<Product> = Application.products.filter { it.discount == null },
    val highlightProduct: List<Product> = Application.products.filter { it.discount != null },
    val favoriteIds: List<Int> = emptyList(),
)


class HomeViewModel(
    val repository: Repository
): ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = combine(_uiState, repository.sharedFavorites) { newState, favorites ->
        newState.copy(
            favoriteIds = favorites
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, HomeUiState())


    fun favoriteSwitch(product: Product) {
        val newList = uiState.value.favoriteIds.toMutableList()
        if(newList.contains(product.id)) {
            newList.remove(product.id)
        } else {
            newList.add(product.id)
        }
        // reflect change to local uiState first to reflect changes instantly in the Ui
        _uiState.update { it.copy(favoriteIds =  newList) }
        // then post change to repository
        viewModelScope.launch {  repository.updateSharedFavorites(newList.toList()) }
    }
}