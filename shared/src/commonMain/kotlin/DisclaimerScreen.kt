import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            }
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(it).padding(8.dp)) {
                Column(modifier = Modifier.weight(1f, true)) {
                    Image(
                        painter = painterResource("app_logo.png"), contentDescription = "app logo",
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(.5f),
                        contentScale = ContentScale.FillWidth
                    )

                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp).padding(top = 8.dp),
                        text = buildAnnotatedString {
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
                            append(": This decision aid does not replace the medical advice, diagnosis or treatment provided by your doctors. If you have any questions, you may contact the Principal Investigator, Dr Fok Wai Yee Rose, at 64368000.")
                        }
                    )
                }

                Button(
                    onClick = { navigator.replace(DecisionAidScreen()) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text("Click to acknowledge that I have read & understood the above")
                }
            }
        }
    }
}