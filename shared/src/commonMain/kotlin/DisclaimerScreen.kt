import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
import ui.ClickableTextWithUri
import ui.ThemeTopAppBar

data class DisclaimerScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val screenTitle = "DISCLAIMER"
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle) }
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)) {
                Column(modifier = Modifier.weight(1f, true).verticalScroll(rememberScrollState())) {
                    val phoneNumber = "64368000"
                    val noteText = buildAnnotatedString {
                        append("This decision aid is created for research purposes by a team of health care professionals comprising oncologists and pharmacists from the National Cancer Centre Singapore. The decision aid is ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("not official yet.")
                        }
                        append("\n\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("PLEASE NOTE")
                        }
                        append(": This decision aid does not replace the medical advice, diagnosis or treatment provided by your doctors. If you have any questions, you may contact the Principal Investigator, Dr Fok Wai Yee Rose, at ")
                        withStyle(SpanStyle(Color.Blue, fontWeight = FontWeight.Bold)) {
                            pushStringAnnotation(tag = phoneNumber, annotation = phoneNumber)
                            append(phoneNumber)
                        }
                        append(".")
                    }
                    ClickableTextWithUri(
                        noteText, "tel:$phoneNumber",
                        MaterialTheme.typography.body1,
                        Modifier.padding(8.dp).padding(top = 8.dp)
                    )

                    Image(
                        painter = painterResource("app_logo.png"), contentDescription = "app logo",
                        modifier = Modifier.align(Alignment.CenterHorizontally).width(240.dp)
                            .padding(top = 16.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }

                Button(
                    onClick = { navigator.replace(DecisionAidScreen()) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Text("Click to acknowledge that I have read & understood the above")
                }
            }
        }
    }
}