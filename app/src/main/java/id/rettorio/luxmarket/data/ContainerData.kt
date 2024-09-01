package id.rettorio.luxmarket.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope

interface AppContainer {
    val productRepository: Repository
}

class AppDataContainer(
    appScope: CoroutineScope,
    context: Context
) : AppContainer {
    /**
     * Implementation for [Repository]
     */
    override val productRepository: Repository = Repository(appScope, context)


}