package ui.physical_effect

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.HomeOptions
import model.PhysicalEffectInfo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

data class PhysicalEffectInfoScreen(
    val infoType: PhysicalEffectInfo,
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.PHYSICAL_EFFECT
    private val screenTitle = option.title

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                ThemeTopAppBar(
                    screenTitle, option.color,
                    navigationIcon = {
                        Image(
                            painterResource(infoType.icon),
                            null,
                            Modifier.padding(4.dp, 8.dp)
                        )
                    },
                    actions = {
                        Spacer(Modifier.width(56.dp))
                    }
                )
            },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = navigator::pop,
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } }
                )
            }
        ) {
            BoxWithConstraints {
                val isPortrait = maxHeight > maxWidth
                Column(
                    Modifier.padding(it).padding(horizontal = 8.dp).fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = 4.dp,
                        color = Color.White
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Surface(
                                color = Color(0xFF5D535E),
                                modifier = Modifier.wrapContentSize()
                            ) {
                                Text(
                                    infoType.title.uppercase(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.h3.copy(textAlign = TextAlign.Center),
                                    modifier = Modifier.padding(8.dp, 4.dp)
                                )
                            }

                            Image(
                                painterResource(if (isPortrait) infoType.portraitInfo else infoType.landscapeInfo),
                                "${infoType.description} $screenTitle",
                                Modifier.padding(8.dp).fillMaxWidth(),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }
            }
        }
    }
}