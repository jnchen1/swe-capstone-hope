package ui.followup_care

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import model.HealthcareProfessional.*
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

@OptIn(ExperimentalResourceApi::class)
data class FollowupCareProfessionalScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.FOLLOWUP_CARE
    private val screenTitle = option.title

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
                        if (navigator.items.contains(FollowupCareOptionScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(FollowupCareOptionScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true, nextAction = { navigator.push(FollowupCareCommunicationScreen()) }
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
                        text = "A. How do health care professionals care for you under each follow-up care option?",
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Tap on each image below to find out how each of the health care professionals care for you under ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("usual care")
                            }
                            append(" and ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("shared care.")
                            }
                        },
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                    )

                    val ratio = with(painterResource(ONCOLOGIST.image)) {
                        intrinsicSize.width / intrinsicSize.height
                    }
                    val imageWidth = (143.dp / ratio).value
                    if (imageWidth * 2.25 + 24.dp.value > boxScope.maxWidth.value) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            ONCOLOGIST.run {
                                Image(
                                    painterResource(image),
                                    name,
                                    Modifier.height(200.dp).clickable {
                                        navigator.push(FollowupCareProfessionalDetailsScreen(this))
                                    },
                                    contentScale = ContentScale.FillHeight
                                )
                            }

                            NURSE.run {
                                Image(
                                    painterResource(image),
                                    name,
                                    Modifier.height(200.dp).clickable {
                                        navigator.push(FollowupCareProfessionalDetailsScreen(this))
                                    },
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            DOCTOR.run {
                                Image(
                                    painterResource(image),
                                    name,
                                    Modifier.height(200.dp).clickable {
                                        navigator.push(FollowupCareProfessionalDetailsScreen(this))
                                    },
                                    contentScale = ContentScale.FillHeight
                                )
                            }

                            PHARMACIST.run {
                                Image(
                                    painterResource(image),
                                    name,
                                    Modifier.height(200.dp).padding(start = 12.dp).clickable {
                                        navigator.push(FollowupCareProfessionalDetailsScreen(this))
                                    },
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                        }
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(8.dp, 16.dp).wrapContentWidth()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            items(entries) { person ->
                                Image(
                                    painterResource(person.image),
                                    person.name,
                                    Modifier.height(200.dp).clickable {
                                        navigator.push(FollowupCareProfessionalDetailsScreen(person))
                                    },
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}