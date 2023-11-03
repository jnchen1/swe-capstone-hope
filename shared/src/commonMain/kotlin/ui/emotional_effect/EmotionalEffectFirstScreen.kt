package ui.emotional_effect

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.physical_effect.PhysicalEffectExamplesScreen

data class EmotionalEffectFirstScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.EMOTIONAL_EFFECT
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
                        if (navigator.items.contains(PhysicalEffectExamplesScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(PhysicalEffectExamplesScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true,
                    nextAction = { navigator.push(EmotionalEffectSecondScreen()) }
                )
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
                        painter = painterResource("emotional_effects_1.png"),
                        contentDescription = "Emotional effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)
                    )

                }

                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "Survivors may still feel distressed"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("You may feel emotions such as ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("depression, anxiety, worry, or fear ")
                        }
                        append("even after treatment due to worry of cancer returning ")
                        append("financial concerns, changes in physical appearances etc. ")
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                )

                {
                    Image(
                        painter = painterResource("emotional_effects_2.png"),
                        contentDescription = "Emotional Effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)

                    )
                    Image(
                        painter = painterResource("emotional_effects_3.png"),
                        contentDescription = "Emotional Effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)

                    )

                }

                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "Find your emotional support"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("It is important for you to identify someone who can support you ")
                        append("emotionally. He/she can be anyone-a ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("family member, friend, ")
                        }
                        append("or ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("other cancer survivors. ")
                        }
                    }
                )

            }
        }
    }
}