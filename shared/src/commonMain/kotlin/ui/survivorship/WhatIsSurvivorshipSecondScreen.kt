package ui.survivorship

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

data class WhatIsSurvivorshipSecondScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val option = HomeOptions.WHAT_IS_SURVIVORSHIP
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
                    showPrevPage = true, prevAction = {
                        if (navigator.items.contains(WhatIsSurvivorshipFirstScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(WhatIsSurvivorshipFirstScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true,
                    nextAction = { navigator.push(WhatIsSurvivorshipThirdScreen()) }
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
                        painter = painterResource("physical_effects_survivorship.png"),
                        contentDescription = "Physical effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)
                    )
                    Image(
                        painter = painterResource("emotional_effects_survivorship.png"),
                        contentDescription = "Emotional Effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)

                    )
                }
                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "Why is survivorship care important?"
                )
                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("After cancer diagnosis and treatment, breast cancer survivors may experience a ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("broad range of effects")
                        }
                        append(" years after treatment.\n\n")
                        append("Survivorship care ensures that all survivors receive the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("medical and psychosocial support")
                        }
                        append(" they need through the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("prevention, assessment and management")
                        }
                        append(" of such effects.")
                    }
                )

            }
        }
    }
}