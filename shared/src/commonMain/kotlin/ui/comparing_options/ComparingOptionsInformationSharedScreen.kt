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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
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
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeTopAppBar
import ui.followup_care.FollowupCareOptionScreen
import ui.physical_effect.PhysicalEffectIntroScreen

data class ComparingOptionsInformationSharedScreen(
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
                            onClick = {
//                                if (navigator.items.contains(FollowupCareScreen())) {
//                                    navigator.pop()
//                                } else {
//                                    navigator.replace(FollowupCareScreen())
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
                            painter = painterResource("comparing_options/comparing_usual_information_shared.png"),
                            contentDescription = "How usual care share information",
                            modifier = Modifier.width(323.dp).height(278.dp).padding(8.dp,top= 10.dp),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                            text = buildAnnotatedString {
                                append("Health care professionals caring for you ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("may or may")
                                }
                                append(" know about your cancer and treatment")
                            }
                        )

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
                ){
                    Text("Who is caring for you?")
                }
                Button(
                    enabled = false,
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFF727077)),
                ){
                    Text("How is information about you shared?")
                }
                Button(
                    enabled = true,
                    onClick = {
                        if (navigator.items.contains(ComparingOptionsCareScheduleScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(ComparingOptionsCareScheduleScreen())
                        }},
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
                    modifier = Modifier.padding(10.dp),
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
                            painter = painterResource("comparing_options/comparing_shared_information_shared.png"),
                            contentDescription = "How shared care share information",
                            modifier = Modifier.width(323.dp).height(278.dp).padding(8.dp,top= 10.dp),
                            contentScale = ContentScale.FillBounds

                        )
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("All")
                                }
                                append(" health care professionals caring for you have a ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("basic understanding")
                                }
                                append(" of your cancer and treatment")
                            }
                        )



                    }
                }

            }
        }
    }
}