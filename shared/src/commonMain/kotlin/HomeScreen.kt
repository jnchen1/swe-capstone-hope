import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

data class HomeScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "HOME"

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
                BottomAppBar {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
                    ) {
                        Button(
                            onClick = { navigator.pop() },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "previous page arrow"
                            )

                            Text(text = "Back", modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource("home_person_image.png"),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(240.dp)
                        .padding(top = 8.dp).align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "What to do?",
                    style = MaterialTheme.typography.h3
                )

                Text(
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = buildAnnotatedString {
                        append("If this is your ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("first time")
                        }
                        append(" using the decision aid, please read in the order listed.")
                    }
                )

                homeOptions.forEach { (text, color) ->
                    Card(
                        backgroundColor = color,
                        shape = RoundedCornerShape(12.dp),
                        elevation = 4.dp,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).clickable {
                            /* TODO: Navigate to other screen */
                        }
                    ) {
                        Text(text, color = Color.Black, modifier = Modifier.padding(12.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

private val homeOptions = listOf(
    Pair("1. What is cancer survivorship?", Color(0xFFFF7E79)),
    Pair("2. Physical effects", Color(0xFFF4B183)),
    Pair("3. Emotional effects", Color(0xFFFFE699)),
    Pair("4. Follow-up care options", Color(0xFFC6E0B5)),
    Pair("5. Comparing options", Color(0xFFA0E6E3)),
    Pair("6. Finding out what matters to you", Color(0xFF9DC3E6)),
    Pair("7. Conclusion", Color(0xFFBCBBE7)),
    Pair("Other online resources", Color(0xFFEBACEF))
)
