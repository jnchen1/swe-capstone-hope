import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LastPage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
import physical_effect.PhysicalEffectIntroScreen

data class WhatIsSurvivorshipThirdScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "SURVIVORSHIP"

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
                        Button(
                            onClick = {
                                if (navigator.items.contains(WhatIsSurvivorshipSecondScreen())) {
                                    navigator.pop()
                                } else {
                                    navigator.replace(WhatIsSurvivorshipSecondScreen())
                                }
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Icon(
                                Icons.Rounded.ArrowBack, "Back page",
                                Modifier.padding(end = 4.dp)
                            )
                            Text(text = "Back")
                        }

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = { navigator.push(PhysicalEffectIntroScreen()) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            val textStyleButton = MaterialTheme.typography.button
                            var textSize by remember { mutableStateOf(textStyleButton.fontSize) }
                            var readyToDraw by remember { mutableStateOf(false) }

                            Text(
                                text = "Next section",
                                fontSize = textSize,
                                overflow = TextOverflow.Clip,
                                modifier = Modifier.padding(end = 0.dp).drawWithContent {
                                    if (readyToDraw) drawContent()
                                },
                                onTextLayout = { textLayoutResult ->
                                    if (!readyToDraw && textLayoutResult.hasVisualOverflow) {
                                        textSize *= .85
                                    } else if (!readyToDraw && !textLayoutResult.hasVisualOverflow) {
                                        textSize = textStyleButton.fontSize
                                    }
                                    readyToDraw = true
                                }
                            )
                            Icon(
                                Icons.Rounded.LastPage,
                                contentDescription = "Next section"
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
                        painter = painterResource("ribbon_survivorship.png"),
                        contentDescription = "Physical effects",
                        modifier = Modifier.size(200.dp).padding(10.dp)
                    )
                }
                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "Why is survivorship care important?"
                )
                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("Survivorship care includes ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("regular follow-up sessions")
                        }
                        append(" to ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("prevent and monitor")
                        }
                        append(" for signs of returning cancers. This is because there is a ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("possibility")
                        }
                        append(" of the cancer ")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("returning")
                        }
                        append(" in the breast or other parts of the body.")
                    }
                )

            }
        }
    }
}