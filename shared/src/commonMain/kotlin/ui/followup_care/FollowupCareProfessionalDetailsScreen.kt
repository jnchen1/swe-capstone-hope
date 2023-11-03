package ui.followup_care

import HomeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
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
import model.HealthcareProfessional
import model.HealthcareProfessional.*
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeTopAppBar

@OptIn(ExperimentalResourceApi::class)
data class FollowupCareProfessionalDetailsScreen(
    val professional: HealthcareProfessional,
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.FOLLOWUP_CARE
    private val screenTitle = option.title

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
                BottomNavigation {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
                    ) {
                        Button(
                            onClick = {
                                if (navigator.items.contains(FollowupCareProfessionalScreen())) {
                                    navigator.pop()
                                } else {
                                    navigator.replace(FollowupCareProfessionalScreen())
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
                            onClick = { /*TODO*/ },
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
            BoxWithConstraints {
                val boxScope = this
                val isPortrait = boxScope.maxHeight > boxScope.maxWidth

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painterResource(professional.image),
                        contentDescription = professional.name,
                        modifier = Modifier.padding(top = 4.dp).height(150.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.FillHeight
                    )

                    Card(
                        Modifier.padding(horizontal = 4.dp)
                            .padding(bottom = 12.dp).fillMaxWidth(),
                        RoundedCornerShape(12.dp),
                        Color.White,
                        elevation = 8.dp
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            when (professional) {
                                ONCOLOGIST, NURSE -> OncologistAndNurseLayout(isPortrait)
                                DOCTOR -> DoctorLayout(isPortrait)
                                PHARMACIST -> PharmacistLayout(isPortrait)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OncologistAndNurseLayout(isPortrait: Boolean) {
        val careDescription = if (professional == ONCOLOGIST) {
            buildAnnotatedString {
                append("Depending on the treatment(s) you have received, you may be seeing a ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("surgical") }
                append(", and/or ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("radiation") }
                append(", and/or ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("medical") }
                append(" oncologist, usually alternately.")
            }
        } else {
            buildAnnotatedString {
                append("You may see an ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("advanced practice nurse (APN)") }
                append(" at the cancer centre. Under the ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("supervision of an oncologist") }
                append(", he/she will perform similar roles as the oncologist.")
            }
        }

        Text(careDescription)

        val person =
            if (professional == ONCOLOGIST) professional.name.lowercase() else "APN"
        Text(
            "Your $person cares for you under both usual and shared care",
            color = if (professional == ONCOLOGIST) Color(0xFF336B87)
            else Color(0xFFF18D9E),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        if (isPortrait) {
            OncologistAndNurseCares.entries.forEach {
                Row(
                    Modifier.padding(bottom = 4.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(it.image), contentDescription = it.name,
                        modifier = Modifier.height(80.dp),
                        contentScale = ContentScale.FillHeight
                    )
                    Text(
                        it.message,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        } else {
            Row(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth(.5f)) {
                    (0..2).forEach {
                        with(OncologistAndNurseCares.entries[it]) {
                            Row(
                                Modifier.padding(bottom = 4.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(image),
                                    contentDescription = name,
                                    modifier = Modifier.height(80.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Text(
                                    message,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    (3..5).forEach {
                        with(OncologistAndNurseCares.entries[it]) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(image), contentDescription = name,
                                    modifier = Modifier.height(80.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Text(message, modifier = Modifier.padding(start = 8.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DoctorLayout(isPortrait: Boolean, modifier: Modifier = Modifier) {
        if (isPortrait) {
            DoctorUsualCareLayout(modifier)
            DoctorSharedCareLayout(modifier.padding(top = 20.dp))
        } else {
            Row(modifier.fillMaxWidth()) {
                DoctorUsualCareLayout(Modifier.padding(end = 10.dp).fillMaxWidth(.475f))
                Spacer(Modifier.fillMaxWidth(.05f))
                DoctorSharedCareLayout(Modifier.padding(start = 10.dp).fillMaxWidth())
            }
        }
    }

    @Composable
    private fun PharmacistLayout(isPortrait: Boolean, modifier: Modifier = Modifier) {
        if (isPortrait) {
            PharmacistUsualCareLayout(modifier)
            PharmacistSharedCareLayout(modifier.padding(top = 20.dp))
        } else {
            Row(modifier.fillMaxWidth()) {
                PharmacistUsualCareLayout(Modifier.padding(end = 10.dp).fillMaxWidth(.475f))
                Spacer(Modifier.fillMaxWidth(.05f))
                PharmacistSharedCareLayout(Modifier.padding(start = 10.dp).fillMaxWidth())
            }
        }
    }

    @Composable
    private fun DoctorUsualCareLayout(modifier: Modifier = Modifier) {
        Column(modifier) {
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color(0xFFA49592)
            ) {
                Text(
                    "Usual care",
                    Modifier.padding(24.dp, 8.dp),
                    Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                buildAnnotatedString {
                    append("You may see the ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("same or different") }
                    append(" family physician at ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("GP clinics") }
                    append(" and ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("polyclinics") }
                    append(" who will:")
                },
                Modifier.padding(top = 8.dp, start = 8.dp),
                color = Color(0xFFB6452C)
            )

            Row(
                Modifier.padding(top = 4.dp, start = 8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource("followup_care/care_doctor_acute.png"), null)
                Text(
                    buildAnnotatedString {
                        append("Treat your ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("acute conditions") }
                        append(" such as fever, cough, flu, etc.")
                    },
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(
                Modifier.padding(top = 4.dp, start = 8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    buildAnnotatedString {
                        append("Prevent and manage your ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("chronic diseases") }
                        append(" such as diabetes, high blood pressure")
                    },
                    modifier = Modifier.padding(end = 4.dp).fillMaxWidth(.7f)
                )

                Image(painterResource("followup_care/care_doctor_chronic.png"), null)
            }

            Row(
                Modifier.padding(top = 4.dp, start = 8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource("followup_care/care_doctor_lifestyle.png"), null,
                    Modifier.fillMaxWidth(.3f)
                )
                Text(
                    buildAnnotatedString {
                        append("Perform health screening and promote ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("healthy lifestyle") }
                    },
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }

    @Composable
    private fun DoctorSharedCareLayout(modifier: Modifier = Modifier) {
        Column(modifier) {
            Card(shape = RoundedCornerShape(8.dp), backgroundColor = Color(0xFFE99787)) {
                Text(
                    "Shared care",
                    Modifier.padding(24.dp, 8.dp),
                    Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                buildAnnotatedString {
                    append("You will see a ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("family physician") }
                    append(" at a ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("polyclinic") }
                    append(".")
                }, Modifier.padding(top = 8.dp, start = 8.dp), color = Color(0xFFB6452C)
            )

            Row(
                Modifier.padding(top = 4.dp, start = 8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource("followup_care/care_doctor_schedule.png"),
                    null,
                    Modifier.fillMaxWidth(.2f)
                )
                Text(
                    buildAnnotatedString {
                        append("By specially booking into family physician clinic sessions, you will usually see back the ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("same") }
                        append(" doctor")
                    },
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(
                Modifier.padding(top = 8.dp).fillMaxWidth().height(68.dp),
                Arrangement.SpaceEvenly, Alignment.CenterVertically
            ) {
                Image(painterResource("followup_care/care_doctor_acute.png"), null)
                Image(painterResource("followup_care/care_doctor_chronic.png"), null)
                Image(painterResource("followup_care/care_doctor_lifestyle.png"), null)
            }

            Row(
                Modifier.padding(top = 8.dp, start = 8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource("followup_care/care_breastexam.png"), null,
                    Modifier.fillMaxWidth(.2f)
                )
                Text(
                    buildAnnotatedString {
                        append("Besides providing ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("usual care") }
                        append(", this ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("trained") }
                        append(" family physician will also perform ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("breast examination at each visit") }
                        append(" and ask about your ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("cancer history") }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }

    @Composable
    private fun PharmacistUsualCareLayout(modifier: Modifier = Modifier) {
        Column(modifier) {
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color(0xFFA49592)
            ) {
                Text(
                    "Usual care",
                    Modifier.padding(24.dp, 8.dp),
                    Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(Modifier.padding(vertical = 4.dp).fillMaxWidth(), Arrangement.SpaceAround) {
                Image(painterResource("followup_care/care_pharmacist_guardian.png"), null)
                Image(painterResource("followup_care/care_pharmacist_unity.png"), null)
            }

            Image(
                painterResource("followup_care/care_pharmacist_watsons.png"), null,
                Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                buildAnnotatedString {
                    append("You may walk-in to a ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("retail store") }
                    append(" and find a ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("community pharmacist") }
                    append(" to:")
                },
                Modifier.padding(top = 8.dp, start = 8.dp),
                color = Color(0xFFB6452C)
            )

            Row(
                Modifier.padding(top = 4.dp, start = 8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    buildAnnotatedString {
                        append("Seek recommendation on medications to ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("relieve common ailments") }
                    },
                    modifier = Modifier.padding(end = 4.dp).fillMaxWidth(.7f)
                )

                Image(painterResource("followup_care/care_pharmacist_pharmacy.png"), null)
            }

            Row(
                Modifier.padding(top = 4.dp, start = 8.dp).fillMaxWidth(),
                Arrangement.SpaceAround, Alignment.Bottom
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource("followup_care/care_pharmacist_prescriptions.png"), null)
                    Text("Fill prescriptions")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource("followup_care/care_pharmacist_advice.png"), null)
                    Text("Seek health advice")
                }
            }
        }
    }

    @Composable
    private fun PharmacistSharedCareLayout(modifier: Modifier = Modifier) {
        Column(modifier) {
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color(0xFFE99787)
            ) {
                Text(
                    "Shared care",
                    Modifier.padding(24.dp, 8.dp),
                    Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                buildAnnotatedString {
                    append("A trained and assigned ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("community pharmacist") }
                    append(" from ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) { append("Watson’s") }
                    append(" will contact you regularly to:")
                },
                Modifier.padding(top = 8.dp, start = 8.dp),
                color = Color(0xFFB6452C)
            )

            Row(
                Modifier.padding(top = 8.dp, start = 8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    Modifier.fillMaxWidth(.35f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource("followup_care/care_pharmacist_watsons.png"), null)
                    Image(
                        painterResource("followup_care/care_pharmacist_watsonspharmacist.png"),
                        null
                    )
                }

                Column(Modifier.padding(top = 8.dp, start = 8.dp)) {
                    Row {
                        Text("❖", Modifier.padding(end = 4.dp))
                        Text("Assess your active problems or symptoms")
                    }
                    Row {
                        Text("❖", Modifier.padding(end = 4.dp))
                        Text("Provide medication advice")
                    }
                    Row {
                        Text("❖", Modifier.padding(end = 4.dp))
                        Text("Answer your health-related questions, including cancer")
                    }
                    Row {
                        Text("❖", Modifier.padding(end = 4.dp))
                        Text("Recommend referrals to other services or health care professionals")
                    }
                }
            }
        }
    }

    private enum class OncologistAndNurseCares(val image: String, val message: AnnotatedString) {
        MAMMOGRAM("followup_care/care_mammogram.png", buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Companion.Bold)) {
                append("Yearly mammogram")
            }
            append(" to monitor for signs of cancer returning")
        }),
        THERAPY_EFFECT("followup_care/care_therapyeffect.png", buildAnnotatedString {
            append("Manage ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("late and long-term physical effects")
            }
            append(" from cancer treatment(s)")
        }),
        GYNAECOLOGIST("followup_care/care_gynaecologist.png", buildAnnotatedString {
            append("Refer to other support services such as ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("gynaecologist")
            }
        }),
        BREAST_EXAM("followup_care/care_breastexam.png", buildAnnotatedString {
            append("Perform ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("breast examination")
            }
        }),
        BONE_EXAM("followup_care/care_bonetest.png", buildAnnotatedString {
            append("Monitor your bone health through ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("bone mineral density tests")
            }
        }),
        LAB_TEST("followup_care/care_labtest.png", buildAnnotatedString {
            append("Order ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("blood tests, CT or MRI scans")
            }
            append(" where needed")
        }),
    }
}