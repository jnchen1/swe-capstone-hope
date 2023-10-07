import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
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
import ui.theme.AppTypography

data class HomeScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "Home"

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
                            fontSize = AppTypography.h1.fontSize,
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
                            onClick = {
                                navigator.push(WhoIsThisForScreen())
                            }, modifier = Modifier.padding(4.dp),
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
                                fontSize = AppTypography.button.fontSize,
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
                            onClick = {}, modifier = Modifier.padding(4.dp).alpha(0f),
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
                                fontSize = AppTypography.button.fontSize,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }
        ) {

            Column(modifier = Modifier.padding(8.dp)){

                    Image(
                        painter = painterResource("home_person_image.png"),
                        contentDescription = "Image of Who This is for",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth(0.6f).align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "What to do?",
                        fontSize = AppTypography.h2.fontSize,
                        textAlign = TextAlign.Center,
                        color = Color(93, 83, 94)
                    )
                    Text(
                        fontSize = AppTypography.body1.fontSize,
                        color = Color(93, 83, 94),
                        modifier = Modifier.padding( bottom = 4.dp ),
                        text = buildAnnotatedString {
                            append("If this is your ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("first time")
                            }
                            append(" using the decision aid, please read in the order listed.")
                        }
                    )

                    ImageAndText( modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) },painterResource("red_home_button.png"),
                        "Red button for cancer survivorship",
                        "1. What is cancer survivorship?")
                    Spacer(modifier = Modifier.height(5.dp))
                    ImageAndText( modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) }, painterResource("orange_home_button.png"),
                        "Orange button for physical effects",
                        "2. Physical effects")
                    Spacer(modifier = Modifier.height(5.dp))
                    ImageAndText( modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) }, painterResource("yellow_home_button.png"),
                        "Yellow button for emotional effects",
                        "3. Emotional effects")
                    Spacer(modifier = Modifier.height(5.dp))
                    ImageAndText(  modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) }, painterResource("green_home_button.png"),
                        "Green button for Follow up options",
                        "4. Follow-up care options")
                    Spacer(modifier = Modifier.height(5.dp))
                    ImageAndText( modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) }, painterResource("light_blue_home_button.png"),
                        "Light blue button for comparing options",
                        "5. Comparing options")
                    Spacer(modifier = Modifier.height(5.dp))
                    ImageAndText( modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) }, painterResource("blue_home_button.png"),
                        "Blue button for find out what matters",
                        "6. Finding out what matters to you")
                    Spacer(modifier = Modifier.height(5.dp))
                    ImageAndText( modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) }, painterResource("violet_home_button.png"),
                        "Violet button for Conclusion",
                        "7. Conclusion")
                    Spacer(modifier = Modifier.height(5.dp))
                    ImageAndText( modifier = Modifier.clickable { navigator.push(WhoIsThisForScreen()) }, painterResource("purple_home_button.png"),
                        "Purple button for other online resources",
                        "Other online resources")

            }

        }
    }
}

@Composable
fun ImageAndText(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    buttonText: String
){
    val height = 40.dp
    Box(
        modifier = modifier.fillMaxWidth().height(height).background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = buttonText,
            color = Color.Black
        )
    }
}
