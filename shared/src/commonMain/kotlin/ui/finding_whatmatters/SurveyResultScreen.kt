package ui.finding_whatmatters

import HomeScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import model.HomeOptions
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

data class SurveyResultScreen(
    val score: Int,
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.WHAT_MATTERS
    private val screenTitle = option.title

    @Composable
    override fun Content() {
        val shouldShowScoreBar = remember { MutableTransitionState(false) }
        val scoreAlpha = remember { Animatable(0f) }
        val scoreTextAlpha = remember { Animatable(0f) }
        LaunchedEffect(key1 = "") {
            delay(100)
            shouldShowScoreBar.targetState = true
            scoreAlpha.animateTo(
                1f,
                tween(delayMillis = 1000, durationMillis = 500, easing = LinearEasing)
            )
            scoreTextAlpha.animateTo(1f, tween(durationMillis = 500, easing = LinearEasing))
        }

        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle, option.color) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = false,
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = false
                )
            }
        ) {
            BoxWithConstraints {
                val boxScope = this

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(it).fillMaxWidth().verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "Your score is:",
                        Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "$score %",
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                            .drawBehind {
                                drawCircle(
                                    color = Color(0xFF9DC3E6),
                                    radius = size.maxDimension * .7f,
                                    alpha = scoreAlpha.value
                                )
                            }
                    )

                    Text(
                        text = (if (score == 50) "This indicates you are neutral towards both options."
                        else if (score > 50) "Score of more than 50% indicates increasing preference for shared care"
                        else "Score of less than 50% indicates increasing preference for usual care"),
                        modifier = Modifier
                            .padding(8.dp, 20.dp)
                            .alpha(scoreTextAlpha.value),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )

                    AnimatedVisibility(
                        visibleState = shouldShowScoreBar,
                        modifier = Modifier.padding(top = 16.dp),
                        enter = expandHorizontally(
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = LinearEasing
                            )
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(12.dp)
                                .width((boxScope.maxWidth.value * score * .01).dp)
                                .align(Alignment.Start)
                                .background(Color(230, 117, 194))
                        )
                    }

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        Color(164, 149, 146),
                                        Color(233, 151, 135)
                                    )
                                )
                            )
                    ) {
                        Text(
                            text = "USUAL CARE",
                            color = Color.White,
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.CenterStart),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "SHARED CARE",
                            color = Color.White,
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.CenterEnd),
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = { navigator.push(FindingWhatMattersScoreScreen()) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9DC3E6)),
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth(0.6f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Understanding my score")
                    }

                    Button(
                        onClick = { navigator.pop() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.surface,
                            contentColor = MaterialTheme.colors.onSurface
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 12.dp)
                            .fillMaxWidth(0.6f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Retake the survey", color = MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}