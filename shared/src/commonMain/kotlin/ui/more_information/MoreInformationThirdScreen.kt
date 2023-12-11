package ui.more_information

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

data class MoreInformationThirdScreen(
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
                    showPrevPage = true, prevAction = {
                        if (navigator.items.contains(MoreInformationSecondScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(MoreInformationSecondScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true, nextAction = { navigator.push(MoreInformationFourthScreen()) }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Image(
                    painter = painterResource("more_information/heart.png"),
                    contentDescription = "heart",
                    Modifier.padding(top = 8.dp).height(140.dp).align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.FillHeight
                )

                Text(
                    text = "Self-help resources",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(8.dp)
                )

                Row(Modifier.padding(horizontal = 8.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource("more_information/flag_singapore.png"),
                        contentDescription = "sg1",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    HyperlinkText(
                        fullText = "Coping with cancer and treatments",
                        hyperLinks = mutableMapOf(
                            "Coping with cancer and treatments" to "https://www.nccs.com.sg/patient-care/Pages/Coping-with-Cancer-and-treatments.aspx"
                        )
                    )
                }

                Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource("more_information/flag_singapore.png"),
                        contentDescription = "sg2",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    HyperlinkText(
                        fullText = "Exercise during & after treatment",
                        hyperLinks = mutableMapOf(
                            "Exercise during & after treatment" to "https://www.nccs.com.sg/patient-care/Pages/Exercise-and-healthy-living.aspx"
                        )
                    )
                }

                Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource("more_information/flag_singapore.png"),
                        contentDescription = "sg3",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    HyperlinkText(
                        fullText = "How to quit smoking?",
                        hyperLinks = mutableMapOf(
                            "How to quit smoking?" to "https://www.healthhub.sg/live-healthy/1766/build-your-quitting-game-plan"
                        )
                    )
                }

                Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource("more_information/flag_singapore.png"),
                        contentDescription = "sg4",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    HyperlinkText(
                        fullText = "Cervical cancer screening",
                        hyperLinks = mutableMapOf(
                            "Cervical cancer screening" to "https://www.healthhub.sg/a-z/costs-and-financing/cervical-cancer-screening-subsidies-in-singapore"
                        )
                    )
                }

                Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource("more_information/flag_singapore.png"),
                        contentDescription = "sg5",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    HyperlinkText(
                        fullText = "Colorectal cancer screening",
                        hyperLinks = mutableMapOf(
                            "Colorectal cancer screening" to "https://www.healthhub.sg/live-healthy/106/screening_colorectal_cancer_nuhs"
                        )
                    )
                }

                Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource("more_information/flag_singapore.png"),
                        contentDescription = "sg6",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    HyperlinkText(
                        fullText = "Breast self-examination",
                        hyperLinks = mutableMapOf(
                            "Breast self-examination" to "https://www.singaporecancersociety.org.sg/get-screened/breast-cancer/breast-self-examination.html"
                        )
                    )
                }

                Row(Modifier.padding(horizontal = 8.dp).padding(top = 4.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource("more_information/flag_uk.png"),
                        contentDescription = "uk1",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    HyperlinkText(
                        modifier = Modifier.padding(bottom = 8.dp),
                        fullText = "Healthy eating after treatment",
                        hyperLinks = mutableMapOf(
                            "Healthy eating after treatment" to "https://breastcancernow.org/information-support/facing-breast-cancer/living-beyond-breast-cancer/your-body/healthy-eating-after-treatment-breast-cancer"
                        )
                    )
                }
            }
        }
    }
}