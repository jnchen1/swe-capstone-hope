package ui.more_information

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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

data class MoreInformationFourthScreen(
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
                    showPrevPage = true,
                    prevAction = {
                        if (navigator.items.contains(MoreInformationThirdScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(MoreInformationThirdScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                )
            }
        ) {
            BoxWithConstraints(Modifier.padding(it).fillMaxSize()) {
                val isPortrait = maxHeight > maxWidth

                Column(Modifier.padding(horizontal = 8.dp).verticalScroll(rememberScrollState())) {
                    Image(
                        painter = painterResource("more_information/support_groups.png"),
                        contentDescription = "support_groups",
                        modifier = Modifier.size(140.dp).padding(8.dp).align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Support groups and other services",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(8.dp)
                    )

                    if (isPortrait) {
                        NccsAndAinLayout(Modifier.padding(horizontal = 8.dp))
                        FoundationAndSocietyLayout(Modifier.padding(horizontal = 8.dp).padding(bottom = 12.dp))
                    } else {
                        Row(Modifier.fillMaxWidth().wrapContentHeight()) {
                            NccsAndAinLayout(Modifier.padding(end = 8.dp).fillMaxWidth(.5f))
                            FoundationAndSocietyLayout(Modifier.padding(start = 8.dp, bottom = 12.dp).fillMaxWidth())
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun NccsAndAinLayout(modifier: Modifier) {
        Column(modifier) {
            Image(
                painter = painterResource("more_information/nccs.png"),
                contentDescription = "nccs",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(.5f)
            )

            HyperlinkText(
                Modifier.padding(start = 8.dp),
                fullText = "Support groups\n" +
                    "Psychosocial services\n" +
                    "Cancer rehabilitation\n" +
                    "Financial aid",
                hyperLinks = mapOf(
                    "Support groups" to "https://www.nccs.com.sg/patient-care/specialties-services/support-programmes",
                    "Psychosocial services" to "https://www.nccs.com.sg/patient-care/specialties-services/psychosocial-oncology-2",
                    "Cancer rehabilitation" to "https://www.nccs.com.sg/patient-care/Pages/Cancer-rehabilitation.aspx",
                    "Financial aid" to "https://www.nccs.com.sg/patient-care/specialties-services/financial-aid-practical-assistance"
                )
            )

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource("more_information/ain.png"),
                    contentDescription = "ain",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(.3f).padding(end = 8.dp)
                )

                HyperlinkText(
                    fullText = "Services provided by the AIN Society",
                    hyperLinks = mapOf(
                        "Services provided by the AIN Society" to "https://www.ainsociety.org.sg/our-programmes-services/"
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun FoundationAndSocietyLayout(modifier: Modifier) {
        Column(modifier) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource("more_information/bcf.png"),
                    contentDescription = "bcf",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(.3f).padding(end = 8.dp)
                )

                HyperlinkText(
                    fullText = "Services provided by the Breast Cancer Foundation",
                    hyperLinks = mapOf(
                        "Services provided by the Breast Cancer Foundation" to "https://www.bcf.org.sg/our-services/#section-support-group"
                    )
                )
            }

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource("more_information/scs.png"),
                    contentDescription = "scs",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(.3f).padding(end = 8.dp)
                )

                HyperlinkText(
                    fullText = "Services provided by the Singapore Cancer Society",
                    hyperLinks = mapOf(
                        "Services provided by the Singapore Cancer Society" to "https://www.singaporecancersociety.org.sg/"
                    )
                )
            }

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource("more_information/365cps.png"),
                    contentDescription = "365cps",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(.3f).padding(top = 12.dp, end = 8.dp)
                )

                HyperlinkText(
                    fullText = "Services provided by the 365 Cancer Prevention Society",
                    hyperLinks = mapOf(
                        "Services provided by the 365 Cancer Prevention Society" to "https://www.365cps.org.sg/"
                    )
                )
            }
        }
    }
}