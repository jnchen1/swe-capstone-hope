package ui.followup_care

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import ui.emotional_effect.EmotionalEffectSecondScreen

data class FollowupCareIntroScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.FOLLOWUP_CARE
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
                        if (navigator.items.contains(EmotionalEffectSecondScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(EmotionalEffectSecondScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true,
                    nextAction = { navigator.push(FollowupCareOptionScreen()) }
                )
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
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 4.dp, start = 16.dp, bottom = 8.dp),
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