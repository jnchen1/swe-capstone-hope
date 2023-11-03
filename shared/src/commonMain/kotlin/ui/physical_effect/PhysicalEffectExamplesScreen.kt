package ui.physical_effect

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LastPage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.HomeOptions
import model.PhysicalEffectInfo
import model.TherapyMedicineEffect
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeTopAppBar
import ui.emotional_effect.EmotionalEffectFirstScreen

data class PhysicalEffectExamplesScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.PHYSICAL_EFFECT
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
                BottomNavigation {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .padding(bottom = 4.dp)
                    ) {
                        Button(
                            onClick = {
                                if (navigator.items.contains(PhysicalEffectIntroScreen())) {
                                    navigator.pop()
                                } else {
                                    navigator.replace(PhysicalEffectIntroScreen())
                                }
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Back"
                            )
                            Text(text = "Back", modifier = Modifier.padding(start = 4.dp))
                        }

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = { navigator.push(EmotionalEffectFirstScreen()) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            BoxWithConstraints {
                                val boxScope = this

                                val textSize = MaterialTheme.typography.button.fontSize
                                val textLayout = rememberTextMeasurer().measure(
                                    text = "Next section",
                                    style = MaterialTheme.typography.button,
                                    overflow = TextOverflow.Clip
                                )
                                val sizeInDp = with(LocalDensity.current) {
                                    textLayout.size.width.toDp()
                                }
                                val textAndSize =
                                    if (boxScope.maxWidth < sizeInDp * 1.3f) Pair(
                                        "Next\nsection", textSize * .8
                                    )
                                    else Pair("Next section", textSize)

                                Text(
                                    text = textAndSize.first,
                                    fontSize = textAndSize.second,
                                    textAlign = TextAlign.End,
                                    overflow = TextOverflow.Clip,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                            }
                            Icon(Icons.Rounded.LastPage, contentDescription = "Next section")
                        }
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "What are some examples?"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Click")
                        }
                        append(" onto each icon and button to find out the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("possible long-term and late")
                        }
                        append(" physical effects associated with the treatment(s) you have received.")
                    }
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painterResource("physical_effect/pe_surgery.png"),
                        "surgery physical effect",
                        modifier = Modifier.fillMaxWidth(1f / 3).clickable {
                            navigator.push(PhysicalEffectInfoScreen(PhysicalEffectInfo.SURGERY))
                        }
                    )
                    Image(
                        painterResource("physical_effect/pe_radiotherapy.png"),
                        "radiotherapy physical effect",
                        modifier = Modifier.fillMaxWidth(1f / 2).clickable {
                            navigator.push(PhysicalEffectInfoScreen(PhysicalEffectInfo.RADIOTHERAPY))
                        }
                    )
                    Image(
                        painterResource("physical_effect/pe_chemo.png"),
                        "chemotherapy physical effect",
                        modifier = Modifier.fillMaxWidth().clickable {
                            navigator.push(PhysicalEffectInfoScreen(PhysicalEffectInfo.CHEMO))
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(.8f).align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                ) {
                    Image(
                        painterResource("physical_effect/pe_her2.png"),
                        "her2+ therapy physical effect",
                        modifier = Modifier.fillMaxWidth(1f / 2).clickable {
                            navigator.push(PhysicalEffectMedicineScreen(TherapyMedicineEffect.HER2))
                        }
                    )
                    Image(
                        painterResource("physical_effect/pe_hormonal.png"),
                        "hormonal therapy physical effect",
                        modifier = Modifier.fillMaxWidth(1f).clickable {
                            navigator.push(PhysicalEffectMedicineScreen(TherapyMedicineEffect.HORMONAL))
                        }
                    )
                }
            }
        }
    }
}