import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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

data class DecisionAidScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "DECISION AID"

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
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            },
            bottomBar = {
                BottomNavigation {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { navigator.push(WhoIsThisForScreen()) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Next")
                            Icon(
                                imageVector = Icons.Rounded.ArrowForward,
                                contentDescription = "next page arrow",
                                modifier = Modifier.padding(start = 4.dp)
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
                Text(
                    text = "What is this?",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    style = MaterialTheme.typography.body1,
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
                    modifier = Modifier.width(300.dp).align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "How to Navigate?",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    text = "Clickable parts indicated by",
                    modifier = Modifier.padding(horizontal = 8.dp).padding(vertical = 4.dp)
                )

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colors.secondary,
                        disabledBackgroundColor = MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    enabled = false
                ) {
                    Text("Click here for navigation guide")
                }
            }
        }
    }
}