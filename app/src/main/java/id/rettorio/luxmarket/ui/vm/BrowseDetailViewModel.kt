package id.rettorio.luxmarket.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rettorio.luxmarket.data.Repository
import id.rettorio.luxmarket.model.Product
import kotlinx.coroutines.launch

class BrowseDetailViewModel(
    tags: String,
    private val repository: Repository
): ViewModel() {

    val data = if(tags == "All") repository.getProducts() else
        repository.getProducts().filter {product ->
        product.type.lowercase().contains(tags.lowercase()) ||
                product.name.lowercase().contains(tags.lowercase()) ||
                product.subtitle?.lowercase()?.contains(tags.lowercase()) ?: false ||
                product.tags.find { it.lowercase().contains(tags.lowercase()) } != null
    }

    val favoriteIds = repository.sharedFavorites

    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            repository.addToSharedFavorites(product)
        }
    }
}