package id.rettorio.luxmarket.data

import android.content.Context
import androidx.compose.ui.util.fastFirst
import id.rettorio.luxmarket.model.CartItem
import id.rettorio.luxmarket.model.Product
import id.rettorio.luxmarket.model.User
import id.rettorio.luxmarket.model.toCartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class Repository(
    private val externalScope: CoroutineScope,
    context: Context
) {
    private val _carts = MutableSharedFlow<List<CartItem>>(replay = 1)
    private val _sharedFavorites = MutableSharedFlow<List<Int>>(replay = 1)
    private val _cartsCounter = MutableSharedFlow<Int>(replay = 1)

    val sharedFavorites: SharedFlow<List<Int>> = _sharedFavorites
    val sharedCarts: SharedFlow<List<CartItem>> = _carts
    val cartsCounter: SharedFlow<Int> = _cartsCounter
    @OptIn(ExperimentalSerializationApi::class)
    private val users: List<User> = Json.decodeFromStream(context.assets.open("User.json"))

    init {
        externalScope.launch {
            _sharedFavorites.emit(Application.favorites)
            _carts.emit(Application.cart)
            _cartsCounter.emit(Application.cart.size)
        }
    }

    private fun bindNewCartList(newList: List<CartItem>) {
        Application.cart.clear()
        Application.cart.addAll(newList)
    }

    fun updateUser(user: Int) {
        if(user in 1..30) {
            Application.selectedUser = user
        }
    }

    fun getSelectedUser(): User {
        return users.fastFirst { it.id == Application.selectedUser }
    }


    suspend fun updateSharedFavorites(newValue: List<Int>) {
        externalScope.launch {
            _sharedFavorites.emit(newValue)
        }
    }

    suspend fun addToSharedFavorites(product: Product) {
        var currentList: List<Int> = emptyList()
        externalScope.launch {
            sharedFavorites.collect {
                currentList = it
            }
        }
        delay(150L)
        if(!currentList.contains(product.id)) {
            val newList = currentList.toMutableList()
            newList.add(product.id)
            _sharedFavorites.emit(newList)
        }
    }

    suspend fun removeToSharedFavorites(product: Product) {
        var currentList: List<Int> = emptyList()
        externalScope.launch {
            sharedFavorites.collect {
                currentList = it
            }
        }
        delay(150L)
        if(currentList.contains(product.id)) {
            val newList = currentList.toMutableList()
            newList.remove(product.id)
            _sharedFavorites.emit(newList)
        }
    }

    fun getProducts(): List<Product> {
        return Application.products
    }

    //use this method to update cartItem qty
    suspend fun updateSharedCarts(newValue: List<CartItem>) {
        externalScope.launch {
            _carts.emit(newValue)
            _cartsCounter.emit(newValue.size)
            //bind new value directly to the dummy data instance
            //so product.toItemCart() method could reach the right CartItem instead create a new instance
            bindNewCartList(newValue)
        }
    }

    suspend fun addToSharedCarts(product: Product) {
        var currentList: List<CartItem> = emptyList()
        externalScope.launch {
            sharedCarts.collect {
                currentList = it
            }
        }
        delay(150L)
        if(!currentList.contains(product.toCartItem())) {
            val newList = currentList.toMutableList()
            newList.add(product.toCartItem())
            _carts.emit(newList)
            _cartsCounter.emit(newList.size)
            //apply to hard dummy carts list
            bindNewCartList(newList)
        }
    }

    suspend fun removeToSharedCarts(product: Product) {
        var currentList: List<CartItem> = emptyList()
        externalScope.launch {
            sharedCarts.collect {
                currentList = it
            }
        }
        delay(150L)
        if(currentList.contains(product.toCartItem())) {
            val newList = currentList.toMutableList()
            newList.remove(product.toCartItem())
            _carts.emit(newList)
            _cartsCounter.emit(newList.size)
            bindNewCartList(newList)
        }
    }


    fun productById(id: Int) : Product?  {
        return Application.products.find { it.id == id }
    }

}