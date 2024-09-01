package id.rettorio.luxmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import id.rettorio.luxmarket.ui.screen.RootAppScreen
import id.rettorio.luxmarket.ui.theme.LuxmarketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LuxmarketTheme {
                // A surface container using the 'background' color from the theme
                RootAppScreen()
            }
        }
    }
}