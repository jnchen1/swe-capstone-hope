package ui.more_information

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import ui.conclusion.ConclusionScreen

data class MoreInformationFirstScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.RESOURCES
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
                        if (navigator.items.contains(ConclusionScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(ConclusionScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true, nextAction = { navigator.push(MoreInformationSecondScreen()) }
                )
            }
        ) {
            BoxWithConstraints {
                val boxScope = this

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Image(
                        painter = painterResource("home_person_image.png"),
                        contentDescription = "home_person",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.height(140.dp).align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Life After Cancer",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(8.dp, 4.dp)
                    )

                    Row(Modifier.padding(horizontal = 8.dp).fillMaxWidth()) {
                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa_1",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        HyperlinkText(
                            fullText = "Follow-up care after breast cancer treatment",
                            hyperLinks = mapOf(
                                "Follow-up care after breast cancer treatment" to "https://www.cancer.org/cancer/breast-cancer/living-as-a-breast-cancer-survivor/follow-up-care-after-breast-cancer-treatment.html"
                            )
                        )
                    }

                    Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                        Image(
                            painter = painterResource("more_information/flag_singapore.png"),
                            contentDescription = "sg_1",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Understanding breast cancer",
                            hyperLinks = mapOf(
                                "Understanding breast cancer" to "https://www.healthhub.sg/a-z/diseases-and-conditions/20/breastcancer#1"
                            )
                        )
                    }

                    Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                        Image(
                            painter = painterResource("more_information/flag_singapore.png"),
                            contentDescription = "sg_2",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Cancer survivorship – more than just surviving cancer",
                            hyperLinks = mapOf(
                                "Cancer survivorship – more than just surviving cancer" to "https://www.singhealth.com.sg/news/medical-news/cancer-survivorship"
                            )
                        )
                    }

                    Image(
                        painter = painterResource("ribbon_survivorship.png"),
                        contentDescription = "ribbon_survivorship",
                        modifier = Modifier.size(140.dp).align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Return of cancer and second cancers",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(8.dp)
                    )

                    Row(Modifier.padding(horizontal = 8.dp).fillMaxWidth()) {

                        Image(
                            painter = painterResource("more_information/flag_singapore.png"),
                            contentDescription = "sg_3",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Will my cancer come back?",
                            hyperLinks = mapOf(
                                "Will my cancer come back?" to "https://www.nccs.com.sg/patient-care/Pages/Will-my-cancer-come-back.aspx"
                            )
                        )
                    }

                    Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa_3",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Understanding recurrence",
                            hyperLinks = mapOf(
                                "Understanding recurrence" to "https://www.cancer.org/treatment/survivorship-during-and-after-treatment/long-term-health-concerns/recurrence.html"
                            )
                        )
                    }

                    Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa_4",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Second cancers after breast cancer",
                            hyperLinks = mapOf(
                                "Second cancers after breast cancer" to "https://www.cancer.org/cancer/breast-cancer/living-as-a-breast-cancer-survivor/second-cancers-after-breast-cancer.html"
                            )
                        )
                    }

                    Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa_5",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Can I lower my risk of breast cancer coming back?",
                            hyperLinks = mapOf(
                                "Can I lower my risk of breast cancer coming back?" to "https://www.cancer.org/cancer/breast-cancer/living-as-a-breast-cancer-survivor/can-i-lower-my-risk-of-breast-cancer-progressing-or-coming-back.html"
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa_7",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Coping with fear of recurrence",
                            hyperLinks = mapOf(
                                "Coping with fear of recurrence" to "https://www.oncolink.org/support/practical-and-emotional/coping-communication-concerns/coping-with-fear-of-recurrence"
                            )
                        )
                    }

                    Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                        Image(
                            painter = painterResource("more_information/flag_uk.png"),
                            contentDescription = "uk_1",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        HyperlinkText(
                            modifier = Modifier.padding(bottom = 8.dp),
                            fullText = "Breast cancer recurrence symptoms",
                            hyperLinks = mapOf(
                                "Breast cancer recurrence symptoms" to "https://breastcancernow.org/information-support/facing-breast-cancer/living-beyond-breast-cancer/life-after-breast-cancer-treatment/coping-emotionally/breast-cancer-recurrence-symptoms"
                            )
                        )
                    }
                }
            }
        }
    }
}