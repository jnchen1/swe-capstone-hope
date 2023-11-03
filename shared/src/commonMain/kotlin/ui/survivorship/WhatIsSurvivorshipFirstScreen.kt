package ui.survivorship

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

data class WhatIsSurvivorshipFirstScreen(
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
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true,
                    nextAction = { navigator.push(WhatIsSurvivorshipSecondScreen()) }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("Cancer survivorship ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("starts")
                        }
                        append(" from the moment you are diagnosed with cancer and continues even after treatment to the end of life.")
                    }
                )

                Image(
                    painter = painterResource("we_are_all_survivors_survivorship.png"),
                    contentDescription = "Survivorship infographic",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )
            }
        }
    }
}