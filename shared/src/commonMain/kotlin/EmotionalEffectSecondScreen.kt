
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
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
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import ui.followup_care.FollowupCareIntroScreen


data class EmotionalEffectSecondScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "EMOTIONAL EFFECTS"

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
                            color = Color(0xFFFF7E79),
                            style = MaterialTheme.typography.h1,
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
                        Button(
                            onClick = {
                                if (navigator.items.contains(EmotionalEffectFirstScreen())) {
                                    navigator.pop()
                                } else {
                                    navigator.replace(EmotionalEffectFirstScreen())
                                }
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Icon(
                                Icons.Rounded.ArrowBack, "Back page",
                                Modifier.padding(end = 4.dp)
                            )
                            Text(text = "Back")
                        }

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = { navigator.push(FollowupCareIntroScreen()) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Text(text = "Next")

                            Icon(
                                Icons.Rounded.ArrowForward, "Next page",
                                Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource("emotional_effects_4.png"),
                        contentDescription = "Emotional effects",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp).padding(10.dp)
                    )

                }

                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "How to seek help?"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("Consider speaking to a ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("counsellor")
                        }
                        append(" or seek medical attention when you feel like you need help ")

                    }
                )

                Text(
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp),
                    text = "What can you do?"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("You may contact the  ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" National Cancer Centre Singapore")
                        }
                        append(" for their services through the following: ")
                    }
                )

                PhoneLinkText()

                EmailLinkText()

            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PhoneLinkText() {
    val phoneNumber = "+65 6558 0384"
    val phoneNumber2 = "\n+65 6558 0384"

    val uriHandler = LocalUriHandler.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        // Add the image here
        Image(
            painter = painterResource("emotional_effects_5.png"), // Replace 'your_image' with the actual image resource
            contentDescription = null, // Provide a content description as needed
            modifier = Modifier.size(24.dp) // Adjust the size as needed
        )

        // Add the text
        Text(
            style = MaterialTheme.typography.body1.copy(fontSize = 13.sp),
            text = buildAnnotatedString {
                append("Psychological Services, Medical Social Services, Counselling ")
            }
        )


    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            style = MaterialTheme.typography.body1.copy(fontSize = 13.sp),
            text = buildAnnotatedString {
                append("and Financial Assistance: ")
            }
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 13.sp)) {
                    append(phoneNumber)
                }

            },
            modifier = Modifier.clickable {
                uriHandler.openUri("tel:$phoneNumber")
            }
        )

    }



    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(0.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp)) {
                    append("\nPatient Support Programmes: ")
                }

            }
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 13.sp)) {
                    append(phoneNumber2)
                }

            },
            modifier = Modifier.clickable {
                uriHandler.openUri("tel:$phoneNumber2")
            }
        )



    }



}




@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmailLinkText() {
    val emailAddress = "psychosocial@nccs.com.sg"

    val uriHandler = LocalUriHandler.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Add the image here
        Image(
            painter = painterResource("emotional_effects_6.png"), // Replace 'your_image' with the actual image resource
            contentDescription = null, // Provide a content description as needed
            modifier = Modifier.size(24.dp) // Adjust the size as needed
        )

        // Add the text
        Text(
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(0.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp)) {
                    append("Email at ")
                }
            }
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 13.sp)) {
                    append(emailAddress)
                }

            },
            modifier = Modifier.clickable {
                uriHandler.openUri("mailto:$emailAddress")
            }
        )

        Text(
            style = MaterialTheme.typography.body1,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 13.sp)) {
                    append(" for more details")
                }
            }
        )

    }

}







