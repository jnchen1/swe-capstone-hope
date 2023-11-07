package ui.emotional_effect

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.followup_care.FollowupCareIntroScreen

data class EmotionalEffectSecondScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.EMOTIONAL_EFFECT
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
                        if (navigator.items.contains(EmotionalEffectFirstScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(EmotionalEffectFirstScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextSection = true,
                    nextAction = { navigator.push(FollowupCareIntroScreen()) }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource("emotional_effect/emotional_effects_4.png"),
                    contentDescription = "Emotional effects",
                    modifier = Modifier.size(200.dp).padding(8.dp).align(Alignment.CenterHorizontally)
                )

                Text(
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(8.dp, 4.dp),
                    text = "How to seek help?"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = buildAnnotatedString {
                        append("Consider speaking to a ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("counsellor")
                        }
                        append(" or seek medical attention when you feel like you need help ")
                    }
                )

                Text(
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(horizontal = 8.dp).padding(top = 12.dp),
                    text = "What can you do?"
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = buildAnnotatedString {
                        append("You may contact the  ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(" National Cancer Centre Singapore") }
                        append(" for their services through the following: ")
                    }
                )

                PhoneLinkText(
                    Modifier.padding(horizontal = 8.dp).padding(top = 8.dp).fillMaxWidth()
                )

                EmailLinkText(
                    Modifier.padding(8.dp).fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PhoneLinkText(modifier: Modifier = Modifier) {
    val phoneNumber = "+65 6558 0384"

    val uriHandler = LocalUriHandler.current

    Row(verticalAlignment = Alignment.Top, modifier = modifier) {
        // Add the image here
        Image(
            painter = painterResource("emotional_effect/emotional_effects_5.png"), // Replace 'your_image' with the actual image resource
            contentDescription = null, // Provide a content description as needed
            modifier = Modifier.padding(end = 4.dp).size(24.dp)
                .fillMaxWidth(.1f) // Adjust the size as needed
        )

        // Add the text
        Column {
            val serviceText = buildAnnotatedString {
                append("Psychological Services, Medical Social Services, Counselling and Financial Assistance: ")
                withStyle(SpanStyle(Color.Blue, fontWeight = FontWeight.Bold)) {
                    pushStringAnnotation(tag = phoneNumber, annotation = phoneNumber)
                    append(phoneNumber)
                }
            }
            ClickableText(text = serviceText, onClick = { offset ->
                serviceText.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { uriHandler.openUri("tel:$phoneNumber") }
            })

            val programText = buildAnnotatedString {
                append("Patient Support Programmes: ")
                withStyle(SpanStyle(Color.Blue, fontWeight = FontWeight.Bold)) {
                    pushStringAnnotation(tag = phoneNumber, annotation = phoneNumber)
                    append(phoneNumber)
                }
            }
            ClickableText(text = programText, Modifier.padding(top = 4.dp), onClick = { offset ->
                programText.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { uriHandler.openUri("tel:$phoneNumber") }
            })
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmailLinkText(modifier: Modifier = Modifier) {
    val emailAddress = "psychosocial@nccs.com.sg"

    val uriHandler = LocalUriHandler.current

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        // Add the image here
        Image(
            painter = painterResource("emotional_effect/emotional_effects_6.png"), // Replace 'your_image' with the actual image resource
            contentDescription = null, // Provide a content description as needed
            modifier = Modifier.padding(end = 4.dp).size(24.dp) // Adjust the size as needed
        )

        // Add the text
        val emailText = buildAnnotatedString {
            append("Email at ")
            withStyle(SpanStyle(Color.Blue, fontWeight = FontWeight.Bold)) {
                pushStringAnnotation(tag = emailAddress, annotation = emailAddress)
                append(emailAddress)
            }
            append(" for more details")
        }
        ClickableText(text = emailText, Modifier.padding(top = 4.dp), onClick = { offset ->
            emailText.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { uriHandler.openUri("mailto:$emailAddress") }
        })
    }
}







