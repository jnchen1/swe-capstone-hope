package ui.finding_whatmatters

import HomeScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LastPage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
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
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeTopAppBar
import ui.conclusion.ConclusionScreen

data class FindingWhatMattersScoreScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.WHAT_MATTERS
    private val screenTitle = option.title

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        var visible by remember {
            mutableStateOf(false)
        }

        val visibleState = remember { MutableTransitionState(false) }

        val animatedAlpha by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f,
            animationSpec = tween(
                delayMillis = 500,
                durationMillis = 1000,
                easing = LinearEasing
            ),
            label = "alpha"
        )
        val animatedAlpha1 by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f,
            animationSpec = tween(
                delayMillis = 2500,
                durationMillis = 1000,
                easing = LinearEasing
            ),
            label = "alpha"
        )
        val animatedAlpha2 by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f,
            animationSpec = tween(
                delayMillis = 4500,
                durationMillis = 1000,
                easing = LinearEasing
            ),
            label = "alpha"
        )


        LifecycleEffect(
            onStarted = {
                println("Navigator: Start screen $screenTitle")
                visible = true
                visibleState.targetState = true
            },
            onDisposed = {
                println("Navigator: Dispose screen $screenTitle")
            }
        )

        val navigator = LocalNavigator.currentOrThrow




        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle, option.color) },
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
                            onClick = { navigator.push(FindingWhatMattersScreen()) },
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
                            onClick = { navigator.push(ConclusionScreen()) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Next section",
                                overflow = TextOverflow.Clip
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
            BoxWithConstraints {
                val boxScope = this

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "What does your score mean?",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Text(
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(8.dp).padding(top = 4.dp),
                        text = buildAnnotatedString {
                            append("The score gives you an idea of where your preference is for the two options.")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                )
                            ) {
                                append("\nNote:")
                            }
                            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                append(" This is just a guide and is not meant to dictate which option you should eventually choose.")
                            }
                        }
                    )



                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(it).padding(horizontal = 8.dp).weight(1f)
                        ) {
                            Text(
                                text = "Score of less than 50% indicates increasing preference for usual care",
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                                    .padding(top = 4.dp)
                                    .graphicsLayer {
                                        alpha = animatedAlpha1
                                    },
                                style = MaterialTheme.typography.caption
                            )

                            AnimatedVisibility(
                                visibleState = visibleState,
                                enter = expandHorizontally(
                                    expandFrom = Alignment.End,
                                    animationSpec = tween(
                                        delayMillis = 2000,
                                        durationMillis = 1000,
                                        easing = LinearEasing
                                    )
                                ),
                            ) {
                                Image(
                                    painter = painterResource("finding_whatmatters/arrow_left.png"),
                                    contentDescription = "arrow_left",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }



                        Column(
                            modifier = Modifier.padding(it).weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            AnimatedVisibility(
                                visibleState = visibleState,
                                enter = scaleIn(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearEasing
                                    )
                                ),
                            ) {
                                Box(
                                    modifier = Modifier.size(50.dp).clip(CircleShape)
                                        .background(Color(157, 195, 230)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "50%",
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            Text(
                                text = "This indicates you are neutral towards both options.",
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                                    .graphicsLayer {
                                        alpha = animatedAlpha
                                    },
                                style = MaterialTheme.typography.caption
                            )

                        }

                        Column(
                            modifier = Modifier.padding(it).padding(horizontal = 8.dp).weight(1f)
                        ) {
                            Text(
                                text = "Score of more than 50% indicates increasing preference for shared care",
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                                    .padding(top = 4.dp).graphicsLayer {
                                        alpha = animatedAlpha2
                                    },
                                style = MaterialTheme.typography.caption
                            )

                            AnimatedVisibility(
                                visibleState = visibleState,
                                enter = expandHorizontally(
                                    animationSpec = tween(
                                        delayMillis = 4000,
                                        durationMillis = 1000,
                                        easing = LinearEasing
                                    )
                                ),
                            ) {
                                Image(
                                    painter = painterResource("finding_whatmatters/arrow_right.png"),
                                    contentDescription = "arrow_right",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                    ) {
                        Card(
                            backgroundColor = Color(164, 149, 146),
                            shape = RoundedCornerShape(12.dp),
                            elevation = 0.dp,
                            modifier = Modifier.fillMaxWidth().weight(1f)

                        ) {
                            Text(
                                "I prefer continuing with usual care.",
                                color = Color.White,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(12.dp)
                            )
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth().weight(2.5f)
                                .align(Alignment.CenterVertically),
                            shape = RectangleShape,
                            elevation = 0.dp
                        ) {
                            Box(
                                Modifier.background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color(164, 149, 146),
                                            Color(233, 151, 135)
                                        )
                                    )
                                )
                            ) {
                                Text(
                                    text = "PREFERENCE SCALE",
                                    color = Color.White,
                                    modifier = Modifier.padding(12.dp).align(Alignment.Center),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Card(
                            backgroundColor = Color(233, 151, 135),
                            shape = RoundedCornerShape(12.dp),
                            elevation = 0.dp,
                            modifier = Modifier.fillMaxWidth().weight(1f)

                        ) {
                            Text(
                                "I am willing to try out shared care.",
                                color = Color.White,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}