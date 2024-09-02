<div align="center">
<img src="app/src/main/ic_launcher-playstore.png" alt="luxmarket" width="100" height="100"/>
<h1 >Luxmarket</h1>
<div align="center">

E-Commerce concept app made with Jetpack Coxmpose and Material3

![GitHub top language](https://img.shields.io/github/languages/top/BobbyESP/Spowlo?style=for-the-badge)

</div>



## 📸 Screenshots!
<div align="center">
<div>
  <img src="https://github.com/user-attachments/assets/0ee9a18e-3a01-49a2-a0cb-7ea609f1caf1" width="30%" />
  <img src="https://github.com/user-attachments/assets/947fd2fd-336e-440d-9cec-c9a731330e4c" width="30%" />
  <img src="https://github.com/user-attachments/assets/aee78839-617b-4cea-bd27-cfcbd6a3b926" width="30%" />
</div>
</div>
</div>

## 🔮 Features

- Minimalist and Clean UI thanks to [Gloria Ntawuruhunga](https://www.figma.com/@glorianta) for the design material
  
- Carousel to show highlights product
  
- Search and Checkout Feature
  
- Random user generator thanks to [Mockaroo](https://www.mockaroo.com/) for providing dummy user data


## 📖 Description
Luxmarket is a concept e-commerce app built with Jetpack Compose and Material 3 with 
[clean architecture](https://medium.com/simform-engineering/clean-architecture-in-android-12d61c4f5318) impementation. This App does not use any local or online databases instead, it uses JSON and hard-coded data for the data source, which would look like this in the repository class. :
```kotlin
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
```
