package ui.physical_effect

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import model.TherapyMedicineEffect
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

data class PhysicalEffectMedicineScreen(
    val treatmentMedicine: TherapyMedicineEffect,
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.PHYSICAL_EFFECT
    private val screenTitle = option.title
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
                ThemeTopAppBar(
                    screenTitle, option.color,
                    navigationIcon = {
                        Image(
                            painterResource(treatmentMedicine.icon),
                            null,
                            Modifier.size(56.dp).padding(4.dp, 8.dp),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                )
            },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = navigator::pop,
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } }
                )
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
                                style = MaterialTheme.typography.h3,
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