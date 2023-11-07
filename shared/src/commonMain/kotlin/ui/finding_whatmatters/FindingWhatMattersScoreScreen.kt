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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import ui.ThemeBottomNavigation
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
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = {
                        if (navigator.items.contains(SurveyScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(SurveyScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextSection = true, nextAction = { navigator.push(ConclusionScreen()) }
                )
            }
        ) {
            BoxWithConstraints {
                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "What does your score mean?",
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        "The score gives you an idea of where your preference is for the two options.",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                    )

                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                            .padding(top = 4.dp, bottom = 8.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
                                append("Note:")
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
                                style = MaterialTheme.typography.subtitle2
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
                                        style = MaterialTheme.typography.body1,
                                        fontWeight = FontWeight.Bold,
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
                                style = MaterialTheme.typography.subtitle2
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
                                style = MaterialTheme.typography.subtitle2
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
                        Surface(
                            color = Color(164, 149, 146),
                            shape = RoundedCornerShape(12.dp),
                            elevation = 0.dp,
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Text(
                                "I prefer continuing with usual care.",
                                color = Color.White,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth().weight(2.5f).align(Alignment.CenterVertically),
                            shape = RectangleShape
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

                        Surface(
                            color = Color(233, 151, 135),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Text(
                                "I am willing to try out shared care.",
                                color = Color.White,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}