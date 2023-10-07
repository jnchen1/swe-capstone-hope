import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Navigator(
            screen = DisclaimerScreen(),
            onBackPressed = { _ -> true }
        )
    }
}

expect fun getPlatformName(): String