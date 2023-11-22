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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.finding_whatmatters.FindingWhatMattersScreen
import ui.followup_care.FollowupCareCommunicationScreen
import ui.followup_care.FollowupCareScheduleScreen

//private var citizen_button_enabled = true
//private var permanent_resident_button_enabled = true
//private var non_resident_button_enabled = true
//
//private var usual_care_oncologist = buildAnnotatedString { append("") }
//private var usual_care_familyphysician = buildAnnotatedString { append("") }
//private var usual_care_general = buildAnnotatedString { append("") }
//
//private var shared_care_save = buildAnnotatedString { append("") }
//private var shared_care_costmore = buildAnnotatedString { append("") }

data class ComparingOptionsCareCostScreen(
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

        var citizen_button_enabled by remember { mutableStateOf(true) }
        var permanent_resident_button_enabled by remember { mutableStateOf(true) }
        var non_resident_button_enabled by remember { mutableStateOf(true) }

        var usual_care_oncologist by remember { mutableStateOf(buildAnnotatedString { append("") }) }
        var usual_care_familyphysician by remember { mutableStateOf(buildAnnotatedString { append("") }) }
        var usual_care_general by remember { mutableStateOf(buildAnnotatedString { append("") }) }

        var shared_care_save by remember { mutableStateOf(buildAnnotatedString { append("") }) }
        var shared_care_costmore by remember { mutableStateOf(buildAnnotatedString { append("") }) }




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
                            .verticalScroll(rememberScrollState()).fillMaxWidth(),
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
                                        append("In a year, ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("two")
                                        }
                                        append(" oncologist visits cost:")
                                    }
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_2_oncologists.png"),
                                        contentDescription = "Two Oncologist visits",
                                        modifier = Modifier.fillMaxWidth(0.5f).padding(top = 8.dp),
                                        contentScale = ContentScale.FillWidth
                                    ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                    Box(
                                        modifier = Modifier.fillMaxSize().background(Color(0xFF336B87)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            color = Color.White,
                                            modifier = Modifier.padding(2.dp),
                                            textAlign = TextAlign.Center,
                                            text = usual_care_oncologist
                                        )
                                    }

                                }
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(top = 8.dp)
                                        .align(Alignment.CenterHorizontally),
                                    text = "Each visit to the polyclinic will cost:"

                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                                ) {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_doctor.png"),
                                        contentDescription = "Family Physician",
                                        modifier = Modifier.fillMaxWidth(0.25f).padding(8.dp),
                                        contentScale = ContentScale.FillWidth
                                    ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                    Box(
                                        modifier = Modifier.fillMaxSize().background(Color(0xFFFF5B61)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            color = Color.White,
                                            modifier = Modifier.padding(2.dp),
                                            textAlign = TextAlign.Center,
                                            text = usual_care_familyphysician
                                        )

                                    }
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_general_clinic.png"),
                                        contentDescription = "General Clinic Doctor",
                                        modifier = Modifier.fillMaxWidth(0.25f).padding(end = 8.dp),
                                        contentScale = ContentScale.FillWidth
                                    ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                    Box(
                                        modifier = Modifier.fillMaxSize().background(Color(0xFFC61C71)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            color = Color.White,
                                            modifier = Modifier.padding(2.dp),
                                            textAlign = TextAlign.Center,
                                            text = usual_care_general
                                        )

                                    }
                                }

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
                            enabled = true,
                            onClick = {
                                if (navigator.items.contains(ComparingOptionsCareScheduleScreen())) {
                                    navigator.pop()
                                } else {
                                    navigator.replace(ComparingOptionsCareScheduleScreen())
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                        ) {
                            Text("What is your follow-up schedule?")
                        }
                        Button(
                            enabled = false,
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                        ) {
                            Text("Cost?")
                        }

                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally),
                            text = "Click on the option that applies to you below:"
                        )
                        Button(
                            enabled = citizen_button_enabled,
                            onClick = {
                                println("Citzen button is clicked $citizen_button_enabled")

                                citizen_button_enabled = false
                                permanent_resident_button_enabled = true
                                non_resident_button_enabled = true
                                usual_care_oncologist = buildAnnotatedString {

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$76")
                                    }
                                    append(" (after subsidy) to ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$310.30")
                                    }
                                    append(" (private)")
                                }
                                usual_care_familyphysician = buildAnnotatedString {
                                    append("Family physician clinic: ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$25.40")
                                    }
                                }
                                usual_care_general = buildAnnotatedString {
                                    append("General clinic: ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$14")
                                    }
                                }

                                shared_care_save =
                                    buildAnnotatedString {  //text = "$76 (after subsidy) to $310.30 (private)",

                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("\$12.60")
                                        }
                                        append(" (after subsidy) to ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("\$129.75")
                                        }
                                        append(" (private)")
                                    }


                                shared_care_costmore = buildAnnotatedString {

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$11.40")
                                    }
                                    append(" more per visit")
                                }
                                println("Should be false $citizen_button_enabled")
                                println("Test $shared_care_costmore")

                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFFC1E1DC)),
                        ) {
                            Text("Singapore citizen")
                        }
                        Button(
                            enabled = permanent_resident_button_enabled,
                            onClick = {
                                citizen_button_enabled = true
                                permanent_resident_button_enabled = false
                                non_resident_button_enabled = true

                                usual_care_oncologist = buildAnnotatedString {

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$114")
                                    }
                                    append(" (after subsidy) to ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$310.30")
                                    }
                                    append(" (private)")
                                }
                                usual_care_familyphysician = buildAnnotatedString {
                                    append("Family physician clinic: ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$40")
                                    }
                                }
                                usual_care_general = buildAnnotatedString {
                                    append("General clinic: ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$27.60")
                                    }
                                }

                                shared_care_save =
                                    buildAnnotatedString {  //text = "$76 (after subsidy) to $310.30 (private)",

                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("\$17")
                                        }
                                        append(" (after subsidy) to ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("\$115.15")
                                        }
                                        append(" (private)")
                                    }


                                shared_care_costmore = buildAnnotatedString {

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$12.40")
                                    }
                                    append(" more per visit")
                                }

                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFFC1E1DC)),
                        ) {
                            Text("Permanent resident")
                        }
                        Button(
                            enabled = non_resident_button_enabled,
                            onClick = {
                                citizen_button_enabled = true
                                permanent_resident_button_enabled = true
                                non_resident_button_enabled = false

                                usual_care_oncologist = buildAnnotatedString {

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$261.08")
                                    }
                                    append(" (after subsidy) to ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$342.40")
                                    }
                                    append(" (private)")
                                }
                                usual_care_familyphysician = buildAnnotatedString {
                                    append("Family physician clinic: ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$73.90")
                                    }
                                }
                                usual_care_general = buildAnnotatedString {
                                    append("General clinic: ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$53.50")
                                    }
                                }

                                shared_care_save =
                                    buildAnnotatedString {  //text = "$76 (after subsidy) to $310.30 (private)",

                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("\$56.64")
                                        }
                                        append(" (after subsidy) to ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("\$97.30")
                                        }
                                        append(" (private)")
                                    }


                                shared_care_costmore = buildAnnotatedString {

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\$20.40")
                                    }
                                    append(" more per visit")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFFC1E1DC)),
                        ) {
                            Text("Non-resident")
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
                                        append("By replacing one oncologist visit with a polyclinic family physician visit, you will ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("save")
                                        }
                                        append(":")
                                    },
                                    textAlign = TextAlign.Center
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource("comparing_options/comparing_oncologist_family.png"),
                                        contentDescription = "One Visit with Oncologist and One with the Family Physician",
                                        modifier = Modifier.fillMaxWidth(0.5f).padding(top = 8.dp, end = 8.dp),
                                        contentScale = ContentScale.FillWidth
                                    ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                    Box(
                                        modifier = Modifier.fillMaxSize().background(Color(0xFF336B87)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            color = Color.White,
                                            modifier = Modifier.padding(2.dp),
                                            textAlign = TextAlign.Center,
                                            text = shared_care_save
                                        )
                                    }

                                }
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(top = 8.dp)
                                        .align(Alignment.CenterHorizontally),
                                    text = "Seeing a family physician instead of a general doctor in the polyclinic will cost you:",
                                    textAlign = TextAlign.Center

                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize().background(Color(0xFFE99787)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            color = Color.White,
                                            modifier = Modifier.padding(2.dp),
                                            textAlign = TextAlign.Center,
                                            text = shared_care_costmore
                                        )

                                    }
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
                                            .background(Color(0xFFA49592)),
                                        contentAlignment = Alignment.Center
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
                                            append("In a year, ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("two")
                                            }
                                            append(" oncologist visits cost:")
                                        }
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        Image(
                                            painter = painterResource("comparing_options/comparing_2_oncologists.png"),
                                            contentDescription = "Two Oncologist visits",
                                            modifier = Modifier.fillMaxWidth(0.5f).padding(top = 8.dp),
                                            contentScale = ContentScale.FillWidth
                                        ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                        Box(
                                            modifier = Modifier.fillMaxSize().background(Color(0xFF336B87)),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                color = Color.White,
                                                modifier = Modifier.padding(2.dp),
                                                textAlign = TextAlign.Center,
                                                text = usual_care_oncologist
                                            )
                                        }

                                    }
                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(top = 8.dp)
                                            .align(Alignment.CenterHorizontally),
                                        text = "Each visit to the polyclinic will cost:"

                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                                    ) {
                                        Image(
                                            painter = painterResource("comparing_options/comparing_doctor.png"),
                                            contentDescription = "Family Physician",
                                            modifier = Modifier.fillMaxWidth(0.33f).padding(8.dp),
                                            contentScale = ContentScale.FillWidth
                                        ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                        Box(
                                            modifier = Modifier.fillMaxSize().background(Color(0xFFFF5B61)),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                color = Color.White,
                                                modifier = Modifier.padding(2.dp),
                                                textAlign = TextAlign.Center,
                                                text = usual_care_familyphysician
                                            )

                                        }
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp)
                                    ) {
                                        Image(
                                            painter = painterResource("comparing_options/comparing_general_clinic.png"),
                                            contentDescription = "General Clinic Doctor",
                                            modifier = Modifier.fillMaxWidth(0.33f).padding(end = 8.dp),
                                            contentScale = ContentScale.FillWidth
                                        ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                        Box(
                                            modifier = Modifier.fillMaxSize().background(Color(0xFFC61C71)),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                color = Color.White,
                                                modifier = Modifier.padding(2.dp),
                                                textAlign = TextAlign.Center,
                                                text = usual_care_general
                                            )

                                        }
                                    }

                                }
                            }
                            Column(modifier = Modifier.padding(5.dp).weight(0.33f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly){
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
                                    enabled = false,
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text("Cost?")
                                }

                                Text(
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = "Click on the option that applies to you below:"
                                )
                                Button(
                                    enabled = citizen_button_enabled,
                                    onClick = {
                                        println("Citzen button is clicked $citizen_button_enabled")

                                        citizen_button_enabled = false
                                        permanent_resident_button_enabled = true
                                        non_resident_button_enabled = true
                                        usual_care_oncologist = buildAnnotatedString {

                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$76")
                                            }
                                            append(" (after subsidy) to ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$310.30")
                                            }
                                            append(" (private)")
                                        }
                                        usual_care_familyphysician = buildAnnotatedString {
                                            append("Family physician clinic: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$25.40")
                                            }
                                        }
                                        usual_care_general = buildAnnotatedString {
                                            append("General clinic: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$14")
                                            }
                                        }

                                        shared_care_save =
                                            buildAnnotatedString {  //text = "$76 (after subsidy) to $310.30 (private)",

                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                    append("\$12.60")
                                                }
                                                append(" (after subsidy) to ")
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                    append("\$129.75")
                                                }
                                                append(" (private)")
                                            }


                                        shared_care_costmore = buildAnnotatedString {

                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$11.40")
                                            }
                                            append(" more per visit")
                                        }
                                        println("Should be false $citizen_button_enabled")
                                        println("Test $shared_care_costmore")

                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFC1E1DC)),
                                ) {
                                    Text("Singapore citizen")
                                }

                                Button(
                                    enabled = permanent_resident_button_enabled,
                                    onClick = {
                                        citizen_button_enabled = true
                                        permanent_resident_button_enabled = false
                                        non_resident_button_enabled = true

                                        usual_care_oncologist = buildAnnotatedString {

                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$114")
                                            }
                                            append(" (after subsidy) to ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$310.30")
                                            }
                                            append(" (private)")
                                        }
                                        usual_care_familyphysician = buildAnnotatedString {
                                            append("Family physician clinic: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$40")
                                            }
                                        }
                                        usual_care_general = buildAnnotatedString {
                                            append("General clinic: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$27.60")
                                            }
                                        }

                                        shared_care_save =
                                            buildAnnotatedString {  //text = "$76 (after subsidy) to $310.30 (private)",

                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                    append("\$17")
                                                }
                                                append(" (after subsidy) to ")
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                    append("\$115.15")
                                                }
                                                append(" (private)")
                                            }


                                        shared_care_costmore = buildAnnotatedString {

                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$12.40")
                                            }
                                            append(" more per visit")
                                        }

                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFC1E1DC)),
                                ) {
                                    Text("Permanent resident")
                                }


                                Button(
                                    enabled = non_resident_button_enabled,
                                    onClick = {
                                        citizen_button_enabled = true
                                        permanent_resident_button_enabled = true
                                        non_resident_button_enabled = false

                                        usual_care_oncologist = buildAnnotatedString {

                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$261.08")
                                            }
                                            append(" (after subsidy) to ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$342.40")
                                            }
                                            append(" (private)")
                                        }
                                        usual_care_familyphysician = buildAnnotatedString {
                                            append("Family physician clinic: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$73.90")
                                            }
                                        }
                                        usual_care_general = buildAnnotatedString {
                                            append("General clinic: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$53.50")
                                            }
                                        }

                                        shared_care_save =
                                            buildAnnotatedString {  //text = "$76 (after subsidy) to $310.30 (private)",

                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                    append("\$56.64")
                                                }
                                                append(" (after subsidy) to ")
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                    append("\$97.30")
                                                }
                                                append(" (private)")
                                            }


                                        shared_care_costmore = buildAnnotatedString {

                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("\$20.40")
                                            }
                                            append(" more per visit")
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFC1E1DC)),
                                ) {
                                    Text("Non-resident")
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
                                            .background(Color(0xFFE99787)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Shared Care",
                                            style = MaterialTheme.typography.h2,
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    }


                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                                        text = buildAnnotatedString {
                                            append("By replacing one oncologist visit with a polyclinic family physician visit, you will ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("save")
                                            }
                                            append(":")
                                        },
                                        textAlign = TextAlign.Center
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        Image(
                                            painter = painterResource("comparing_options/comparing_oncologist_family.png"),
                                            contentDescription = "One Visit with Oncologist and One with the Family Physician",
                                            modifier = Modifier.fillMaxWidth(0.5f).padding(top = 8.dp, end = 8.dp),
                                            contentScale = ContentScale.FillWidth
                                        ) //modifier = Modifier.fillMaxHeight().padding(top = 8.dp),
                                        Box(
                                            modifier = Modifier.fillMaxSize().background(Color(0xFF336B87)),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                color = Color.White,
                                                modifier = Modifier.padding(2.dp),
                                                textAlign = TextAlign.Center,
                                                text = shared_care_save
                                            )
                                        }

                                    }
                                    Text(
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(top = 8.dp)
                                            .align(Alignment.CenterHorizontally),
                                        text = "Seeing a family physician instead of a general doctor in the polyclinic will cost you:",
                                        textAlign = TextAlign.Center

                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(
                                            start = 24.dp,
                                            end = 24.dp,
                                            top = 8.dp,
                                            bottom = 8.dp
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize().background(Color(0xFFE99787)),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                color = Color.White,
                                                modifier = Modifier.padding(2.dp),
                                                textAlign = TextAlign.Center,
                                                text = shared_care_costmore
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
    }
