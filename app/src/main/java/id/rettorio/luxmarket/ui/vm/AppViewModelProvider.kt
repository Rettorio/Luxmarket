package id.rettorio.luxmarket.ui.vm

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import id.rettorio.luxmarket.LuxmarketApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            CartViewModel(this.application().container.productRepository)
        }

        initializer {
            FavoritesViewModel(this.application().container.productRepository)
        }

        initializer {
            HomeViewModel(this.application().container.productRepository)
        }

        initializer {
            ProfileViewModel(this.application().container.productRepository)
        }

    }

    fun productFactory(id: Int) = viewModelFactory {
        initializer {
            ProductViewModel(id, this.application().container.productRepository)
        }
    }

    fun browseDetailFactory(tags: String) = viewModelFactory {
        initializer {
            BrowseDetailViewModel(
                tags,
                this.application().container.productRepository
            )
        }
    }
}

fun CreationExtras.application(): LuxmarketApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LuxmarketApplication)