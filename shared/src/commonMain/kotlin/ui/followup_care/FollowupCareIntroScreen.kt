package ui.followup_care

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

data class FollowupCareIntroScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "FOLLOW-UP CARE OPTION"

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
                            color = Color(0xFFC6E0B5),
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
                        val textStyleButton = MaterialTheme.typography.button
                        var textSize by remember { mutableStateOf(textStyleButton.fontSize) }
                        var readyToDraw by remember { mutableStateOf(false) }

                        Button(
                            onClick = { /*TODO: Add Section 3*/ },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                Icons.Rounded.FirstPage,
                                contentDescription = "Previous section"
                            )
                            Text(
                                text = "Previous section",
                                fontSize = textSize,
                                overflow = TextOverflow.Clip,
                                modifier = Modifier.padding(start = 8.dp).drawWithContent {
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
                        }

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = { /*TODO: navigator.push()*/ },
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
                Image(
                    painter = painterResource("followup_care/care_image.png"),
                    contentDescription = "Follow-up care",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.padding(top = 8.dp).width(300.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Follow-up care aims to ensure your well-being",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                )

                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp)) {
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "1. "
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "Monitor for signs of cancer returning"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "2. "
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "Manage late and long-term physical effects from cancer treatment(s)"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "3. "
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "Manage your emotional needs"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "4. "
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "Manage chronic diseases (if any)"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "5. "
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "Promote general health and wellness"
                    )
                }
            }
        }
    }
}