package ui.comparing_options

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.finding_whatmatters.FindingWhatMattersScreen
import ui.followup_care.FollowupCareScheduleScreen

data class ComparingOptionsCareScheduleScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.COMPARE_CARE
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
                    showPrevSection = true,
                    prevAction = {
                        if (navigator.items.contains(FollowupCareScheduleScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(FollowupCareScheduleScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextSection = true, nextAction = { navigator.push(FindingWhatMattersScreen()) }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    elevation = 2.dp,
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.5f).clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFA49592))
                        ) {
                            Text(
                                text = "Usual Care",
                                style = MaterialTheme.typography.h2,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                            text = buildAnnotatedString {
                                append("In a year, you will usually see an oncologist once to twice a year")
                            }
                        )

                        Image(
                            painter = painterResource("comparing_options/comparing_usual_care_schedule.png"),
                            contentDescription = "Health Care Professionals not knowing each other",
                            modifier = Modifier.width(308.dp).height(118.dp).padding(top = 8.dp),
                            contentScale = ContentScale.FillHeight
                        )

                    }
                }

                Button(
                    enabled = true,
                    onClick = {
                        if (navigator.items.contains(ComparingOptionsIntroScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(ComparingOptionsIntroScreen())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ) {
                    Text("Who is caring for you?")
                }
                Button(
                    enabled = true,
                    onClick = {
                        if (navigator.items.contains(ComparingOptionsInformationSharedScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(ComparingOptionsInformationSharedScreen())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ) {
                    Text("How is information about you shared?")
                }
                Button(
                    enabled = false,
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ) {
                    Text("What is your follow-up schedule?")
                }
                Button(
                    enabled = true,
                    onClick = {
                        if (navigator.items.contains(ComparingOptionsCareCostScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(ComparingOptionsCareCostScreen())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ) {
                    Text("Cost?")
                }

                Card(
                    elevation = 2.dp,
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.5f).clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFE99787))
                        ) {
                            Text(
                                text = "Shared Care",
                                style = MaterialTheme.typography.h2,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                            text = buildAnnotatedString {
                                append("One of the visits to oncologist will be ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("merged")
                                }
                                append(" and ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("replaced")
                                }
                                append(" by a consult with the polyclinic family physician.")
                            }
                        )
                        Image(
                            painter = painterResource("comparing_options/comparing_shared_care_schedule.png"),
                            contentDescription = "Health Care Professionals being a team",
                            modifier = Modifier.width(308.dp).height(206.dp).padding(top = 8.dp),
                            contentScale = ContentScale.FillHeight
                        )
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                            text = buildAnnotatedString {
                                append("A community pharmacist will check-in ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("regularly")
                                }
                            }
                        )

                    }
                }

            }
        }
    }
}