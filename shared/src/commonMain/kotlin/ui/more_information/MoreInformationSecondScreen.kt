package ui.more_information

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
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

data class MoreInformationSecondScreen(
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
                        if (navigator.items.contains(MoreInformationFirstScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(MoreInformationFirstScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true, nextAction = { navigator.push(MoreInformationThirdScreen()) }
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
                        painter = painterResource("more_information/5logo.png"),
                        contentDescription = "5logo",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp)
                    )

                    Text(
                        text = "Managing effects of cancer treatment",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(4.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

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

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa1",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Managing cancer-related side effects",
                            hyperLinks = mutableMapOf(
                                "Managing cancer-related side effects" to "https://www.cancer.org/treatment/treatments-and-side-effects/physical-side-effects.html"
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa2",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Menopausal hormone therapy after breast cancer",
                            hyperLinks = mutableMapOf(
                                "Menopausal hormone therapy after breast cancer" to "https://www.cancer.org/cancer/breast-cancer/living-as-a-breast-cancer-survivor/menopausal-hormone-therapy-after-breast-cancer.html"
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa3",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Pregnancy after breast cancer",
                            hyperLinks = mutableMapOf(
                                "Pregnancy after breast cancer" to "https://www.cancer.org/cancer/breast-cancer/living-as-a-breast-cancer-survivor/pregnancy-after-breast-cancer.html"
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource("more_information/flag_usa.png"),
                            contentDescription = "usa4",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Body image and sexuality after breast cancer",
                            hyperLinks = mutableMapOf(
                                "Body image and sexuality after breast cancer" to "https://www.cancer.org/cancer/breast-cancer/living-as-a-breast-cancer-survivor/body-image-and-sexuality-after-breast-cancer.html"
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource("more_information/flag_uk.png"),
                            contentDescription = "uk1",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Sex, intimacy and breast cancer",
                            hyperLinks = mutableMapOf(
                                "Sex, intimacy and breast cancer" to "https://breastcancernow.org/information-support/facing-breast-cancer/living-beyond-breast-cancer/your-body/sex-intimacy-breast-cancer"
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource("more_information/flag_uk.png"),
                            contentDescription = "uk2",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        HyperlinkText(
                            fullText = "Menopausal symptoms and breast cancer",
                            hyperLinks = mutableMapOf(
                                "Menopausal symptoms and breast cancer" to "https://breastcancernow.org/information-support/facing-breast-cancer/going-through-treatment-breast-cancer/side-effects/menopausal-symptoms-after-treatment"
                            )
                        )
                    }

                }
            }
        }
    }
}