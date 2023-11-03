package ui.more_information

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
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
                BottomNavigation {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
                    ) {
                        val textSize = MaterialTheme.typography.button.fontSize
                        val textLayout = rememberTextMeasurer().measure(
                            text = "Previous section",
                            style = MaterialTheme.typography.button,
                            overflow = TextOverflow.Clip
                        )

                        Button(
                            onClick = { navigator.push(MoreInformationThirdScreen()) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                Icons.Rounded.FirstPage,
                                contentDescription = "Previous section"
                            )

                            BoxWithConstraints {
                                val boxScope = this
                                val sizeInDp = with(LocalDensity.current) {
                                    textLayout.size.width.toDp()
                                }

                                Text(
                                    text = "Previous section",
                                    fontSize = if (boxScope.maxWidth - 4.dp < sizeInDp) textSize * .8 else textSize,
                                    overflow = TextOverflow.Clip,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }

                        }

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )


                        Spacer(modifier = Modifier.weight(1f))

                    }
                }
            }
        ) {
            BoxWithConstraints {
                val boxScope = this

                Column(
                    modifier = Modifier.padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Image(
                        painter = painterResource("more_information/support_groups.png"),
                        contentDescription = "support_groups",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp)
                    )

                    Text(
                        text = "Support groups and other services",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(4.dp)
                    )

                    Image(
                        painter = painterResource("more_information/nccs.png"),
                        contentDescription = "nccs",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 100.dp).fillMaxWidth()
                    )

                    HyperlinkText(
                        fullText = "Support groups" +
                            "\n" +
                            "Psychosocial services" +
                            "\n" +
                            "Cancer rehabilitation" +
                            "\n" +
                            "Financial aid",
                        hyperLinks = mutableMapOf(
                            "Support groups" to "https://www.nccs.com.sg/patient-care/specialties-services/pages/support-groups.aspx",
                            "Psychosocial services" to "https://www.nccs.com.sg/patient-care/specialties-services/psychosocial-oncology-2",
                            "Cancer rehabilitation" to "https://www.nccs.com.sg/patient-care/Pages/Cancer-rehabilitation.aspx",
                            "Financial aid" to "https://www.nccs.com.sg/patient-care/seeing-a-specialist/payment-financial-aid"
                        )
                    )

                    Image(
                        painter = painterResource("more_information/ain.png"),
                        contentDescription = "ain",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 100.dp).fillMaxWidth()
                    )

                    HyperlinkText(
                        fullText = "Services provided by the AIN Society",
                        hyperLinks = mutableMapOf(
                            "Services provided by the AIN Society" to "https://www.ainsociety.org.sg/our-programmes-services/"
                        )
                    )

                    Image(
                        painter = painterResource("more_information/bcf.png"),
                        contentDescription = "bcf",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 100.dp).fillMaxWidth()
                    )

                    HyperlinkText(
                        fullText = "Services provided by the Breast Cancer Foundation",
                        hyperLinks = mutableMapOf(
                            "Services provided by the Breast Cancer Foundation" to "https://www.bcf.org.sg/our-services/#section-support-group"
                        )
                    )

                    Image(
                        painter = painterResource("more_information/scs.png"),
                        contentDescription = "scs",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 100.dp).fillMaxWidth()
                    )

                    HyperlinkText(
                        fullText = "Services provided by the Singapore Cancer Society",
                        hyperLinks = mutableMapOf(
                            "Services provided by the Singapore Cancer Society" to "https://www.singaporecancersociety.org.sg/"
                        )
                    )

                    Image(
                        painter = painterResource("more_information/365cps.png"),
                        contentDescription = "365cps",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.padding(horizontal = 100.dp).fillMaxWidth()
                    )

                    HyperlinkText(
                        fullText = "Services provided by the 365 Cancer Prevention Society" + "\n\n\n\n",
                        hyperLinks = mutableMapOf(
                            "Services provided by the 365 Cancer Prevention Society" to "https://www.365cps.org.sg/"
                        )
                    )

                }
            }
        }
    }
}