package ui.conclusion

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class ConclusionScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "CONCLUSION"


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
                TopAppBar(
                    title = {
                        Text(
                            text = screenTitle,
                            style = MaterialTheme.typography.h1,
                            color = Color(0xFFBCBBE7),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            },
            bottomBar = {
                BottomNavigation {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
                    ) {
                        val textSize = MaterialTheme.typography.button.fontSize
                        val textLayout = rememberTextMeasurer().measure(
                            text = "Previous section",
                            style = MaterialTheme.typography.button,
                            overflow = TextOverflow.Clip
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                    }
                }
            }
        ) {
            BoxWithConstraints {
                val boxScope = this

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource("conclusion/nextstep_image.png"),
                            contentDescription = "nextstep1",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().weight(0.9f).padding(horizontal=4.dp)
                        )

                        Image(
                            painter = painterResource("conclusion/nextstep_image2.png"),
                            contentDescription = "nextstep2",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal=4.dp)
                        )
                    }

                    Text(
                        text = "Next steps",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "You have reached the end of this decision aid. You can now:" +
                                "\n\n" +
                                "-   Revisit previous sections by pressing the ‘HOME’ button" +
                                "\n\n" +
                                "-   Revisit this decision aid in the future" +
                                "\n\n" +
                                "-   Speak to your oncologist to discuss your suitability for whichever option you favour",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "Where can you go for more information?",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "Click the button below to access a list of online websites you may be interested in",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(4.dp)
                    )

                }
            }
        }
    }
}