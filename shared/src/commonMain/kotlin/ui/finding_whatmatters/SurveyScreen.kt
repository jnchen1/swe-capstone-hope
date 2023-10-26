package ui.finding_whatmatters

import HomeScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi


data class SurveyScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "PREFERENCE SURVEY"





    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        val totalPoints = remember { mutableStateOf(0) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = screenTitle,
                            style = MaterialTheme.typography.h1,
                            color = Color(0xFF9DC3E6),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            },
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
                            onClick = { navigator.push(FindingWhatMattersScreen())
                            },
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
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "What matters to you?\nPlease select the statement that best applies to you\n\n",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )




                    val q1option = listOf(
                        "The cost of visits does not matter much to me.",
                        "I am willing to pay more to see the oncologist as I do now.",
                        "I do not mind trying shared care as it is cheaper with the consolidated primary care doctor visit.")
                    val q1points = listOf(1,0,2)
                    val (q1choice, q1setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "1. When thinking about the cost of follow-up,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q1option,
                            q1choice, q1setchoice
                        )

                    }


                    val q2option = listOf(
                        "The location of the cancer centre or polyclinics do not matter much to me.",
                        "I am willing to travel a longer distance to see the oncologist.",
                        "I do not mind trying shared care as the polyclinic is located more conveniently than the cancer centre.")
                    val q2points = listOf(1,0,2)
                    val (q2choice, q2setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n2. Thinking about the location of cancer centre and polyclinics,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q2option,
                            q2choice, q2setchoice
                        )
                    }

                    val q3option = listOf(
                        "I like the idea that the polyclinic doctor I see is trained to care for my cancer-related problems.",
                        "Whether the polyclinic doctor I see care for my cancer-related problems is not important to me.",
                        "I prefer for my cancer-related problems to be cared for by my oncologist only.")
                    val q3points = listOf(1,0,2)
                    val (q3choice, q3setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n3. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q3option,
                            q3choice, q3setchoice
                        )
                    }

                    val q4option = listOf(
                        "I am neutral towards polyclinic doctors' ability in caring for my cancer.",
                        "I have low or no confidence in polyclinic doctors in caring for my cancer, even if they are trained by oncologists for it.",
                        "I am confident that the polyclinic doctors could care for my cancer if they are trained by oncologists for it.")
                    val q4points = listOf(1,0,2)
                    val (q4choice, q4setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n4. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q4option,
                            q4choice, q4setchoice
                        )
                    }

                    val q5option = listOf(
                        "I like the idea that the polyclinic doctor I see is aware of my cancer history.",
                        "It is not necessary for the polyclinic doctor I see to be aware of my cancer history.")
                    val q5points = listOf(1,2)
                    val (q5choice, q5setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n5. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q5option,
                            q5choice, q5setchoice
                        )
                    }


                    val q6option = listOf(
                        "It does not matter much to me whether I see the same or a different polyclinic doctor each time I viist the polyclinic.",
                        "Seeing the same polyclinic doctor each time is important to me.")
                    val q6points = listOf(1,2)
                    val (q6choice, q6setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n6. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q6option,
                            q6choice, q6setchoice
                        )
                    }

                    val q7option = listOf(
                        "I prefer for my cancer-related problems to be cared for by my oncologist only.",
                        "It is useful for me to be able to contact a community pharmacist to ask questions and seek advice for my cancer.",
                        "Whether the community pharmacist I talk to care for my cancer-related problems is not important to me.")
                    val q7points = listOf(1,0,2)
                    val (q7choice, q7setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n7. When thinking about community pharmacists,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q7option,
                            q7choice, q7setchoice
                        )
                    }

                    val q8option = listOf(
                        "It is not necessary for the community pharmacist talking to me to be aware of my cancer history.",
                        "I like the idea that the community pharmacist talking to me is aware of my cancer history.")
                    val q8points = listOf(1,2)
                    val (q8choice, q8setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n8. When thinking about community pharmacists,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q8option,
                            q8choice, q8setchoice
                        )
                    }

                    val q9option = listOf(
                        "Whether the oncologist, polyclinic doctor, and community pharmacists caring for me are contactable by one another is not important to me.",
                        "I like the idea that the oncologist, polyclinic doctor, and community pharmacists caring for me are contactable by one another.")
                    val q9points = listOf(1,2)
                    val (q9choice, q9setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "\n\n9. When thinking about cancer follow-up care in general,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        KindRadioGroup(
                            mItems = q9option,
                            q9choice, q9setchoice
                        )
                    }

                    val errormsg = remember { mutableStateOf("") }


                    Button(
                        onClick = {
                            totalPoints.value = 0
                            if (q1choice == "" || q2choice == "" || q3choice == "" || q4choice == "" ||
                                q5choice == "" || q6choice == "" || q7choice == "" || q8choice == "" || q9choice == "")
                                errormsg.value = "Error : Complete all questions"
                            else
                            {errormsg.value = ""
                                totalPoints.value =

                                    (totalPoints.value.plus(q1points[q1option.indexOf(q1choice)]
                                    .plus(q2points[q2option.indexOf(q2choice)])
                                    .plus(q3points[q3option.indexOf(q3choice)])
                                    .plus(q4points[q4option.indexOf(q4choice)])
                                    .plus(q5points[q5option.indexOf(q5choice)])
                                    .plus(q6points[q6option.indexOf(q6choice)])
                                    .plus(q7points[q7option.indexOf(q7choice)])
                                    .plus(q8points[q8option.indexOf(q8choice)])
                                    .plus(q9points[q9option.indexOf(q9choice)]))*100/18
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9DC3E6)),
                        modifier = Modifier.padding(vertical =16.dp).fillMaxWidth().align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "SUBMIT")
                    }

                    Text(
                        text = "${totalPoints.value}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = errormsg.value,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                }
            }
        }
    }

    @Composable
    fun KindRadioGroup(
        mItems: List<String>,
        selected: String,
        setSelected: (selected: String) -> Unit,
    ) {
        CompositionLocalProvider() {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                mItems.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {



                        RadioButton(
                            selected = selected == item,
                            onClick = {
                                setSelected(item)
                            },
                            enabled = true,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Magenta
                            )
                        )

                        Text(text = item, modifier = Modifier.padding(start = 8.dp))

                    }
                }
            }
        }
    }
}