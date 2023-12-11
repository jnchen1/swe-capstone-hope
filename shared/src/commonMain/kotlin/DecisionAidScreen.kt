import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

data class DecisionAidScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "DECISION AID"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        var shouldShowGuide by remember { mutableStateOf(false) }
        val barGuideAlpha = remember { Animatable(0f) }
        val backGuideAlpha = remember { Animatable(0f) }
        val nextGuideAlpha = remember { Animatable(0f) }
        val continueGuideAlpha = remember { Animatable(0f) }
        val animationSpec = tween<Float>(
            durationMillis = 500,
            easing = LinearEasing
        )

        if (shouldShowGuide) {
            LaunchedEffect("") {
                continueGuideAlpha.animateTo(0f)
                barGuideAlpha.animateTo(1f, animationSpec = animationSpec)
                delay(1500)
                barGuideAlpha.animateTo(0f)
                backGuideAlpha.animateTo(1f, animationSpec = animationSpec)
                delay(1500)
                backGuideAlpha.animateTo(0f)
                nextGuideAlpha.animateTo(1f, animationSpec = animationSpec)
                delay(1500)
                nextGuideAlpha.animateTo(0f)
                continueGuideAlpha.animateTo(1f, animationSpec = animationSpec)
                shouldShowGuide = false
            }
        }

        LifecycleEffect(
            onStarted = {
                println("Navigator: Start screen $screenTitle")
            },
            onDisposed = {
                println("Navigator: Dispose screen $screenTitle")
            }
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
            BoxWithConstraints(modifier = Modifier.padding(it).fillMaxSize()) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp)
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
                        text = "How to Use and Navigate?",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "This app works in both portrait and landscape modes. Simply rotate your device and observe the change.",
                        modifier = Modifier.padding(8.dp, 4.dp)
                    )

                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "Clickable parts indicated by",
                        modifier = Modifier.padding(horizontal = 8.dp).padding(bottom = 4.dp)
                    )

                    Button(
                        onClick = { shouldShowGuide = true },
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colors.secondary,
                            disabledBackgroundColor = MaterialTheme.colors.secondary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Tap here for navigation guide")
                    }
                }

                Image(
                    painter = painterResource("guide_nav_bar.png"),
                    contentDescription = "guide_nav_bar",
                    modifier = Modifier.fillMaxWidth(0.4f).align(Alignment.BottomCenter).alpha(barGuideAlpha.value)
                )

                Image(
                    painter = painterResource("guide_back.png"),
                    contentDescription = "guide_back",
                    modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.BottomStart).alpha(backGuideAlpha.value)
                )

                Image(
                    painter = painterResource("guide_next.png"),
                    contentDescription = "guide_next",
                    modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.BottomEnd).alpha(nextGuideAlpha.value)
                )

                Image(
                    painter = painterResource("guide_continue.png"),
                    contentDescription = "guide_continue",
                    modifier = Modifier.fillMaxWidth(0.3f).align(Alignment.BottomEnd).alpha(continueGuideAlpha.value)
                )
            }
        }
    }
}