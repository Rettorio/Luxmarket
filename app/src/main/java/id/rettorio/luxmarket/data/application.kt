package id.rettorio.luxmarket.data


import id.rettorio.luxmarket.R
import id.rettorio.luxmarket.model.CartItem
import id.rettorio.luxmarket.model.Product
import id.rettorio.luxmarket.model.ProductImages

object Application {
    val products: List<Product> = listOf(
        Product(
            id = 1,
            name = "RØDE PodMic",
            images = ProductImages(R.drawable.small_rode_microphone, R.drawable.medium_rode_microphone, R.drawable.large_rode_microphone),
            price = 199.99,
            discount = 0.25,
            type = "Microphones",
            subtitle = "Dynamic microphone, Speaker microphone",
            description = R.string.podmic_desc,
            tags = listOf("Audio","Photo","Video")
        ),
        Product(
            id = 2,
            name = "SONY Premium Wireless Headphones",
            images = ProductImages(R.drawable.small_sony_headphones_01, R.drawable.medium_sony_headphones_01, R.drawable.large_sony_headphones_01),
            price = 349.99,
            discount = null,
            type = "Headphones",
            subtitle = "Model: WH-1000XM4, Black",
            description = R.string.sony_headphone_desc,
            tags = listOf("Audio","Peripherals")
        ),
        Product(
            id = 3,
            name = "SONY Premium Wireless Headphones",
            images = ProductImages(R.drawable.small_sony_headphones_02, R.drawable.medium_sony_headphones_02, R.drawable.large_sony_headphones_02),
            price = 349.99,
            discount = null,
            type = "Headphones",
            subtitle = "Model: WH-1000XM4, Beige",
            description = R.string.sony_headphone_desc, 
            tags = listOf("Audio","Peripherals")
        ),
        Product(
            id = 4,
            name = "Samsung Galaxy Buds 2 Pro",
            images = ProductImages(R.drawable.small_samsung_buds, R.drawable.medium_samsung_buds, R.drawable.large_samsung_buds),
            price = 149.99,
            discount = 0.20,
            type = "Headphones",
            subtitle = "NC, 6 h, wireless",
            description = R.string.galaxy_bud_desc,
            tags = listOf("Audio", "Peripherals")
        ),
        Product(
            id = 5,
            name = "Apple AirPods Pro MagSafe Case",
            images = ProductImages(R.drawable.small_apple_airpods, R.drawable.medium_apple_airpods, R.drawable.large_apple_airpods),
            price = 179.00,
            discount = null,
            type = "Headphones",
            subtitle = "NC, 4 h, Wireless",
            description = R.string.airpods_desc,
            tags = listOf("Audio", "Peripherals")
        ),
        Product(
            id = 6,
            name = "SHURE SM7B",
            images = ProductImages(R.drawable.small_shure_microphone, R.drawable.medium_shure_microphone, R.drawable.large_shure_microphone),
            price = 379.49,
            discount = null,
            type = "Microphones",
            subtitle = "Studio Microphones",
            description = R.string.shure_desc,
            tags = listOf("Audio", "Podcasts")
        ),
        Product(
            id = 7,
            name = "GOOGLE Nest Mini",
            images = ProductImages(R.drawable.small_googlehome, R.drawable.medium_googlehome, R.drawable.large_googlehome),
            price = 70.0,
            discount = null,
            type = "Smart Assistant",
            subtitle = "Google Assistant, IFTTT",
            description = R.string.google_nest_desc,
            tags = listOf("Drones", "Electronics")
        ),
        Product(
            id = 8,
            name = "SONY Alpha 7 IV",
            images = ProductImages(R.drawable.small_sony_camera, R.drawable.medium_sony_camera, R.drawable.large_sony_camera),
            price = 329.99,
            discount = null,
            type = "Camera",
            subtitle = "Full-frame Interchangeable Lens Camera 33MP, 10FPS, 4K/60p",
            description = R.string.sony_camera_desc,
            tags = listOf("Photo","Video")
        ),
        Product(
            id = 9,
            name = "XIAOMI Redmi Watch 3",
            images = ProductImages(R.drawable.small_xiaomi_watch, R.drawable.medium_xiaomi_watch, R.drawable.large_xiaomi_watch),
            price = 94.2,
            discount = 0.10,
            type = "Smartwatches",
            subtitle = "42.58 mm, Aluminium, Plastic, One size",
            description = R.string.redmi_watch_desc,
            tags = listOf("")
        ),
        Product(
            id = 10,
            name = "Google Pixel 6",
            images = ProductImages(R.drawable.small_googlepixel_smartphone, R.drawable.medium_googlepixel_smartphone, R.drawable.large_googlepixel_smartphone),
            price = 499.9,
            discount = 0.30,
            type = "Smartphones",
            subtitle = "Android 14, 20:9 Aspect Ratio, 4524mah Battery and 8 GB LPDDR5 RAM",
            description = R.string.gp6_desc,
            tags = listOf("Photo", "Android")
        ),
        Product(
            id = 11,
            name = "43\" Crystal UHD DU8000 4K Smart TV (2024)",
            images = ProductImages(R.drawable.small_samsung_television, R.drawable.medium_samsung_television, R.drawable.large_samsung_television),
            price = 327.57,
            discount = null,
            type = "Smart TV",
            subtitle = "Dynamic Crystal Color, 50Hz 4K HDR",
            description = R.string.samsungtv_desc,
            tags = listOf("")
        ),
        Product(
            id = 12,
            name = "Lenovo IdeaPad Slim 5i 14IRL8 ",
            images = ProductImages(R.drawable.small_lenovo_pc, R.drawable.medium_lenovo_pc, R.drawable.large_lenovo_pc),
            price = 899.99,
            discount = 0.15,
            type = "Laptop",
            subtitle = "Intel i7-13620H, 16GB Ram and 512gb SSD with IPS 16:10 display",
            description = R.string.lenovopc_desc,
            tags = listOf("")
        ),
    )
    val browseTags: List<String> = listOf(
        "All",
        "Audio",
        "Drones + Electronics",
        "Photo + Video",
        "Gaming + VR",
        "Networking",
        "Notebooks + PCs",
        "PC Components",
        "Peripherals",
        "Smartphones + Tablets",
        "Software Solutions",
        "TV + Home Cinema"
    )


    val cart: MutableList<CartItem> =
        mutableListOf(
            CartItem(1, 1),
            CartItem(2, 1),
            CartItem(7, 1),
            CartItem(9, 1)
        )

    val favorites: List<Int> = listOf(
        3,
        4
    )
    var selectedUser: Int = 1
}