package physical_effect

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import model.TherapyMedicineEffect
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class PhysicalEffectMedicineScreen(
    val treatmentMedicine: TherapyMedicineEffect,
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "PHYSICAL EFFECT"
    private val buttonColors = listOf(Color(0xFF426E86), Color(0xFF5B7065), Color(0xFF755248))

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
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
                            color = Color(0xFFF4B183),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        Image(
                            painterResource(treatmentMedicine.icon),
                            null,
                            Modifier.padding(4.dp, 8.dp)
                        )
                    },
                    actions = {
                        Spacer(Modifier.width(56.dp))
                    }
                )
            },
            bottomBar = {
                BottomNavigation {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .padding(bottom = 4.dp)
                    ) {
                        Button(
                            onClick = { navigator.pop() },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Back"
                            )
                            Text(text = "Back", modifier = Modifier.padding(start = 4.dp))
                        }

                        BottomNavigationItem(
                            selected = false,
                            onClick = { navigator.popUntil { it == HomeScreen() } },
                            icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                            label = { Text(text = "Home", color = Color.White) },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = 4.dp,
                    color = Color.White
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            color = Color(0xFF5D535E),
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Text(
                                treatmentMedicine.therapyName,
                                color = Color.White,
                                style = MaterialTheme.typography.h3.copy(fontSize = 18.sp),
                                modifier = Modifier.padding(8.dp, 4.dp).align(Alignment.Start)
                            )
                        }

                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Click")
                                }
                                append(" into the medicine that you have taken.")
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                                .padding(top = 16.dp, bottom = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        treatmentMedicine.medicine.forEachIndexed { index, (name, info) ->
                            Card(
                                onClick = { navigator.push(PhysicalEffectInfoScreen(info)) },
                                modifier = Modifier.padding(horizontal = 16.dp)
                                    .align(Alignment.CenterHorizontally).then(
                                        if (index != treatmentMedicine.medicine.lastIndex)
                                            Modifier.padding(top = 8.dp)
                                        else
                                            Modifier.padding(top = 8.dp, bottom = 20.dp)
                                    ),
                                shape = RoundedCornerShape(4.dp),
                                backgroundColor = buttonColors[index]
                            ) {
                                Text(
                                    name,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(16.dp, 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}