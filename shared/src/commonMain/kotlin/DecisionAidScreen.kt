import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class DecisionAidScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "Decision Aid"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        val selectedIndex = remember { mutableStateOf(0) }
        Scaffold(
//        modifier = Modifier.nestedScroll(scrollBeh)
            topBar = {

                TopAppBar(
                    title = {
                        Text(
                            text = screenTitle,
                            fontSize = 30.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                    }
                )

            },
            bottomBar = {
                BottomAppBar(
                    backgroundColor = Color(48, 48, 47),
                    contentColor = Color.White
                ) {

                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(
                            onClick = {}, modifier = Modifier.padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    208,
                                    150,
                                    131
                                )
                            )
                        ) {

                            Icon(
                                imageVector = Icons.Default.ArrowBack,

                                contentDescription = "previous page arrow icon "
                            )
                            Text(
                                text = "Back",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        BottomNavigationItem(onClick = {
                            selectedIndex.value = 0
                        }, icon = { Icon(imageVector = Icons.Default.Home, "home button") },
                            selected = (selectedIndex.value == 0),
                            label = { Text(text = "Home") }
                        )

                        Button(
                            onClick = {}, modifier = Modifier.padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    208,
                                    150,
                                    131
                                )
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "next page arrow icon "
                            )
                            Text(
                                text = "Next",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }
        ) {

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "What is this?",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color(93, 83, 94)
                )
                Text(
                    fontSize = 15.sp,
                    color = Color(93, 83, 94),
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("A tool to help readers learn more about the importance of ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("cancer follow-up care")
                        }
                        append(" and to understand the currently ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("available options")
                        }
                        append(" in Singapore.")
                    }
                )
                Image(
                    painter = painterResource("thinking_decisionaid.png"),
                    contentDescription = "Image of thinking person",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "How to Navigate?",
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    color = Color(93, 83, 94)
                )
                Text(
                    fontSize = 15.sp,
                    text = "Clickable parts indicated by a '' symbol.",
                    color = Color(93, 83, 94)
                )

                Button(
                    onClick = {
                    },
                    shape = CutCornerShape(5),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(208, 150, 131))
                ) {
                    Text("Click here for navigation guide", color = Color.White)
                }

            }

        }
    }
}