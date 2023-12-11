package ui.comparing_options

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LastPage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.finding_whatmatters.FindingWhatMattersScreen
import ui.followup_care.FollowupCareScheduleScreen

data class ComparingOptionsIntroScreen(
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
                    showPrevSection = true, prevAction = {
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
            BoxWithConstraints{
                val boxScope = this
                val isPortrait = boxScope.maxHeight > boxScope.maxWidth

                if (isPortrait) {
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
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Image(
                                    painter = painterResource("comparing_options/comparing_care_question_marks.png"),
                                    contentDescription = "Health Care Professionals not knowing each other",
                                    modifier = Modifier.fillMaxWidth(0.95f).padding(top = 8.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                                    text = buildAnnotatedString {
                                        append("Health care professionals caring for you usually do not know one another ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("personally")
                                        }
                                    }
                                )
                                Row {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_gp_doctor.png"),
                                        contentDescription = "Doctor and GP",
                                        modifier = Modifier.fillMaxWidth(0.30f).padding(10.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
                                        text = buildAnnotatedString {
                                            append("You ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("may or may not")
                                            }
                                            append(" see the ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("same")
                                            }
                                            append(" GP or polyclinic doctor")
                                        }
                                    )
                                }
                                Row {

                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically).weight(0.65f),
                                        text = buildAnnotatedString {
                                            append("You will need to ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("walk-in")
                                            }
                                            append(" to retail stores to consult a community pharmacist")

                                        }
                                    )
                                    Image(
                                        painter = painterResource("comparing_options/comparing_pharmacist.png"),
                                        contentDescription = "Pharmacist",
                                        modifier = Modifier.fillMaxWidth(0.20f).padding(end=10.dp).weight(0.20f).align(Alignment.CenterVertically),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }

                            }
                        }

                        Button(
                            enabled = false,
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                        ) {
                            Text("Who is caring for you?")
                        }
                        Button(
                            enabled = true,
                            onClick = { navigator.push(ComparingOptionsInformationSharedScreen()) },
                            colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                        ) {
                            Text("How is information about you shared?")
                        }
                        Button(
                            enabled = true,
                            onClick = { navigator.push(ComparingOptionsCareScheduleScreen()) },
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
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Image(
                                    painter = painterResource("comparing_options/comparing_care_team.png"),
                                    contentDescription = "Health Care Professionals being a team",
                                    modifier = Modifier.fillMaxWidth(0.85f).padding(top = 8.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                                    text = buildAnnotatedString {
                                        append("Health care professionals caring for you in a team are ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("contactable")
                                        }
                                        append(" by one another")
                                    }
                                )
                                Row {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_doctor.png"),
                                        contentDescription = "Doctor and GP",
                                        modifier = Modifier.fillMaxWidth(0.20f).padding(10.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
                                        text = buildAnnotatedString {
                                            append("You usually see the ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("same")
                                            }
                                            append(" polyclinic family physician who is ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("trained")
                                            }
                                            append(" to care for cancer survivors")
                                        }
                                    )
                                }
                                Row {

                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(start = 8.dp, top = 8.dp).align(Alignment.CenterVertically).weight(0.65f),
                                        text = buildAnnotatedString {
                                            append("A community pharmacist ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("trained")
                                            }
                                            append(" to care for cancer survivors will ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("actively call")
                                            }
                                            append(" you regularly to check-in")
                                        }
                                    )
                                    Image(
                                        painter = painterResource("comparing_options/comparing_pharmacist.png"),
                                        contentDescription = "Pharmacist",
                                        modifier = Modifier.fillMaxWidth(0.20f).padding(end=15.dp).weight(0.20f).align(Alignment.CenterVertically),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }

                            }
                        }

                    }
                } else{
                    Row(
                        modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            elevation = 2.dp,
                            modifier = Modifier.padding(10.dp).weight(0.33f),
                            shape = RoundedCornerShape(20.dp),
                            backgroundColor = Color.White
                        ) {
                            Column(
                                modifier = Modifier.padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(0.6f).clip(RoundedCornerShape(20.dp))
                                        .background(Color(0xFFA49592))
                                ) {
                                    Text(
                                        text = "Usual Care",
                                        style = MaterialTheme.typography.h2,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Image(
                                    painter = painterResource("comparing_options/comparing_care_question_marks.png"),
                                    contentDescription = "Health Care Professionals not knowing each other",
                                    modifier = Modifier.fillMaxWidth(0.95f).padding(top = 8.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                                    text = buildAnnotatedString {
                                        append("Health care professionals caring for you usually do not know one another ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("personally")
                                        }
                                    }
                                )
                                Row {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_gp_doctor.png"),
                                        contentDescription = "Doctor and GP",
                                        modifier = Modifier.fillMaxWidth(0.50f).padding(10.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
                                        text = buildAnnotatedString {
                                            append("You ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("may or may not")
                                            }
                                            append(" see the ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("same")
                                            }
                                            append(" GP or polyclinic doctor")
                                        }
                                    )
                                }
                                Row {

                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically).weight(0.55f),
                                        text = buildAnnotatedString {
                                            append("You will need to ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("walk-in")
                                            }
                                            append(" to retail stores to consult a community pharmacist")
                                        }
                                    )
                                    Image(
                                        painter = painterResource("comparing_options/comparing_pharmacist.png"),
                                        contentDescription = "Pharmacist",
                                        modifier = Modifier.fillMaxWidth(0.20f).padding(end=10.dp).weight(0.40f).align(Alignment.CenterVertically),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }

                            }
                        }

                        Column(modifier = Modifier.padding(5.dp).weight(0.33f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly){
                            Button(
                                enabled = false,
                                onClick = {},
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
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text("How is information about you shared?")
                            }

                            Button(
                                enabled = true,
                                onClick = {
                                    if (navigator.items.contains(ComparingOptionsCareScheduleScreen())) {
                                        navigator.pop()
                                    } else {
                                        navigator.replace(ComparingOptionsCareScheduleScreen())
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                                modifier = Modifier.padding(4.dp)
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
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text("Cost?")
                            }

                        }

                        Card(
                            elevation = 2.dp,
                            modifier = Modifier.padding(10.dp).weight(0.33f),
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
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Image(
                                    painter = painterResource("comparing_options/comparing_care_team.png"),
                                    contentDescription = "Health Care Professionals being a team",
                                    modifier = Modifier.fillMaxWidth(0.85f).padding(top = 8.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                                    text = buildAnnotatedString {
                                        append("Health care professionals caring for you in a team are ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("contactable")
                                        }
                                        append(" by one another")
                                    }
                                )
                                Row {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_doctor.png"),
                                        contentDescription = "Doctor and GP",
                                        modifier = Modifier.fillMaxWidth(0.37f).padding(10.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
                                        text = buildAnnotatedString {
                                            append("You usually see the ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("same")
                                            }
                                            append(" polyclinic family physician who is ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("trained")
                                            }
                                            append(" to care for cancer survivors")
                                        }
                                    )
                                }
                                Row {

                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(start = 8.dp, top = 8.dp).align(Alignment.CenterVertically).weight(0.55f),
                                        text = buildAnnotatedString {
                                            append("A community pharmacist ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("trained")
                                            }
                                            append(" to care for cancer survivors will ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("actively call")
                                            }
                                            append(" you regularly to check-in")
                                        }
                                    )
                                    Image(
                                        painter = painterResource("comparing_options/comparing_pharmacist.png"),
                                        contentDescription = "Pharmacist",
                                        modifier = Modifier.fillMaxWidth(0.20f).padding(end=15.dp).weight(0.40f).align(Alignment.CenterVertically),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }

                            }
                        }

                    }
                }
            }

        }
    }
}