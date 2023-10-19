import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import ui.physical_effect.PhysicalEffectExamplesScreen

data class EmotionalEffectsFirstScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "EMOTIONAL EFFECTS"

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
                            color = Color(0xFFFF7E79),
                            style = MaterialTheme.typography.h1,
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

                        Button(
                            onClick = {
                                if (navigator.items.contains(PhysicalEffectExamplesScreen())) {
                                    navigator.pop()
                                } else {
                                    navigator.replace(PhysicalEffectExamplesScreen())
                                }
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                Icons.Rounded.FirstPage,
                                contentDescription = "Previous section"
                            )

                            BoxWithConstraints {
                                val boxScope = this
                                val sizeInDp = with(LocalDensity.current) {
                                    textLayout.size.width.toDp()
                                }

                                Text(
                                    text = "Previous section",
                                    fontSize = if (boxScope.maxWidth - 4.dp < sizeInDp) textSize * .8 else textSize,
                                    overflow = TextOverflow.Clip,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }

                        }

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = { navigator.push(EmotionalEffectsSecondScreen()) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Text(text = "Next")

                            Icon(
                                Icons.Rounded.ArrowForward, "Next page",
                                Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource("emotional_effect_1.png"),
                        contentDescription = "Emotional effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)
                    )

                }

                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "Survivors may still feel distressed"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("You may feel emotions such as ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("depression, anxiety, worry, or fear ")
                        }
                        append("even after treatment due to worry of cancer returning ")
                        append("financial concerns, changes in physical appearances etc. ")
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                )

                {
                    Image(
                        painter = painterResource("emotional_effect_2.png"),
                        contentDescription = "Emotional Effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)

                    )
                    Image(
                        painter = painterResource("emotional_effect_3.png"),
                        contentDescription = "Emotional Effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)

                    )

                }

                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "Find your emotional support"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("It is important for you to identify someone who can support you ")
                        append("emotionally. He/she can be anyone-a ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("family member, friend, ")
                        }
                        append("or ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("other cancer survivors. ")
                        }
                    }
                )



            }
        }
    }
}