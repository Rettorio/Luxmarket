package id.rettorio.luxmarket

import android.app.Application
import id.rettorio.luxmarket.data.AppDataContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LuxmarketApplication: Application() {

    lateinit var container: AppDataContainer

    override fun onCreate() {
        super.onCreate()
        val appScope = CoroutineScope(SupervisorJob())
        container = AppDataContainer(appScope, applicationContext)
    }
}