package ui.finding_whatmatters

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
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
import ui.comparing_options.ComparingOptionsIntroScreen

data class FindingWhatMattersScreen(
    val wrapContent: Boolean = false,
    val totalPoints: Int = 0
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.WHAT_MATTERS
    private val screenTitle = option.title

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle, option.color) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevSection = true, prevAction = {
                        if (navigator.items.contains(ComparingOptionsIntroScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(ComparingOptionsIntroScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = false
                )
            }
        ) {
            BoxWithConstraints {
                val boxScope = this

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Two possible decisions that may apply to you",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Text(
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(8.dp).padding(top = 4.dp),
                        text = buildAnnotatedString {
                            append("After learning about both cancer follow-up care options, this section aims to help you find out")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(" what matters to you")
                            }
                            append(" when deciding on the type of care to take up.")
                        }
                    )

                    Image(
                        painter = painterResource("finding_whatmatters/checklist_phone.png"),
                        contentDescription = "What-Matters",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(top = 8.dp, start = 10.dp).fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Click on the following button to complete a short survey.",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Button(
                        onClick = { navigator.push(SurveyScreen()) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9DC3E6)),
                        modifier = Modifier.padding(top = 4.dp).fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Preference survey")
                    }

                    Text(
                        text = "The survey will give you a score. Click on the following button to find out what the score means.",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Button(
                        onClick = { navigator.push(FindingWhatMattersScoreScreen()) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9DC3E6)),
                        modifier = Modifier.padding(top = 4.dp).fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Understanding my score")
                    }

                }
            }
        }
    }
}