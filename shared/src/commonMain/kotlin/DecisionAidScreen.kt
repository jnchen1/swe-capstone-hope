import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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

data class DecisionAidScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "DECISION AID"

    @OptIn(ExperimentalResourceApi::class, ExperimentalAnimationApi::class)
    @Composable
    override fun Content() {

        val visibleState = remember {
            MutableTransitionState(false)}

        val visibleState2 = remember {
            MutableTransitionState(false)}

        val visibleState3 = remember {
            MutableTransitionState(false)}

        val visibleState4 = remember {
            MutableTransitionState(false)}

        var visible by remember {
            mutableStateOf(false)
        }

        val animatedAlpha by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f,
            animationSpec = tween(
                delayMillis = 1000,
                durationMillis = 1000,
                easing = LinearEasing
            ),
            label = "alpha"
        )


        LifecycleEffect(
            onStarted = {
                println("Navigator: Start screen $screenTitle") },
            onDisposed = {
                println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow



        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = { navigator.push(DisclaimerScreen()) },
                    showNextPage = true, nextAction = { navigator.push(WhoIsThisForScreen()) }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "What is this?",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("A tool to help readers learn more about the importance of ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("cancer follow-up care")
                        }
                        append(" and to understand the currently ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("available options")
                        }
                        append(" in Singapore.")
                    }
                )

                Image(
                    painter = painterResource("thinking_decisionaid.png"),
                    contentDescription = "Image of thinking person",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(300.dp).align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "How to Navigate?",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    text = "Clickable parts indicated by",
                    modifier = Modifier.padding(horizontal = 8.dp).padding(vertical = 4.dp)
                )

                Button(
                    onClick = {
                        visibleState4.targetState = false
                        visibleState.targetState = true
                              },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colors.secondary,
                        disabledBackgroundColor = MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text("Click here for navigation guide")
                }

                AnimatedVisibility(
                    visibleState = visibleState,
                    enter = fadeIn(
                        animationSpec = tween(
                            delayMillis = 1000,
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            delayMillis = 1000,
                            durationMillis = 1000,
                            easing = LinearEasing
                        ),
                    )
                ) {
                    Image(
                        painter = painterResource("guide_nav_bar.png"),
                        contentDescription = "guide_nav_bar",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )

                    if(this.transition.currentState == this.transition.targetState) {
                        visibleState.targetState = false
                        visibleState2.targetState = true
                    }
                }


                AnimatedVisibility(
                    visibleState = visibleState2,
                    enter = fadeIn(
                        animationSpec = tween(
                            delayMillis = 3000,
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            delayMillis = 1000,
                            durationMillis = 1000,
                            easing = LinearEasing
                        ),
                    )
                ) {
                    Image(
                        painter = painterResource("guide_back.png"),
                        contentDescription = "guide_back",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth(0.4f)
                    )

                    if(this.transition.currentState == this.transition.targetState) {
                        visibleState2.targetState = false
                        visibleState3.targetState = true
                    }
                }

                AnimatedVisibility(
                    visibleState = visibleState3,
                    modifier = Modifier.align(Alignment.End),
                    enter = fadeIn(
                        animationSpec = tween(
                            delayMillis = 3000,
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            delayMillis = 1000,
                            durationMillis = 1000,
                            easing = LinearEasing
                        ),
                    )
                ) {
                    Image(
                        painter = painterResource("guide_next.png"),
                        contentDescription = "guide_next",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth(0.4f).align(Alignment.End)
                    )

                    if(this.transition.currentState == this.transition.targetState) {
                        visibleState3.targetState = false
                        visibleState4.targetState = true
                    }
                }

                AnimatedVisibility(
                    visibleState = visibleState4,
                    modifier = Modifier.align(Alignment.End),
                    enter = fadeIn(
                        animationSpec = tween(
                            delayMillis = 3000,
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    ),
                ) {
                    Image(
                        painter = painterResource("guide_continue.png"),
                        contentDescription = "guide_next",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.End)
                    )
                }
            }
        }
    }
}