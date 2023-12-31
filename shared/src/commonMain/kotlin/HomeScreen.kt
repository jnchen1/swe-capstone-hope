import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.HomeOptions.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.comparing_options.ComparingOptionsIntroScreen
import ui.conclusion.ConclusionScreen
import ui.emotional_effect.EmotionalEffectFirstScreen
import ui.finding_whatmatters.FindingWhatMattersScreen
import ui.followup_care.FollowupCareIntroScreen
import ui.more_information.MoreInformationFirstScreen
import ui.physical_effect.PhysicalEffectIntroScreen
import ui.survivorship.WhatIsSurvivorshipFirstScreen

data class HomeScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "HOME"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = navigator::pop
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource("home_person_image.png"),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(240.dp)
                        .padding(top = 8.dp).align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "What to do?",
                    style = MaterialTheme.typography.h2
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = buildAnnotatedString {
                        append("If this is your ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("first time")
                        }
                        append(" using the decision aid, please read in the order listed.")
                    }
                )

                entries.forEach { option ->
                    Card(
                        backgroundColor = option.color,
                        shape = RoundedCornerShape(12.dp),
                        elevation = 4.dp,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).clickable {
                            when (option) {
                                WHAT_IS_SURVIVORSHIP -> navigator.push(WhatIsSurvivorshipFirstScreen())
                                PHYSICAL_EFFECT -> navigator.push(PhysicalEffectIntroScreen())
                                EMOTIONAL_EFFECT -> navigator.push(EmotionalEffectFirstScreen())
                                FOLLOWUP_CARE -> navigator.push(FollowupCareIntroScreen())
                                COMPARE_CARE -> navigator.push(ComparingOptionsIntroScreen())
                                WHAT_MATTERS -> navigator.push(FindingWhatMattersScreen())
                                CONCLUSION -> navigator.push(ConclusionScreen())
                                RESOURCES -> navigator.push(MoreInformationFirstScreen())
                            }
                        }
                    ) {
                        Text(option.text, color = Color.Black, modifier = Modifier.padding(12.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
