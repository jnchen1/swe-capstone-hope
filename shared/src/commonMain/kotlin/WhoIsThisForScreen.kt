import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

data class WhoIsThisForScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "WHO IS THIS FOR?"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = navigator::pop,
                    showNextPage = true, nextAction = { navigator.push(HomeScreen()) }
                )
            }
        ) {
            BoxWithConstraints {
                val isPortrait = maxHeight > maxWidth
                val image = if (isPortrait) "whoIsThisFor_portrait.png" else "whoIsThisFor_landscape.png"

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Breast cancer survivors")
                            }
                            append(" who are ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("at least 3 years")
                            }
                            append(" out of initial treatment phase.")
                        }
                    )

                    val imageModifier =
                        Modifier.then(if (isPortrait) Modifier.fillMaxHeight() else Modifier.fillMaxWidth())
                    Image(
                        painter = painterResource(image),
                        contentDescription = "Image of Who This is for",
                        contentScale = ContentScale.FillHeight,
                        modifier = imageModifier.padding(bottom = 12.dp).align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}