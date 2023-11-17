package ui.finding_whatmatters

import HomeScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
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
        val textMeasurer = rememberTextMeasurer()
        val density = LocalDensity.current

        val scoreAlpha = remember { Animatable(0f) }
        val leftTextAlpha = remember { Animatable(0f) }
        val shouldShowLeftArrow = remember { MutableTransitionState(false) }
        val rightTextAlpha = remember { Animatable(0f) }
        val shouldShowRightArrow = remember { MutableTransitionState(false) }

        LaunchedEffect(key1 = "") {
            delay(100)
            scoreAlpha.animateTo(1f, tween(durationMillis = 500, easing = LinearEasing))
            leftTextAlpha.animateTo(1f, tween(500, 500, easing = LinearEasing))
            shouldShowRightArrow.targetState = true
            rightTextAlpha.animateTo(1f, tween(500, 500, easing = LinearEasing))
        }

        LifecycleEffect(
            onStarted = {
                println("Navigator: Start screen $screenTitle")
                shouldShowLeftArrow.targetState = true
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
                    showPrevPage = true, prevAction = { navigator.pop() },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextSection = true, nextAction = { navigator.push(ConclusionScreen()) }
                )
            }
        ) {
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
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Note:")
                        }
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append(" This is just a guide and is not meant to dictate which option you should eventually choose.")
                        }
                    }
                )

                BoxWithConstraints(
                    Modifier.fillMaxWidth().padding(horizontal = 8.dp).padding(top = 20.dp, bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(end = 8.dp).width(maxWidth * .3f).align(Alignment.CenterStart)) {
                        Text(
                            text = "Score of less than 50% indicates increasing preference for usual care",
                            modifier = Modifier.padding(8.dp).alpha(leftTextAlpha.value),
                            style = MaterialTheme.typography.subtitle2
                        )

                        AnimatedVisibility(
                            visibleState = shouldShowLeftArrow,
                            enter = expandHorizontally(
                                expandFrom = Alignment.End,
                                animationSpec = tween(
                                    delayMillis = 600,
                                    durationMillis = 500,
                                    easing = LinearEasing
                                )
                            ),
                        ) {
                            Image(
                                painter = painterResource("finding_whatmatters/arrow_left.png"),
                                contentDescription = "arrow_left",
                                modifier = Modifier.fillMaxWidth().height(80.dp)
                            )
                        }
                    }

                    Column(
                        Modifier.width(maxWidth * .3f).align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "50%",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.drawBehind {
                                drawCircle(
                                    color = Color(0xFF9DC3E6),
                                    radius = size.maxDimension * .8f,
                                    alpha = scoreAlpha.value
                                )
                            }
                        )
                        Text(
                            text = "This indicates you are neutral towards both options.",
                            modifier = Modifier.padding(top = 24.dp).alpha(scoreAlpha.value),
                            style = MaterialTheme.typography.subtitle2
                        )
                    }

                    Column(
                        modifier = Modifier.padding(start = 8.dp).width(maxWidth * .3f).align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = "Score of more than 50% indicates increasing preference for shared care",
                            modifier = Modifier.padding(8.dp).alpha(rightTextAlpha.value),
                            style = MaterialTheme.typography.subtitle2
                        )

                        AnimatedVisibility(
                            visibleState = shouldShowRightArrow,
                            enter = expandHorizontally(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearEasing
                                )
                            ),
                        ) {
                            Image(
                                painter = painterResource("finding_whatmatters/arrow_right.png"),
                                contentDescription = "arrow_right",
                                modifier = Modifier.fillMaxWidth().height(80.dp)
                            )
                        }
                    }
                }

                BoxWithConstraints(modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth()) {
                    val usualCareText = "I prefer continuing with usual care."
                    val sharedCareText = "I am willing to try out shared care."

                    val usualTextLayout = textMeasurer.measure(
                        text = usualCareText,
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Clip,
                        constraints = Constraints.fixedWidth((maxWidth.value / 3).toInt())
                    )
                    val sharedTextLayout = textMeasurer.measure(
                        text = usualCareText,
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Clip,
                        constraints = Constraints.fixedWidth((maxWidth.value / 3).toInt())
                    )

                    val boxHeight = with(density) {
                        val layoutHeight =
                            usualTextLayout.size.height.coerceAtLeast(sharedTextLayout.size.height)
                        MaterialTheme.typography.body2.fontSize.times(5f).toDp()
                            .coerceAtLeast(layoutHeight.toDp())
                    }

                    Surface(
                        color = Color(164, 149, 146),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .width(maxWidth / 3)
                            .height(boxHeight)
                    ) {
                        Text(
                            usualCareText,
                            color = Color.White,
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(8.dp)
                                .wrapContentHeight()
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(boxHeight * .5f),
                        shape = RectangleShape,
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
                                modifier = Modifier
                                    .width(this@BoxWithConstraints.maxWidth / 3)
                                    .align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Surface(
                        color = Color(233, 151, 135),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .width(maxWidth / 3)
                            .height(boxHeight)
                    ) {
                        Text(
                            sharedCareText,
                            color = Color.White,
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(8.dp)
                                .wrapContentHeight()
                        )
                    }
                }
            }
        }
    }
}