package ui.comparing_options

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.followup_care.FollowupCareOptionScreen
import ui.physical_effect.PhysicalEffectIntroScreen

data class ComparingOptionsIntroScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "COMPARING THE OPTIONS"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = screenTitle,
                            style = MaterialTheme.typography.h1,
                            color = Color(0xFFC1E1DC),
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
                            onClick = {
//                                if (navigator.items.contains(ComparingOptionsIntroScreen())) {
//                                    navigator.pop()
//                                } else {
//                                    navigator.replace(ComparingOptionsIntroScreen())
//                                }
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
                                    modifier = Modifier.padding(start = 8.dp)
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

                        Button(
                            onClick = {
                                //navigator.push(PhysicalEffectIntroScreen())
                                },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            BoxWithConstraints {
                                val boxScope = this

                                val textSize = MaterialTheme.typography.button.fontSize
                                val textLayout = rememberTextMeasurer().measure(
                                    text = "Next section",
                                    style = MaterialTheme.typography.button,
                                    overflow = TextOverflow.Clip
                                )
                                val sizeInDp = with(LocalDensity.current) {
                                    textLayout.size.width.toDp()
                                }
                                val textAndSize =
                                    if (boxScope.maxWidth < sizeInDp * 1.3f) Pair(
                                        "Next\nsection", textSize * .8
                                    )
                                    else Pair("Next section", textSize)

                                Text(
                                    text = textAndSize.first,
                                    fontSize = textAndSize.second,
                                    textAlign = TextAlign.End,
                                    overflow = TextOverflow.Clip,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                            }
                            Icon(
                                Icons.Rounded.LastPage,
                                contentDescription = "Next section"
                            )
                        }
                    }
                }
            }
        ) {
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
                ){
                    Column(modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Box(
                            modifier = Modifier.fillMaxWidth(0.5f).clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFA49592))
                        ){
                            Text(text = "Usual Care",
                                style = MaterialTheme.typography.h2,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Image(
                            painter = painterResource("comparing_options/comparing_care_question_marks.png"),
                            contentDescription = "Health Care Professionals not knowing each other",
                            modifier = Modifier.width(400.dp).height(120.dp).padding(top = 8.dp),
                            contentScale = ContentScale.FillHeight
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
                        Row{
                            Image(
                                painter = painterResource("comparing_options/comparing_gp_doctor.png"),
                                contentDescription = "Doctor and GP",
                                modifier = Modifier.size(120.dp).padding(10.dp)
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
                        Row{
                            //TODO: Move the image ot the right of the text. At the moment it disappears when that happens
                            Image(
                                painter = painterResource("comparing_options/comparing_pharmacist.png"),
                                contentDescription = "Pharmacist",
                                modifier = Modifier.size(110.dp).padding(10.dp).align(Alignment.CenterVertically)
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


                    }
                }

                Button(
                    enabled = false,
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ){
                    Text("Who is caring for you?")
                }
                Button(
                    enabled = true,
                    onClick = {navigator.push(ComparingOptionsInformationSharedScreen())},
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ){
                    Text("How is information about you shared?")
                }
                Button(
                    enabled = true,
                    onClick = {navigator.push(ComparingOptionsCareScheduleScreen())},
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ){
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
                ){
                    Text("Cost?")
                }

                Card(
                    elevation = 2.dp,
                    modifier = Modifier.padding(10.dp), //.width(350.dp).height(475.dp)
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color.White
                ){
                    Column(modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Box(
                            modifier = Modifier.fillMaxWidth(0.5f).clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFE99787))
                        ){
                            Text(text = "Shared Care",
                                style = MaterialTheme.typography.h2,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Image(
                            painter = painterResource("comparing_options/comparing_care_team.png"),
                            contentDescription = "Health Care Professionals being a team",
                            modifier = Modifier.width(400.dp).height(150.dp).padding(top = 8.dp),
                            contentScale = ContentScale.FillHeight
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
                        Row{
                            Image(
                                painter = painterResource("comparing_options/comparing_doctor.png"),
                                contentDescription = "Doctor and GP",
                                modifier = Modifier.size(110.dp).padding(10.dp)
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
                        Row{
                            //TODO: Move the image ot the right of the text. At the moment it disappears when that happens
                            Image(
                                painter = painterResource("comparing_options/comparing_pharmacist.png"),
                                contentDescription = "Pharmacist",
                                modifier = Modifier.size(110.dp).padding(10.dp).align(Alignment.CenterVertically)
                            )
                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
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
                        }


                    }
                }


//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//
//                }
//                Text(
//                    style = MaterialTheme.typography.h1,
//                    modifier = Modifier.padding(8.dp),
//                    text = "Why is survivorship care important?"
//                )
//                Text(
//                    style = MaterialTheme.typography.body1,
//                    modifier = Modifier.padding(8.dp),
//                    text = buildAnnotatedString {
//                        append("Survivorship care includes ")
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("regular follow-up sessions")
//                        }
//                        append(" to ")
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("prevent and monitor")
//                        }
//                        append(" for signs of returning cancers. This is because there is a ")
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("possibility")
//                        }
//                        append(" of the cancer ")
//
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("returning")
//                        }
//                        append(" in the breast or other parts of the body.")
//                    }
//                )

            }
        }
    }
}