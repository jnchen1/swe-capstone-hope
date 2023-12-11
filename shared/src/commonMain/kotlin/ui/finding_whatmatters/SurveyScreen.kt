package ui.finding_whatmatters

import HomeScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.HomeOptions
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

data class SurveyScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.WHAT_MATTERS
    private val screenTitle = option.title

    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        val totalPoints = remember { mutableStateOf(0) }


        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle, option.color) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = {
                        if (navigator.items.contains(FindingWhatMattersScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(FindingWhatMattersScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = false
                )
            }
        ) {
            BoxWithConstraints {
                val boxScope = this
                var shouldEnableSubmitButton by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "What matters to you?",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Text(
                        text = "Please select the statement that best applies to you",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 8.dp).padding(top = 4.dp)
                    )

                    val q1option = listOf(
                        "The cost of visits does not matter much to me.",
                        "I am willing to pay more to see the oncologist as I do now.",
                        "I do not mind trying shared care as it is cheaper with the consolidated primary care doctor visit."
                    )
                    val q1points = listOf(1, 0, 2)
                    val (q1choice, q1setchoice) = remember { mutableStateOf("") }

                    Column {
                        Text(
                            text = "1. When thinking about the cost of follow-up,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q1option,
                            q1choice, q1setchoice
                        )
                    }

                    val q2option = listOf(
                        "The location of the cancer centre or polyclinics do not matter much to me.",
                        "I am willing to travel a longer distance to see the oncologist.",
                        "I do not mind trying shared care as the polyclinic is located more conveniently than the cancer centre."
                    )
                    val q2points = listOf(1, 0, 2)
                    val (q2choice, q2setchoice) = remember { mutableStateOf("") }

                    Column {
                        Text(
                            text = "2. Thinking about the location of cancer centre and polyclinics,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q2option,
                            q2choice, q2setchoice
                        )
                    }

                    val q3option = listOf(
                        "I like the idea that the polyclinic doctor I see is trained to care for my cancer-related problems.",
                        "Whether the polyclinic doctor I see care for my cancer-related problems is not important to me.",
                        "I prefer for my cancer-related problems to be cared for by my oncologist only."
                    )
                    val q3points = listOf(1, 0, 2)
                    val (q3choice, q3setchoice) = remember { mutableStateOf("") }

                    Column {
                        Text(
                            text = "3. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q3option,
                            q3choice, q3setchoice
                        )
                    }

                    val q4option = listOf(
                        "I am neutral towards polyclinic doctors' ability in caring for my cancer.",
                        "I have low or no confidence in polyclinic doctors in caring for my cancer, even if they are trained by oncologists for it.",
                        "I am confident that the polyclinic doctors could care for my cancer if they are trained by oncologists for it."
                    )
                    val q4points = listOf(1, 0, 2)
                    val (q4choice, q4setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "4. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q4option,
                            q4choice, q4setchoice
                        )
                    }

                    val q5option = listOf(
                        "I like the idea that the polyclinic doctor I see is aware of my cancer history.",
                        "It is not necessary for the polyclinic doctor I see to be aware of my cancer history."
                    )
                    val q5points = listOf(1, 2)
                    val (q5choice, q5setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "5. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q5option,
                            q5choice, q5setchoice
                        )
                    }

                    val q6option = listOf(
                        "It does not matter much to me whether I see the same or a different polyclinic doctor each time I viist the polyclinic.",
                        "Seeing the same polyclinic doctor each time is important to me."
                    )
                    val q6points = listOf(1, 2)
                    val (q6choice, q6setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "6. When thinking about polyclinic doctors,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q6option,
                            q6choice, q6setchoice
                        )
                    }

                    val q7option = listOf(
                        "I prefer for my cancer-related problems to be cared for by my oncologist only.",
                        "It is useful for me to be able to contact a community pharmacist to ask questions and seek advice for my cancer.",
                        "Whether the community pharmacist I talk to care for my cancer-related problems is not important to me."
                    )
                    val q7points = listOf(1, 0, 2)
                    val (q7choice, q7setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "7. When thinking about community pharmacists,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q7option,
                            q7choice, q7setchoice
                        )
                    }

                    val q8option = listOf(
                        "It is not necessary for the community pharmacist talking to me to be aware of my cancer history.",
                        "I like the idea that the community pharmacist talking to me is aware of my cancer history."
                    )
                    val q8points = listOf(1, 2)
                    val (q8choice, q8setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "8. When thinking about community pharmacists,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q8option,
                            q8choice, q8setchoice
                        )
                    }

                    val q9option = listOf(
                        "Whether the oncologist, polyclinic doctor, and community pharmacists caring for me are contactable by one another is not important to me.",
                        "I like the idea that the oncologist, polyclinic doctor, and community pharmacists caring for me are contactable by one another."
                    )
                    val q9points = listOf(1, 2)
                    val (q9choice, q9setchoice) = remember { mutableStateOf("") }


                    Column {
                        Text(
                            text = "9. When thinking about cancer follow-up care in general,",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp).padding(top = 24.dp)
                        )

                        KindRadioGroup(
                            mItems = q9option,
                            q9choice, q9setchoice
                        )
                    }

                    shouldEnableSubmitButton = isAllInputFilled(
                        q1choice, q2choice, q3choice, q4choice, q5choice,
                        q6choice, q7choice, q8choice, q9choice
                    )

                    Button(
                        enabled = shouldEnableSubmitButton,
                        onClick = {
                            totalPoints.value = 0
                            totalPoints.value = (
                                totalPoints.value +
                                    q1points[q1option.indexOf(q1choice)] +
                                    q2points[q2option.indexOf(q2choice)] +
                                    q3points[q3option.indexOf(q3choice)] +
                                    q4points[q4option.indexOf(q4choice)] +
                                    q5points[q5option.indexOf(q5choice)] +
                                    q6points[q6option.indexOf(q6choice)] +
                                    q7points[q7option.indexOf(q7choice)] +
                                    q8points[q8option.indexOf(q8choice)] +
                                    q9points[q9option.indexOf(q9choice)]
                                ) * 100 / 18

                            navigator.push(SurveyResultScreen(totalPoints.value))
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9DC3E6)),
                        modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "SUBMIT")
                    }
                }
            }
        }
    }

    private fun isAllInputFilled(
        q1choice: String, q2choice: String, q3choice: String, q4choice: String,
        q5choice: String, q6choice: String, q7choice: String, q8choice: String,
        q9choice: String,
    ) = !(q1choice == "" || q2choice == "" || q3choice == "" || q4choice == "" ||
        q5choice == "" || q6choice == "" || q7choice == "" || q8choice == "" || q9choice == "")

    @Composable
    fun KindRadioGroup(
        mItems: List<String>,
        selected: String,
        setSelected: (selected: String) -> Unit,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            mItems.forEach { item ->
                Row(Modifier.clickable { setSelected(item) }) {
                    RadioButton(
                        selected = selected == item,
                        onClick = { setSelected(item) },
                        enabled = true,
                        modifier = Modifier.align(Alignment.Top)
                    )

                    Text(text = item, style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}