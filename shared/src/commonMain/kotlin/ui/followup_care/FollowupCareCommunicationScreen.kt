package ui.followup_care

import HomeScreen
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import model.HealthcareProfessional.*
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.comparing_options.ComparingOptionsIntroScreen

@OptIn(ExperimentalResourceApi::class)
data class FollowupCareCommunicationScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.FOLLOWUP_CARE
    private val screenTitle = option.title

    private val defaultImageHeight = 240.dp
    private val imageHeightPercent = .085f
    private val continueText = "Tap here to continue"
    private val lineColor = Color(0xFF595959)
    private val orangeColor = Color(0xFFFF5B61)
    private val blueGreyColor = Color(0xFF336B87)
    private val pinkColor = Color(0xFFC61C71)
    private val salmonPinkColor = Color(0xFFF18D9E)

    @OptIn(ExperimentalMaterialApi::class)
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
                    showPrevPage = true, prevAction = { /*TODO*/ },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextSection = true, nextAction = { navigator.push(ComparingOptionsIntroScreen()) }
                )
            }
        ) {
            BoxWithConstraints {
                val boxScope = this
                val isPortrait = boxScope.maxHeight > boxScope.maxWidth
                val screenSize = if (isPortrait) {
                    if (boxScope.maxWidth < 600.dp) ScreenSize.COMPACT else ScreenSize.LARGE
                } else {
                    if (boxScope.maxHeight < 480.dp) ScreenSize.COMPACT else ScreenSize.LARGE
                }

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp).then(
                        if (isPortrait) Modifier else Modifier.verticalScroll(rememberScrollState())
                    )
                ) {

                    Text(
                        text = "B. How do health care professionals communicate about you under each follow-up care option?",
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Text(
                        text = "Click on each button to see the difference of each option.",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    var currentCareOption by remember { mutableStateOf(CareOption.USUAL) }

                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceAround) {
                        Card(
                            onClick = { currentCareOption = CareOption.USUAL },
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
                        Card(
                            onClick = { currentCareOption = CareOption.SHARED },
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color(0xFFE99787)
                        ) {
                            Text(
                                "Shared care", Modifier.padding(24.dp, 8.dp),
                                Color.White, fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Surface(
                        Modifier.padding(bottom = 8.dp).fillMaxWidth()
                            .height(boxScope.maxHeight * .9f),
                        RoundedCornerShape(12.dp), Color.White, elevation = 4.dp
                    ) {
                        Column(Modifier.fillMaxSize()) {
                            if (currentCareOption == CareOption.USUAL) {
                                UsualCareLayout(
                                    boxScope.maxWidth, boxScope.maxHeight, isPortrait, screenSize,
                                    { currentCareOption = CareOption.SHARED },
                                    Modifier.align(Alignment.CenterHorizontally)
                                )
                            } else {
                                SharedCareLayout(
                                    boxScope.maxWidth, boxScope.maxHeight, isPortrait, screenSize,
                                    Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun UsualCareLayout(
        width: Dp, height: Dp,
        isPortrait: Boolean,
        screenSize: ScreenSize,
        changeToSharedCare: () -> Unit,
        modifier: Modifier = Modifier
    ) {


        val boxTextStyle = with(MaterialTheme.typography) {
            if (screenSize == ScreenSize.COMPACT) caption else body1
        }.copy(textAlign = TextAlign.Center)

        val mammogramText = buildAnnotatedString {
            append("You will receive ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("yearly mammogram")
            }
            append(" at the cancer centre\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val oncologistText = buildAnnotatedString {
            append("You may see your oncologist\n(surgical, radiation, or medical)\nonce or twice\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val doctorText = buildAnnotatedString {
            append("You may see a GP or polyclinic doctor as many times as needed\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val tapToSharedCare =
            "Tap here or the above shared care button to see the difference"
        val doctorText2 = buildAnnotatedString {
            append("You may or may not see the same doctor each time\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = tapToSharedCare,
                    annotation = tapToSharedCare
                )
                append(tapToSharedCare)
            }
        }

        var shouldShowMammogramText by remember { mutableStateOf(false) }
        var shouldShowOncologistText by remember { mutableStateOf(false) }
        var shouldShowDoctor1Text by remember { mutableStateOf(false) }
        var shouldShowDoctor2Text by remember { mutableStateOf(false) }
        var shouldShowOncologist by remember { mutableStateOf(false) }
        var shouldShowDoctor by remember { mutableStateOf(false) }
        var shouldWiggleDoctor by remember { mutableStateOf(false) }

        LaunchedEffect("") {
            delay(3000)
            shouldShowMammogramText = true
        }

        if (shouldShowOncologist) {
            LaunchedEffect(shouldShowOncologist) {
                delay(1600)
                shouldShowOncologistText = true
            }
        }

        if (shouldShowDoctor) {
            LaunchedEffect(shouldShowOncologist) {
                delay(2400)
                shouldShowDoctor1Text = true
            }
        }

        if (shouldWiggleDoctor) {
            LaunchedEffect(shouldShowOncologist) {
                delay(1000)
                shouldShowDoctor2Text = true
            }
        }

        Surface(modifier, color = Color(0xFFA49592)) {
            Text("Usual care", Modifier.padding(16.dp, 4.dp), Color.White)
        }


        if (isPortrait) {
            val listState = rememberLazyListState()
            LazyColumn(state = listState,modifier = Modifier.padding(4.dp, 8.dp).fillMaxSize(), verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally ) {


                item {
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.5f)
                    println("Test " + visibleItems)
                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp),
                        text = buildAnnotatedString {
                            append("All health care professionals can access the ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Nation Electronic Health Records")
                            }
                            append(" to retrieve your ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("basic")
                            }
                            append(" clinical information.")
                        },
                        textAlign = TextAlign.Center
                    )

                }

                item{

                    Image(
                        painter = painterResource("followup_care/care_laptop_doctors.png"),
                        contentDescription = "National Electronic Health Records",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth().padding(8.dp,4.dp)
                    )

                }
                item {
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.3f)
                    if(visibleItems.contains(2)){
                        itemVisible = true
                    }
                    AnimatedVisibility(visible = itemVisible,enter = fadeIn()){
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp),
                            text = buildAnnotatedString {
                                append("Each health care professional records ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("their own detailed documentation")
                                }
                                append(" after seeing you.")
                            },
                            textAlign = TextAlign.Center
                        )
                    }

                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 1f)
                    if(visibleItems.contains(3)){
                        itemVisible = true
                    }

                    BoxWithConstraints{
                        val maxWidth = this.maxWidth
                        val clockwiseArrowWidth = maxWidth.value*0.30f
                        val counterArrowWidth = maxWidth.value*0.18f
                        println(clockwiseArrowWidth.dp)
                        println(counterArrowWidth.dp)
                        var imageHeight by remember{ mutableStateOf(0f)}
                        Image(
                            painter = painterResource("followup_care/care_information_sharing.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().padding(8.dp,4.dp).onGloballyPositioned {
                                coordinates ->  imageHeight = coordinates.size.height.toFloat()
                            }
                        )
                        val arrowHeight = imageHeight*0.15f
                        println(arrowHeight.dp)
                        println(imageHeight.dp)
                        AnimatedVisibility(
                        visible = itemVisible,
                        enter = scaleIn(),
                    ) {
                        Image(
                            painter = painterResource("followup_care/care_clockwise_arrow.png"),
                            contentDescription = "Clockwise Arrow",
                            modifier = Modifier.padding(start = clockwiseArrowWidth.dp,top = arrowHeight.dp).fillMaxWidth(0.40f),
                            contentScale = ContentScale.FillWidth
                        )
                        Image(
                            painter = painterResource("followup_care/care_counter_clock_arrow.png"),
                            contentDescription = "Counter Clockwise Arrow",
                            modifier = Modifier.padding(start = counterArrowWidth.dp,top = arrowHeight.dp).fillMaxWidth(0.32f),
                            contentScale = ContentScale.FillWidth
                        )
                    }

                    }

                }
                item {
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.1f)
                    if(visibleItems.contains(4)){
                        itemVisible = true
                    }
                    AnimatedVisibility(visible = true,enter = fadeIn()){
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp),
                            text = "The oncologist and polyclinic doctors can see each other's documentation" +
                                    " if they are in the same health cluster. For instance, Nation Cancer Centre" +
                                    " and SingHealth Polyclinics",
                            textAlign = TextAlign.Center
                        )
                    }

                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 1f)
                    if(visibleItems.contains(6)){
                        itemVisible = true
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally){

                        Image(
                            painter = painterResource("followup_care/care_referral.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().padding(8.dp,4.dp)
                        )

                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = scaleIn(),
                        ) {
                            Image(
                                painter = painterResource("followup_care/care_pharmacist_calling.png"),
                                contentDescription = "Pharmacist calling polyclinic doctor and oncologist",
                                modifier = Modifier.fillMaxWidth(0.75f),
                                contentScale = ContentScale.FillWidth
                            )

                        }

                    }

                }

                item {
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.3f)
                    if(visibleItems.contains(7)){
                        itemVisible = true
                    }
                    AnimatedVisibility(visible = true,enter = fadeIn()){
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp),
                            text = buildAnnotatedString {
                                append("Oncologists and polyclinic doctors may ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("initiate referrals")
                                }
                                append(" to each other depending on your needs.\n\n")
                                append("Community pharmacists may call the oncologists and " +
                                        "polyclinic doctors to clarify your prescription.")
                            },
                            textAlign = TextAlign.Center
                        )
                    }

                }


            }

        } else {
            //TODO: Change UI for Landscape orientation (sme a portrait rn)
            // Sizing of the animation has been changed, just needs to be reviewed
            val listState = rememberLazyListState()
            LazyColumn(state = listState,modifier = Modifier.padding(4.dp, 8.dp).fillMaxSize(), verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally ) {


                item {
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.5f)
                    println("Test " + visibleItems)

                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp),
                            text = buildAnnotatedString {
                                append("All health care professionals can access the ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Nation Electronic Health Records")
                                }
                                append(" to retrieve your ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("basic")
                                }
                                append(" clinical information.")
                            },
                            textAlign = TextAlign.Center
                        )

                }

                item{
                        Image(
                            painter = painterResource("followup_care/care_laptop_doctors.png"),
                            contentDescription = "National Electronic Health Records",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.6f).padding(8.dp,4.dp)
                        )

                }
                item {
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.3f)
                    if(visibleItems.contains(2)){
                        itemVisible = true
                    }
                    AnimatedVisibility(visible = itemVisible,enter = fadeIn()){
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp),
                            text = buildAnnotatedString {
                                append("Each health care professional records ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("their own detailed documentation")
                                }
                                append(" after seeing you.")
                            },
                            textAlign = TextAlign.Center
                        )
                    }

                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.75f)
                    if(visibleItems.contains(3)){
                        itemVisible = true
                    }

                    BoxWithConstraints{
                        val maxWidth = this.maxWidth
                        val clockwiseArrowWidth = maxWidth.value*0.15f
                        val counterArrowWidth = maxWidth.value*0.08f
                        println(clockwiseArrowWidth.dp)
                        println(counterArrowWidth.dp)
                        var imageHeight by remember{ mutableStateOf(0f)}
                        Image(
                            painter = painterResource("followup_care/care_information_sharing.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.6f).padding(8.dp,4.dp).onGloballyPositioned {
                                    coordinates ->  imageHeight = coordinates.size.height.toFloat()
                            }
                        )
                        val arrowHeight = imageHeight*0.12f
                        println(arrowHeight.dp)
                        println(imageHeight.dp)
                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = scaleIn(),
                        ) {
                            Image(
                                painter = painterResource("followup_care/care_clockwise_arrow.png"),
                                contentDescription = "Clockwise Arrow",
                                modifier = Modifier.padding(start = clockwiseArrowWidth.dp,top = arrowHeight.dp).fillMaxWidth(0.25f),
                                contentScale = ContentScale.FillWidth
                            )
                            Image(
                                painter = painterResource("followup_care/care_counter_clock_arrow.png"),
                                contentDescription = "Counter Clockwise Arrow",
                                modifier = Modifier.padding(start = counterArrowWidth.dp,top = arrowHeight.dp).fillMaxWidth(0.22f),
                                contentScale = ContentScale.FillWidth
                            )
                        }

                    }

                }
                item {
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.1f)
                    if(visibleItems.contains(4)){
                        itemVisible = true
                    }
                    AnimatedVisibility(visible = true,enter = fadeIn()){
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp),
                            text = "The oncologist and polyclinic doctors can see each other's documentation" +
                                    " if they are in the same health cluster. For instance, Nation Cancer Centre" +
                                    " and SingHealth Polyclinics",
                            textAlign = TextAlign.Center
                        )
                    }

                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 1f)
                    if(visibleItems.contains(6)){
                        itemVisible = true
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally){

                        Image(
                            painter = painterResource("followup_care/care_referral.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.6f).padding(8.dp,4.dp)
                        )

                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = scaleIn(),
                        ) {
                            Image(
                                painter = painterResource("followup_care/care_pharmacist_calling.png"),
                                contentDescription = "Pharmacist calling polyclinic doctor and oncologist",
                                modifier = Modifier.fillMaxWidth(0.45f),
                                contentScale = ContentScale.FillWidth
                            )

                        }

                    }

                }

                item {
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.3f)
                    if(visibleItems.contains(7)){
                        itemVisible = true
                    }
                    AnimatedVisibility(visible = true,enter = fadeIn()){
                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(8.dp),
                            text = buildAnnotatedString {
                                append("Oncologists and polyclinic doctors may ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("initiate referrals")
                                }
                                append(" to each other depending on your needs.\n")
                                append("Community pharmacists may call the oncologists and " +
                                        "polyclinic doctors to clarify your prescription.")
                            },
                            textAlign = TextAlign.Center
                        )
                    }

                }





            }
        }
    }

    @Composable
    private fun SharedCareLayout(
        width: Dp, height: Dp,
        isPortrait: Boolean, screenSize: ScreenSize,
        modifier: Modifier
    ) {
        val boxTextStyle = with(MaterialTheme.typography) {
            if (screenSize == ScreenSize.COMPACT) caption else body1
        }.copy(textAlign = TextAlign.Center)

        val startText = buildAnnotatedString {
            append("You will now see how the schedule ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("changes")
            }
            append(" under shared care\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val mammogramText = buildAnnotatedString {
            append("You will ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("continue")
            }
            append(" to receive ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("yearly mammogram")
            }
            append(" at the cancer centre\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val physicianText = buildAnnotatedString {
            append("You will see the ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("same family physician")
            }
            append(" from polyclinic\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val replaceOncologistText = buildAnnotatedString {
            append("One of the oncologist visits will be merged and  ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("replaced")
            }
            append(" with your visit to the ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("family physician")
            }
            append(" from polyclinic\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val diseaseText = buildAnnotatedString {
            append("The family physician will see you for your ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("cancer and chronic diseases")
            }
            append("\n")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(
                    tag = continueText, annotation = continueText
                )
                append(continueText)
            }
        }
        val pharmacistText = buildAnnotatedString {
            append("An assigned ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("community pharmacist")
            }
            append(" will ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("check-in")
            }
            append(" with you regularly via ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("phone calls")
            }
        }

        var shouldShowStartText by remember { mutableStateOf(true) }
        var shouldShowMammogramText by remember { mutableStateOf(false) }
        var shouldShowPhysicianText by remember { mutableStateOf(false) }
        var shouldShowReplaceText by remember { mutableStateOf(false) }
        var shouldShowPhysicianExplanationText by remember { mutableStateOf(false) }
        var shouldShowPharmacistText by remember { mutableStateOf(false) }
        var shouldWiggleMammogram by remember { mutableStateOf(false) }
        var shouldHidePhysician by remember { mutableStateOf(false) }
        var shouldReplaceOncologist by remember { mutableStateOf(false) }
        var shouldShowPharmacist by remember { mutableStateOf(false) }
        val replaceTextAlpha = remember { Animatable(0f) }

        if (shouldShowReplaceText) {
            LaunchedEffect(shouldShowReplaceText) {
                replaceTextAlpha.animateTo(1f, tween(500, 1400, LinearEasing))
            }
        }

        Surface(modifier, color = Color(0xFFE99787)) {
            Text("Shared care", Modifier.padding(16.dp, 4.dp), Color.White)
        }

        if (isPortrait) {
            val listState = rememberLazyListState()
            LazyColumn(state = listState,modifier = Modifier.padding(4.dp, 8.dp).fillMaxSize(), verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally ) {


                item{
                        Image(
                            painter = painterResource("followup_care/care_laptop_doctors.png"),
                            contentDescription = "National Electronic Health Records",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().padding(8.dp,4.dp)
                        )

                }
                item {
                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp),
                        text = buildAnnotatedString {

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Similar to usual care")
                            }
                            append(", your care team (oncologist, polyclinic, family physician, and community pharmacist)" +
                                    " can access the ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("National Electronic Health Records")
                            }
                            append(".")
                        },
                        textAlign = TextAlign.Center
                    )


                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 1f)
                    if(visibleItems.contains(2)){
                        itemVisible = true
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top){
                        Image(
                            painter = painterResource("followup_care/care_oncologist_nurse.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.37f).padding(8.dp,4.dp)
                        )
                        Image(
                            painter = painterResource("followup_care/care_doctor.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.4f).padding(8.dp,4.dp)
                        )
                        Image(
                            painter = painterResource("followup_care/care_pharmacist.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.9f).padding(8.dp,4.dp)
                        )

                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom){

                        AnimatedContent(
                            targetState = itemVisible,
                            transitionSpec = {
//                                fadeIn(animationSpec = tween(15)).togetherWith(
//                                    fadeOut(animationSpec = tween(durationMillis = 15)))
                                scaleIn().togetherWith(
                                    fadeOut( ))
                            }
                        ) {
                                itemVisible ->
                            Image(
                                painter = painterResource(if (itemVisible) "followup_care/care_blue_document_no_arrow.png" else "followup_care/care_blue_document.png"),
                                contentDescription = "Share Care Blue Document information sharing",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(if (itemVisible) 0.20f else 0.20f).padding(8.dp,4.dp)
                            )
                        }
                        AnimatedContent(
                            targetState = itemVisible,
                            transitionSpec = {
//                                fadeIn(animationSpec = tween(15)).togetherWith(
//                                    fadeOut(animationSpec = tween(durationMillis = 15)))
                                scaleIn().togetherWith(
                                    fadeOut( ))
                            }
                        ) {
                                itemVisible ->
                            Image(
                                painter = painterResource(if (itemVisible) "followup_care/care_red_document_three_arrows.png" else "followup_care/care_red_document.png"),
                                contentDescription = "Share Care Red Document information sharing",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(if (itemVisible) 0.51f else 0.25f).padding(8.dp,4.dp)
                            )
                        }
                        AnimatedContent(
                            targetState = itemVisible,
                            transitionSpec = {
//                                fadeIn(animationSpec = tween(15)).togetherWith(
//                                    fadeOut(animationSpec = tween(15)))
                                scaleIn().togetherWith(
                                    fadeOut( ))

                            }
                        ) {
                                itemVisible ->
                            Image(
                                painter = painterResource(if (itemVisible) "followup_care/care_black_document_no_arrow.png" else "followup_care/care_black_document.png"),
                                contentDescription = "Share Care Black Document information sharing",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(if (itemVisible) 0.5f else 0.3445f).padding(8.dp,4.dp)
                            )
                        }

                    }

                }
                item {

                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp),
                        text = buildAnnotatedString {
                            append("The ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("detailed documentation")
                            }
                            append(" recorded by each health care professional will be consolidated into a ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("common shared care plan")
                            }
                            append(" and shared between them.")
                        },
                        textAlign = TextAlign.Center
                    )


                }



                item {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                        Image(
                            painter = painterResource("followup_care/care_cancer_checklist.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.5f).padding(8.dp,4.dp)
                        )

                        Column{
                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp),
                                text = buildAnnotatedString {
                                    append("Because of this ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("shared care plan")
                                    }
                                    append(", when you see your oncologist, polyclinic doctor, or pharmacist, he/she will:")

                                },
                                textAlign = TextAlign.Start
                            )
                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp,0.dp),
                                text = buildAnnotatedString {
                                    append("\u2022")
                                    append("\t\tKnow your cancer history\n")
                                    append("\u2022")
                                    append("\t\tKnow what happened at your previous visit(s) with other health care professionals.")
                                },
                                textAlign = TextAlign.Start
                            )

                        }

                    }

                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 1f)
                    if(visibleItems.contains(6)){
                        itemVisible = true
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally){

                        Image(
                            painter = painterResource("followup_care/care_referral.png"),
                            contentDescription = "Cancer Checklist",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().padding(8.dp,8.dp)
                        )

                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = scaleIn(),
                        ) {
                            Image(
                                painter = painterResource("followup_care/care_pharmacist_calling.png"),
                                contentDescription = "Pharmacist calling polyclinic doctor and oncologist",
                                modifier = Modifier.fillMaxWidth(0.75f),
                                contentScale = ContentScale.FillWidth
                            )

                        }

                    }

                }

                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){

                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(12.dp,top = 8.dp, bottom = 4.dp),
                            text = "Besides routine referrals and calls, health care " +
                                    "professionals in your care team can also:",
                            textAlign = TextAlign.Center
                        )
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                            Image(
                                painter = painterResource("followup_care/care_checklist.png"),
                                contentDescription = "Checklist",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(0.35f).padding(8.dp,4.dp)
                            )

                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp),
                                text = buildAnnotatedString {
                                    append("Write notes to each other in your ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("shared care plan")
                                    }
                                },
                                textAlign = TextAlign.Start
                            )

                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp),
                                text = buildAnnotatedString {
                                    append("Clarify questions in \ncommon ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("group chats")
                                    }
                                },
                                textAlign = TextAlign.End
                            )

                            Image(
                                painter = painterResource("followup_care/care_group_chat.png"),
                                contentDescription = "Phone chats",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(0.45f).padding(8.dp,4.dp)
                            )

                        }

                    }

                }

            }

        } else {

            val listState = rememberLazyListState()
            LazyColumn(state = listState,modifier = Modifier.padding(4.dp, 8.dp).fillMaxSize(), verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally ) {


                item{
                        Image(
                            painter = painterResource("followup_care/care_laptop_doctors.png"),
                            contentDescription = "National Electronic Health Records",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.4f).padding(8.dp,4.dp)
                        )

                }
                item {
                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp),
                        text = buildAnnotatedString {

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Similar to usual care")
                            }
                            append(", your care team (oncologist, polyclinic, family physician, and community pharmacist)" +
                                    " can access the ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("National Electronic Health Records")
                            }
                            append(".")
                        },
                        textAlign = TextAlign.Center
                    )


                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.5f)
                    if(visibleItems.contains(2)){
                        itemVisible = true
                    }

                    Row(modifier = Modifier.fillMaxWidth(0.4f), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top){
                        Image(
                            painter = painterResource("followup_care/care_oncologist_nurse.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.37f).padding(8.dp,4.dp)
                        )
                        Image(
                            painter = painterResource("followup_care/care_doctor.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.4f).padding(8.dp,4.dp)
                        )
                        Image(
                            painter = painterResource("followup_care/care_pharmacist.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.9f).padding(8.dp,4.dp)
                        )

                    }
                    Row(modifier = Modifier.fillMaxWidth(0.4f), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom){

                        AnimatedContent(
                            targetState = itemVisible,
                            transitionSpec = {
//                                fadeIn(animationSpec = tween(15)).togetherWith(
//                                    fadeOut(animationSpec = tween(durationMillis = 15)))
                                scaleIn().togetherWith(
                                    fadeOut( ))
                            }
                        ) {
                                itemVisible ->
                            Image(
                                painter = painterResource(if (itemVisible) "followup_care/care_blue_document_no_arrow.png" else "followup_care/care_blue_document.png"),
                                contentDescription = "Share Care Blue Document information sharing",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(if (itemVisible) 0.20f else 0.20f).padding(8.dp,4.dp)
                            )
                        }
                        AnimatedContent(
                            targetState = itemVisible,
                            transitionSpec = {
//                                fadeIn(animationSpec = tween(15)).togetherWith(
//                                    fadeOut(animationSpec = tween(durationMillis = 15)))
                                scaleIn().togetherWith(
                                    fadeOut( ))
                            }
                        ) {
                                itemVisible ->
                            Image(
                                painter = painterResource(if (itemVisible) "followup_care/care_red_document_three_arrows.png" else "followup_care/care_red_document.png"),
                                contentDescription = "Share Care Red Document information sharing",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(if (itemVisible) 0.51f else 0.25f).padding(8.dp,4.dp)
                            )
                        }
                        AnimatedContent(
                            targetState = itemVisible,
                            transitionSpec = {
//                                fadeIn(animationSpec = tween(15)).togetherWith(
//                                    fadeOut(animationSpec = tween(15)))
                                scaleIn().togetherWith(
                                    fadeOut( ))

                            }
                        ) {
                                itemVisible ->
                            Image(
                                painter = painterResource(if (itemVisible) "followup_care/care_black_document_no_arrow.png" else "followup_care/care_black_document.png"),
                                contentDescription = "Share Care Black Document information sharing",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(if (itemVisible) 0.5f else 0.3445f).padding(8.dp,4.dp)
                            )
                        }

                    }

                }
                item {

                    Text(
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp),
                        text = buildAnnotatedString {
                            append("The ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("detailed documentation")
                            }
                            append(" recorded by each health care professional will be consolidated into a ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("common shared care plan")
                            }
                            append(" and shared between them.")
                        },
                        textAlign = TextAlign.Center
                    )


                }



                item {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                        Image(
                            painter = painterResource("followup_care/care_cancer_checklist.png"),
                            contentDescription = "Usual Care information sharing",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(0.15f).padding(8.dp,4.dp)
                        )

                        Column{
                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp),
                                text = buildAnnotatedString {
                                    append("Because of this ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("shared care plan")
                                    }
                                    append(", when you see your oncologist, polyclinic doctor, or pharmacist, he/she will:")

                                },
                                textAlign = TextAlign.Start
                            )
                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp,0.dp),
                                text = buildAnnotatedString {
                                    append("\u2022")
                                    append("\t\tKnow your cancer history\n")
                                    append("\u2022")
                                    append("\t\tKnow what happened at your previous visit(s) with other health care professionals.")
                                },
                                textAlign = TextAlign.Start
                            )

                        }

                    }

                }

                item{
                    var itemVisible by remember { mutableStateOf(false) }
                    val visibleItems = listState.visibleItemsWithThreshold(percentThreshold = 0.7f)
                    if(visibleItems.contains(5)){
                        itemVisible = true
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally,  modifier = Modifier.fillMaxWidth(0.4f)){

                        Image(
                            painter = painterResource("followup_care/care_referral.png"),
                            contentDescription = "Cancer Checklist",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().padding(8.dp,8.dp)
                        )

                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = scaleIn(),
                        ) {
                            Image(
                                painter = painterResource("followup_care/care_pharmacist_calling.png"),
                                contentDescription = "Pharmacist calling polyclinic doctor and oncologist",
                                modifier = Modifier.fillMaxWidth(0.75f),
                                contentScale = ContentScale.FillWidth
                            )

                        }

                    }

                }

                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){

                        Text(
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(12.dp,top = 8.dp, bottom = 4.dp),
                            text = "Besides routine referrals and calls, health care " +
                                    "professionals in your care team can also:",
                            textAlign = TextAlign.Center
                        )
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                            Image(
                                painter = painterResource("followup_care/care_checklist.png"),
                                contentDescription = "Checklist",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(0.15f).padding(8.dp,4.dp)
                            )

                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp),
                                text = buildAnnotatedString {
                                    append("Write notes to each other in your ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("shared care plan")
                                    }
                                },
                                textAlign = TextAlign.Start
                            )

                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                            Text(
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(8.dp),
                                text = buildAnnotatedString {
                                    append("Clarify questions in \ncommon ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("group chats")
                                    }
                                },
                                textAlign = TextAlign.End
                            )

                            Image(
                                painter = painterResource("followup_care/care_group_chat.png"),
                                contentDescription = "Phone chats",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth(0.15f).padding(8.dp,4.dp)
                            )

                        }

                    }

                }

            }



        }
    }
    @Composable
    fun AnimatedItem(
        enter: EnterTransition = fadeIn(),
        exit: ExitTransition = fadeOut(),
        content: @Composable ()-> Unit){
        val state = remember{
            MutableTransitionState(false).apply{
            targetState = true
        }
        }
        AnimatedVisibility(
            visibleState = state,
            enter = enter,
            exit = exit

        ){
            content()
        }
    }

    @Composable
    private fun LazyListState.visibleItemsWithThreshold(percentThreshold: Float): List<Int> {

        return remember(this) {
            derivedStateOf {
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                if (layoutInfo.totalItemsCount == 0) {
                    emptyList()
                } else {
                    val fullyVisibleItemsInfo = visibleItemsInfo.toMutableList()
                    val lastItem = fullyVisibleItemsInfo.last()

                    val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

                    if (lastItem.offset + (lastItem.size*percentThreshold) > viewportHeight) {
                        fullyVisibleItemsInfo.removeLast()
                    }

                    val firstItemIfLeft = fullyVisibleItemsInfo.firstOrNull()
                    if (firstItemIfLeft != null &&
                        firstItemIfLeft.offset + (lastItem.size*percentThreshold) < layoutInfo.viewportStartOffset) {
                        fullyVisibleItemsInfo.removeFirst()
                    }

                    fullyVisibleItemsInfo.map { it.index }
                }
            }
        }.value
    }

    @Composable
    private fun AnimationText(
        boxColor: Color,
        text: AnnotatedString,
        textStyle: TextStyle,
        modifier: Modifier,
        onClick: () -> Unit = {}
    ) {
        Box(
            modifier.wrapContentHeight().drawBehind {
                drawRoundRect(
                    color = boxColor,
                    style = Stroke(
                        width = 2f,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(16f, 16f),
                            0f
                        )
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            },
            contentAlignment = Alignment.Center
        ) {
            Column(Modifier.padding(4.dp)) {
                ClickableText(
                    text = text,
                    style = textStyle,
                    onClick = { offset ->
                        text.getStringAnnotations(offset, offset)
                            .firstOrNull()?.let { onClick.invoke() }
                    })
            }
        }
    }

    @Composable
    private fun UsualCareAnimation(
        shouldShowOncologist: Boolean,
        shouldShowDoctor: Boolean,
        shouldWiggleDoctor: Boolean,
        isPortrait: Boolean,
        screenSize: ScreenSize,
        modifier: Modifier
    ) {
        val oncologist = painterResource("followup_care/care_oncologist.png")
        val doctor1 = painterResource("followup_care/care_doctor.png")
        val doctor2 = painterResource("followup_care/care_practioner.png")
        val mammogram = painterResource("followup_care/care_mammogram.png")

        val textStyle = with(MaterialTheme.typography) {
            if (screenSize == ScreenSize.COMPACT) body1 else h3
        }.copy(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)

        var shouldShowText by remember { mutableStateOf(true) }
        val arrowBody = remember { Animatable(0f) }
        val arrow = remember { Animatable(0f) }
        val mammogramAlpha = remember { Animatable(0f) }
        val oncologist1Alpha = remember { Animatable(0f) }
        val oncologist2Alpha = remember { Animatable(0f) }
        val doctor1Alpha = remember { Animatable(0f) }
        val doctor2Alpha = remember { Animatable(0f) }
        val doctor3Alpha = remember { Animatable(0f) }
        val doctor2Rotation = remember { Animatable(0f) }
        val doctor1Rotation = remember { Animatable(0f) }
        LaunchedEffect(shouldShowText) {
            delay(100)
            shouldShowText = true
            arrowBody.animateTo(1f, animationSpec = tween(1500))
            arrow.animateTo(1f, animationSpec = tween(300))
            mammogramAlpha.animateTo(1f, animationSpec = tween(1000, easing = LinearEasing))
        }

        if (shouldShowOncologist) {
            LaunchedEffect(shouldShowOncologist) {
                oncologist1Alpha.animateTo(1f, animationSpec = tween(800, easing = LinearEasing))
                oncologist2Alpha.animateTo(1f, animationSpec = tween(800, easing = LinearEasing))
            }
        }

        if (shouldShowDoctor) {
            LaunchedEffect(shouldShowOncologist) {
                doctor1Alpha.animateTo(
                    1f,
                    animationSpec = tween(800, easing = LinearEasing)
                )
                doctor2Alpha.animateTo(
                    1f,
                    animationSpec = tween(800, easing = LinearEasing)
                )
                doctor3Alpha.animateTo(
                    1f,
                    animationSpec = tween(800, easing = LinearEasing)
                )
            }
        }

        if (shouldWiggleDoctor) {
            LaunchedEffect(shouldWiggleDoctor) {
                doctor2Rotation.animateTo(360f, animationSpec = tween(500))
                (1..5).forEach { time ->
                    when (time) {
                        1, 3 -> doctor1Rotation.animateTo(
                            -10f, tween(100, easing = LinearEasing)
                        )

                        2, 4 -> doctor1Rotation.animateTo(
                            10f, tween(200, easing = LinearEasing)
                        )

                        else -> doctor1Rotation.animateTo(
                            0f, tween(100, easing = LinearEasing)
                        )
                    }
                }
            }
        }

        if (isPortrait) {
            UsualCareAnimationPortrait(
                textStyle,
                oncologist, mammogram, doctor1, doctor2,
                shouldShowText, shouldWiggleDoctor,
                arrowBody, arrow,
                mammogramAlpha, oncologist1Alpha, oncologist2Alpha,
                doctor1Alpha, doctor2Alpha, doctor3Alpha, doctor1Rotation, doctor2Rotation,
                modifier
            )
        } else {
            UsualCareAnimationLandscape(
                textStyle, oncologist, mammogram, doctor1, doctor2,
                shouldShowText, shouldWiggleDoctor,
                arrowBody, arrow,
                mammogramAlpha, oncologist1Alpha, oncologist2Alpha,
                doctor1Alpha, doctor2Alpha, doctor3Alpha,
                doctor1Rotation, doctor2Rotation,
                modifier.fillMaxSize(),/*.autoWrapHeight()*/
            )
        }
    }

    @Composable
    private fun UsualCareAnimationLandscape(
        textStyle: TextStyle,
        oncologist: Painter,
        mammogram: Painter,
        doctor1: Painter,
        doctor2: Painter,
        shouldShowText: Boolean,
        shouldWiggleDoctor: Boolean,
        arrowBody: Animatable<Float, AnimationVector1D>,
        arrow: Animatable<Float, AnimationVector1D>,
        mammogramAlpha: Animatable<Float, AnimationVector1D>,
        oncologist1Alpha: Animatable<Float, AnimationVector1D>,
        oncologist2Alpha: Animatable<Float, AnimationVector1D>,
        doctor1Alpha: Animatable<Float, AnimationVector1D>,
        doctor2Alpha: Animatable<Float, AnimationVector1D>,
        doctor3Alpha: Animatable<Float, AnimationVector1D>,
        doctor1Rotation: Animatable<Float, AnimationVector1D>,
        doctor2Rotation: Animatable<Float, AnimationVector1D>,
        modifier: Modifier = Modifier
    ) {
        val density = LocalDensity.current
        val textMeasurer = rememberTextMeasurer()

        Canvas(modifier) {
            val height = size.height * 4f
            val imageSize = with(density) {
                val imageHeight =
                    (height * .2f).dp.takeIf { it < defaultImageHeight } ?: defaultImageHeight
                val ratio =
                    oncologist.intrinsicSize.height / oncologist.intrinsicSize.width
                Size(imageHeight.value / ratio, imageHeight.value)
            }

            val textLayoutSize = textMeasurer.measure("1 year period", textStyle)

            val horizontalLineY = imageSize.height * 1.2f
            val textBoxSize =
                Size(textLayoutSize.size.width * 1.1f, textLayoutSize.size.height * 1.2f)
            val textBoxOffset = Offset(0f, horizontalLineY - textBoxSize.center.y)

            if (shouldShowText) {
                drawLine(
                    lineColor,
                    start = Offset(textBoxSize.center.x, horizontalLineY),
                    end = Offset((size.width * .96f) * arrowBody.value, horizontalLineY),
                    strokeWidth = height * 0.02f
                )
                val path = Path().apply {
                    moveTo(
                        size.width * .96f + (size.width * .04f * arrow.value),
                        horizontalLineY
                    )
                    lineTo(size.width * .96f, horizontalLineY - height * .055f)
                    lineTo(size.width * .96f, horizontalLineY + height * .055f)
                    close()
                }
                drawPath(path, lineColor)


                drawRoundRect(
                    lineColor, textBoxOffset,
                    textBoxSize, CornerRadius(20f, 20f)
                )
                drawText(
                    textLayoutSize, Color.White,
                    Offset(
                        textBoxSize.center.x - textLayoutSize.size.center.x,
                        textBoxOffset.y
                    )
                )
            }

            val circleRadius = textBoxSize.height * .3f

            val pinkOffset = Offset(size.width * .57f, horizontalLineY)
            drawCircle(pinkColor, circleRadius, pinkOffset, mammogramAlpha.value)
            translate(pinkOffset.x - imageSize.height * .5f) {
                with(mammogram) {
                    draw(
                        Size(imageSize.height * .9f, imageSize.height * .9f),
                        mammogramAlpha.value
                    )
                }
            }

            val orange1Offset =
                Offset(textBoxSize.width + size.width * .03f, horizontalLineY)
            val bottomImageY = textBoxOffset.y + textBoxSize.height
            drawCircle(orangeColor, circleRadius, orange1Offset, doctor1Alpha.value)
            translate(orange1Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor1) {
                    if (!shouldWiggleDoctor) draw(imageSize, doctor1Alpha.value)
                    else rotate(doctor1Rotation.value, imageSize.center) {
                        draw(imageSize, doctor1Alpha.value)
                    }
                }
            }

            val blue1Offset =
                Offset(textBoxSize.width + size.width * .1f, horizontalLineY)
            drawCircle(blueGreyColor, circleRadius, blue1Offset, oncologist1Alpha.value)
            translate(blue1Offset.x - imageSize.center.x) {
                with(oncologist) { draw(imageSize, oncologist1Alpha.value) }
            }

            val orange2Offset =
                Offset(textBoxSize.width + size.width * .25f, horizontalLineY)
            drawCircle(orangeColor, circleRadius, orange2Offset, doctor2Alpha.value)
            translate(orange2Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor2) {
                    if (!shouldWiggleDoctor) draw(imageSize, doctor2Alpha.value)
                    else rotate(doctor2Rotation.value, imageSize.center) {
                        draw(imageSize, doctor2Alpha.value)
                    }
                }
            }

            val blue2Offset = Offset(size.center.x + size.width * .225f, horizontalLineY)
            drawCircle(blueGreyColor, circleRadius, blue2Offset, oncologist2Alpha.value)
            translate(blue2Offset.x - imageSize.center.x) {
                with(oncologist) { draw(imageSize, oncologist2Alpha.value) }
            }

            val orange3Offset = Offset(size.width * .78f, horizontalLineY)
            drawCircle(orangeColor, circleRadius, orange3Offset, doctor3Alpha.value)
            translate(orange3Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor1) {
                    if (!shouldWiggleDoctor) draw(imageSize, doctor3Alpha.value)
                    else rotate(doctor1Rotation.value, imageSize.center) {
                        draw(imageSize, doctor3Alpha.value)
                    }
                }
            }
        }
    }

    @Composable
    private fun UsualCareAnimationPortrait(
        textStyle: TextStyle,
        oncologist: Painter,
        mammogram: Painter,
        doctor1: Painter,
        doctor2: Painter,
        shouldShowText: Boolean,
        shouldWiggleDoctor: Boolean,
        arrowBody: Animatable<Float, AnimationVector1D>,
        arrow: Animatable<Float, AnimationVector1D>,
        mammogramAlpha: Animatable<Float, AnimationVector1D>,
        oncologist1Alpha: Animatable<Float, AnimationVector1D>,
        oncologist2Alpha: Animatable<Float, AnimationVector1D>,
        doctor1Alpha: Animatable<Float, AnimationVector1D>,
        doctor2Alpha: Animatable<Float, AnimationVector1D>,
        doctor3Alpha: Animatable<Float, AnimationVector1D>,
        doctor1Rotation: Animatable<Float, AnimationVector1D>,
        doctor2Rotation: Animatable<Float, AnimationVector1D>,
        modifier: Modifier = Modifier
    ) {
        val density = LocalDensity.current
        val textMeasurer = rememberTextMeasurer()

        Canvas(modifier) {
            val imageSize = with(density) {
                val imageHeight =
                    (size.height * imageHeightPercent).dp.takeIf { it < defaultImageHeight }
                        ?: defaultImageHeight
                val ratio =
                    oncologist.intrinsicSize.height / oncologist.intrinsicSize.width
                Size(imageHeight.value / ratio, imageHeight.value)
            }

            val textLayoutSize = textMeasurer.measure("1 year period", textStyle)

            val middleX = size.width * .5f
            val textBoxSize =
                Size(textLayoutSize.size.width * 1.2f, textLayoutSize.size.height * 1.4f)
            val textBoxOffset = Offset(middleX - textBoxSize.center.x, 0f)

            if (shouldShowText) {
                drawLine(
                    lineColor,
                    start = Offset(middleX, textBoxOffset.y),
                    end = Offset(middleX, (size.height * .977f) * arrowBody.value),
                    strokeWidth = size.width * 0.02f
                )

                val path = Path().apply {
                    moveTo(
                        middleX,
                        size.height * .975f + (size.height * .025f * arrow.value)
                    )
                    lineTo(middleX - size.width * .055f, size.height * .975f)
                    lineTo(middleX + size.width * .055f, size.height * .975f)
                }
                drawPath(path, lineColor)

                drawRoundRect(
                    lineColor, textBoxOffset,
                    textBoxSize, CornerRadius(20f, 20f)
                )
                drawText(
                    textLayoutSize, Color.White, Offset(
                        textBoxOffset.x + textBoxSize.center.x - textLayoutSize.size.center.x,
                        textBoxSize.center.y - textLayoutSize.size.center.y
                    )
                )
            }

            val circleRadius = textBoxSize.height * .25f
            val leftImageOffsetX = middleX - imageSize.width * 1.5f
            val rightImageOffsetX = middleX + imageSize.width * .5f

            val pinkOffset = Offset(middleX, size.height * .5f)
            drawCircle(pinkColor, circleRadius, pinkOffset, mammogramAlpha.value)
            translate(
                middleX - imageSize.height * 1.1f,
                pinkOffset.y - imageSize.height * .5f
            ) {
                with(mammogram) {
                    draw(
                        Size(imageSize.height * .9f, imageSize.height * .9f),
                        mammogramAlpha.value
                    )
                }
            }

            val doctor1Offset = Offset(middleX, size.height * .1f)
            drawCircle(orangeColor, circleRadius, doctor1Offset, doctor1Alpha.value)
            translate(rightImageOffsetX, doctor1Offset.y - imageSize.center.y) {
                with(doctor1) {
                    if (!shouldWiggleDoctor) draw(imageSize, doctor1Alpha.value)
                    else rotate(doctor1Rotation.value, imageSize.center) {
                        draw(imageSize, doctor1Alpha.value)
                    }
                }
            }

            val oncologist1Offset = Offset(middleX, size.height * .2f)
            drawCircle(blueGreyColor, circleRadius, oncologist1Offset, oncologist1Alpha.value)
            translate(leftImageOffsetX, oncologist1Offset.y - imageSize.center.y) {
                with(oncologist) { draw(imageSize, oncologist1Alpha.value) }
            }

            val doctor2Offset = Offset(middleX, size.height * .4f)
            drawCircle(orangeColor, circleRadius, doctor2Offset, doctor2Alpha.value)
            translate(rightImageOffsetX, doctor2Offset.y - imageSize.center.y) {
                with(doctor2) {
                    if (!shouldWiggleDoctor) draw(imageSize, doctor2Alpha.value)
                    else rotate(doctor2Rotation.value, imageSize.center) {
                        draw(imageSize, doctor2Alpha.value)
                    }
                }
            }

            val oncologist2Offset = Offset(middleX, size.height * .7f)
            drawCircle(blueGreyColor, circleRadius, oncologist2Offset, oncologist2Alpha.value)
            translate(leftImageOffsetX, oncologist2Offset.y - imageSize.center.y) {
                with(oncologist) { draw(imageSize, oncologist2Alpha.value) }
            }

            val doctor3Offset = Offset(middleX, size.height * .8f)
            drawCircle(orangeColor, circleRadius, doctor3Offset, doctor3Alpha.value)
            translate(rightImageOffsetX, doctor3Offset.y - imageSize.center.y) {
                with(doctor1) {
                    if (!shouldWiggleDoctor) draw(imageSize, doctor3Alpha.value)
                    else rotate(doctor1Rotation.value, imageSize.center) {
                        draw(imageSize, doctor3Alpha.value)
                    }
                }
            }
        }
    }

    @Composable
    private fun SharedCareAnimation(
        shouldWiggleMammogram: Boolean,
        shouldHidePhysician: Boolean,
        shouldReplaceOncologist: Boolean,
        shouldShowPharmacist: Boolean,
        isPortrait: Boolean, screenSize: ScreenSize,
        modifier: Modifier
    ) {
        val oncologist = painterResource("followup_care/care_oncologist.png")
        val doctor1 = painterResource("followup_care/care_doctor.png")
        val doctor2 = painterResource("followup_care/care_practioner.png")
        val mammogram = painterResource("followup_care/care_mammogram.png")
        val pharmacist = painterResource("followup_care/care_pharmacist.png")

        val textStyle = with(MaterialTheme.typography) {
            if (screenSize == ScreenSize.COMPACT) body1 else h3
        }.copy(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)

        val physicianAlpha = remember { Animatable(1f) }
        val doctorAlpha = remember { Animatable(0f) }
        val mammogramRotation = remember { Animatable(0f) }
        val doctorRotation = remember { Animatable(0f) }
        val dotMovement = remember { Animatable(0f) }
        val doctorMovement = remember { Animatable(0f) }
        val pharmacist1Alpha = remember { Animatable(0f) }
        val pharmacist2Alpha = remember { Animatable(0f) }
        val pharmacist3Alpha = remember { Animatable(0f) }

        if (shouldWiggleMammogram) {
            LaunchedEffect(shouldWiggleMammogram) {
                (1..5).forEach { time ->
                    when (time) {
                        1, 3 -> mammogramRotation.animateTo(
                            -10f,
                            tween(100, easing = LinearEasing)
                        )

                        2, 4 -> mammogramRotation.animateTo(
                            10f,
                            tween(200, easing = LinearEasing)
                        )

                        else -> mammogramRotation.animateTo(
                            0f,
                            tween(100, easing = LinearEasing)
                        )
                    }
                }
            }
        }

        if (shouldHidePhysician) {
            LaunchedEffect(shouldHidePhysician) {
                physicianAlpha.animateTo(0f, tween(800, easing = EaseOutBack))
                doctorAlpha.animateTo(1f, tween(800, easing = EaseIn))
                (1..5).forEach { time ->
                    when (time) {
                        1, 3 -> doctorRotation.animateTo(
                            -10f,
                            tween(100, easing = LinearEasing)
                        )

                        2, 4 -> doctorRotation.animateTo(
                            10f,
                            tween(200, easing = LinearEasing)
                        )

                        else -> doctorRotation.animateTo(
                            0f,
                            tween(100, easing = LinearEasing)
                        )
                    }
                }
            }
        }

        if (shouldReplaceOncologist) {
            LaunchedEffect(shouldReplaceOncologist) {
                dotMovement.animateTo(1f, tween(800))
                doctorMovement.animateTo(1f, tween(800))
            }
        }

        if (shouldShowPharmacist) {
            LaunchedEffect(shouldShowPharmacist) {
                pharmacist1Alpha.animateTo(1f, tween(800))
                pharmacist2Alpha.animateTo(1f, tween(800))
                pharmacist3Alpha.animateTo(1f, tween(800))
            }
        }

        if (isPortrait) {
            SharedCareAnimationPortrait(
                textStyle, mammogram, oncologist, doctor1, doctor2, pharmacist,
                mammogramRotation, doctorMovement, dotMovement, physicianAlpha,
                doctorRotation, doctorAlpha,
                pharmacist1Alpha, pharmacist2Alpha, pharmacist3Alpha,
                modifier
            )
        } else {
            SharedCareAnimationLandscape(
                textStyle, mammogram, oncologist, doctor1, doctor2, pharmacist,
                mammogramRotation, doctorRotation, physicianAlpha,
                doctorAlpha, doctorMovement, dotMovement,
                pharmacist1Alpha, pharmacist2Alpha, pharmacist3Alpha,
                modifier/*.autoWrapHeight()*/
            )
        }
    }

    @Composable
    private fun SharedCareAnimationPortrait(
        textStyle: TextStyle,
        mammogram: Painter,
        oncologist: Painter,
        doctor1: Painter,
        doctor2: Painter,
        pharmacist: Painter,
        mammogramRotation: Animatable<Float, AnimationVector1D>,
        doctorMovement: Animatable<Float, AnimationVector1D>,
        dotMovement: Animatable<Float, AnimationVector1D>,
        physicianAlpha: Animatable<Float, AnimationVector1D>,
        doctorRotation: Animatable<Float, AnimationVector1D>,
        doctorAlpha: Animatable<Float, AnimationVector1D>,
        pharmacist1Alpha: Animatable<Float, AnimationVector1D>,
        pharmacist2Alpha: Animatable<Float, AnimationVector1D>,
        pharmacist3Alpha: Animatable<Float, AnimationVector1D>,
        modifier: Modifier
    ) {
        val density = LocalDensity.current
        val textMeasurer = rememberTextMeasurer()

        Canvas(modifier) {
            val imageSize = with(density) {
                val imageHeight =
                    (size.height * imageHeightPercent).dp.takeIf { it < defaultImageHeight }
                        ?: defaultImageHeight
                val ratio = oncologist.intrinsicSize.height / oncologist.intrinsicSize.width
                Size(imageHeight.value / ratio, imageHeight.value)
            }
            val pharmacistSize = (imageSize.height * 1.1f).let { imageHeight ->
                val ratio =
                    pharmacist.intrinsicSize.height / pharmacist.intrinsicSize.width
                Size(imageHeight / ratio, imageHeight)
            }

            val textLayoutSize = textMeasurer.measure("1 year period", textStyle)

            val middleX = size.width * .5f
            val textBoxSize =
                Size(textLayoutSize.size.width * 1.2f, textLayoutSize.size.height * 1.4f)
            val textBoxOffset = Offset(middleX - textBoxSize.center.x, 0f)

            drawLine(
                lineColor,
                start = Offset(middleX, textBoxOffset.y),
                end = Offset(middleX, size.height * .977f),
                strokeWidth = size.width * 0.02f
            )

            val path = Path().apply {
                moveTo(middleX, size.height)
                lineTo(middleX - size.width * .055f, size.height * .975f)
                lineTo(middleX + size.width * .055f, size.height * .975f)
            }
            drawPath(path, lineColor)

            drawRoundRect(
                lineColor, textBoxOffset,
                textBoxSize, CornerRadius(20f, 20f)
            )
            drawText(
                textLayoutSize, Color.White, Offset(
                    textBoxOffset.x + textBoxSize.center.x - textLayoutSize.size.center.x,
                    textBoxSize.center.y - textLayoutSize.size.center.y
                )
            )

            val circleRadius = textBoxSize.height * .25f
            val leftImageOffsetX = middleX - imageSize.width * 1.5f
            val rightImageOffsetX = middleX + imageSize.width * .5f

            val pinkOffset = Offset(middleX, size.height * .5f)
            drawCircle(pinkColor, circleRadius, pinkOffset)
            translate(
                middleX - imageSize.height * 1.1f,
                pinkOffset.y - imageSize.height * .5f
            ) {
                with(mammogram) {
                    rotate(mammogramRotation.value, imageSize.center) {
                        draw(Size(imageSize.height * .9f, imageSize.height * .9f))
                    }
                }
            }

            val oncologist1Offset = Offset(middleX, size.height * .2f)
            drawCircle(blueGreyColor, circleRadius, oncologist1Offset)
            translate(leftImageOffsetX, oncologist1Offset.y - imageSize.center.y) {
                with(oncologist) { draw(imageSize, 1 - doctorMovement.value) }
            }

            val orangeToBlue = size.height * .1f * dotMovement.value
            val orange1OffsetY = size.height * .1f
            val movementX = (rightImageOffsetX - leftImageOffsetX) * (doctorMovement.value)
            drawCircle(
                orangeColor, circleRadius,
                Offset(middleX, orange1OffsetY + orangeToBlue)
            )
            translate(
                rightImageOffsetX - movementX,
                orange1OffsetY + (orangeToBlue * doctorMovement.value) - imageSize.center.y
            ) {
                with(doctor1) {
                    rotate(doctorRotation.value, imageSize.center) { draw(imageSize) }
                }
            }

            val pharmacist1Offset = Offset(middleX, size.height * .3f)
            drawCircle(
                salmonPinkColor, circleRadius, pharmacist1Offset, pharmacist1Alpha.value
            )
            translate(
                middleX + pharmacistSize.width * 0.15f,
                pharmacist1Offset.y - pharmacistSize.center.y
            ) {
                with(pharmacist) { draw(pharmacistSize, pharmacist1Alpha.value) }
            }

            val doctor2Offset = Offset(middleX, size.height * .4f)
            drawCircle(orangeColor, circleRadius, doctor2Offset)
            translate(rightImageOffsetX, doctor2Offset.y - imageSize.center.y) {
                with(doctor2) { draw(imageSize, physicianAlpha.value) }
            }
            translate(rightImageOffsetX, doctor2Offset.y - imageSize.center.y) {
                with(doctor1) {
                    rotate(doctorRotation.value, imageSize.center) {
                        draw(imageSize, doctorAlpha.value)
                    }
                }
            }

            val pharmacist2Offset = Offset(middleX, size.height * .6f)
            drawCircle(salmonPinkColor, circleRadius, pharmacist2Offset, pharmacist2Alpha.value)
            translate(
                middleX + pharmacistSize.width * 0.15f,
                pharmacist2Offset.y - pharmacistSize.center.y
            ) {
                with(pharmacist) { draw(pharmacistSize, pharmacist2Alpha.value) }
            }

            val oncologist2Offset = Offset(middleX, size.height * .7f)
            drawCircle(blueGreyColor, circleRadius, oncologist2Offset)
            translate(leftImageOffsetX, oncologist2Offset.y - imageSize.center.y) {
                with(oncologist) { draw(imageSize) }
            }

            val doctor3Offset = Offset(middleX, size.height * .8f)
            drawCircle(orangeColor, circleRadius, doctor3Offset)
            translate(rightImageOffsetX, doctor3Offset.y - imageSize.center.y) {
                with(doctor1) {
                    rotate(doctorRotation.value, imageSize.center) { draw(imageSize) }
                }
            }

            val pharmacist3Offset = Offset(middleX, size.height * .9f)
            drawCircle(salmonPinkColor, circleRadius, pharmacist3Offset, pharmacist3Alpha.value)
            translate(
                middleX + pharmacistSize.width * 0.15f,
                pharmacist3Offset.y - pharmacistSize.center.y
            ) {
                with(pharmacist) { draw(pharmacistSize, pharmacist3Alpha.value) }
            }
        }
    }

    @Composable
    private fun SharedCareAnimationLandscape(
        textStyle: TextStyle,
        mammogram: Painter,
        oncologist: Painter,
        doctor1: Painter,
        doctor2: Painter,
        pharmacist: Painter,
        mammogramRotation: Animatable<Float, AnimationVector1D>,
        doctorRotation: Animatable<Float, AnimationVector1D>,
        physicianAlpha: Animatable<Float, AnimationVector1D>,
        doctorAlpha: Animatable<Float, AnimationVector1D>,
        doctorMovement: Animatable<Float, AnimationVector1D>,
        dotMovement: Animatable<Float, AnimationVector1D>,
        pharmacist1Alpha: Animatable<Float, AnimationVector1D>,
        pharmacist2Alpha: Animatable<Float, AnimationVector1D>,
        pharmacist3Alpha: Animatable<Float, AnimationVector1D>,
        modifier: Modifier
    ) {
        val density = LocalDensity.current
        val textMeasurer = rememberTextMeasurer()

        Canvas(modifier) {
            val height = size.height * 4f
            val imageSize = with(density) {
                val imageHeight =
                    (height * .2f).dp.takeIf { it < defaultImageHeight } ?: defaultImageHeight
                val ratio =
                    oncologist.intrinsicSize.height / oncologist.intrinsicSize.width
                Size(imageHeight.value / ratio, imageHeight.value)
            }
            val pharmacistSize = (imageSize.height * 1.2f).let { imageHeight ->
                val ratio =
                    pharmacist.intrinsicSize.height / pharmacist.intrinsicSize.width
                Size(imageHeight / ratio, imageHeight)
            }

            val textLayoutSize = textMeasurer.measure(
                "1 year period", textStyle
            )

            val horizontalLineY = imageSize.height * 1.2f
            val textBoxSize =
                Size(textLayoutSize.size.width * 1.1f, textLayoutSize.size.height * 1.2f)
            val textBoxOffset = Offset(0f, horizontalLineY - textBoxSize.center.y)

            drawLine(
                lineColor,
                start = Offset(textBoxSize.center.x, horizontalLineY),
                end = Offset(size.width * .96f, horizontalLineY),
                strokeWidth = height * 0.02f
            )
            val path = Path().apply {
                moveTo(
                    size.width * .96f + (size.width * .04f),
                    horizontalLineY
                )
                lineTo(size.width * .96f, horizontalLineY - height * .055f)
                lineTo(size.width * .96f, horizontalLineY + height * .055f)
                close()
            }
            drawPath(path, lineColor)

            drawRoundRect(
                lineColor, textBoxOffset,
                textBoxSize, CornerRadius(20f, 20f)
            )
            drawText(
                textLayoutSize, Color.White,
                Offset(
                    textBoxSize.center.x - textLayoutSize.size.center.x,
                    textBoxOffset.y
                )
            )

            val circleRadius = textBoxSize.height * .3f

            val pinkOffset = Offset(size.width * .57f, horizontalLineY)
            drawCircle(pinkColor, circleRadius, pinkOffset)
            translate(pinkOffset.x - imageSize.height * .5f) {
                with(mammogram) {
                    rotate(mammogramRotation.value, imageSize.center) {
                        draw(Size(imageSize.height * .9f, imageSize.height * .9f))
                    }
                }
            }

            val blue1Offset =
                Offset(textBoxSize.width + size.width * .1f, horizontalLineY)
            drawCircle(blueGreyColor, circleRadius, blue1Offset)
            translate(blue1Offset.x - imageSize.center.x) {
                with(oncologist) { draw(imageSize, 1 - doctorMovement.value) }
            }

            val orangeToBlue = size.width * .07f * dotMovement.value
            val orange1OffsetX = textBoxSize.width + size.width * .03f
            val orange1CircleOffset = Offset(orange1OffsetX + orangeToBlue, horizontalLineY)
            val bottomImageY = textBoxOffset.y + textBoxSize.height
            val orange2Y = bottomImageY * (1 - doctorMovement.value)
            drawCircle(orangeColor, circleRadius, orange1CircleOffset)
            translate(
                orange1OffsetX + (orangeToBlue * doctorMovement.value) - imageSize.center.x,
                orange2Y
            ) {
                with(doctor1) {
                    rotate(doctorRotation.value, imageSize.center) { draw(imageSize) }
                }
            }

            val pharmacist1Offset = Offset(textBoxSize.width + size.width * .175f, horizontalLineY)
            drawCircle(salmonPinkColor, circleRadius, pharmacist1Offset, pharmacist1Alpha.value)
            translate(pharmacist1Offset.x - pharmacistSize.center.x * 1.3f, bottomImageY) {
                with(pharmacist) { draw(pharmacistSize, pharmacist1Alpha.value) }
            }

            val orange2Offset =
                Offset(textBoxSize.width + size.width * .25f, horizontalLineY)
            drawCircle(orangeColor, circleRadius, orange2Offset)
            translate(orange2Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor2) { draw(imageSize, physicianAlpha.value) }
            }
            translate(orange2Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor1) {
                    rotate(doctorRotation.value, imageSize.center) {
                        draw(imageSize, doctorAlpha.value)
                    }
                }
            }

            val pharmacist2Offset = Offset(size.center.x + size.width * .15f, horizontalLineY)
            drawCircle(salmonPinkColor, circleRadius, pharmacist2Offset, pharmacist2Alpha.value)
            translate(pharmacist2Offset.x - pharmacistSize.center.x * 1.3f, bottomImageY) {
                with(pharmacist) { draw(pharmacistSize, pharmacist2Alpha.value) }
            }

            val blue2Offset = Offset(size.center.x + size.width * .225f, horizontalLineY)
            drawCircle(blueGreyColor, circleRadius, blue2Offset)
            translate(blue2Offset.x - imageSize.center.x) {
                with(oncologist) { draw(imageSize) }
            }

            val orange3Offset = Offset(size.width * .78f, horizontalLineY)
            drawCircle(orangeColor, circleRadius, orange3Offset)
            translate(orange3Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor1) {
                    rotate(doctorRotation.value, imageSize.center) { draw(imageSize) }
                }
            }

            val pharmacist3Offset = Offset(size.width * .925f, horizontalLineY)
            drawCircle(salmonPinkColor, circleRadius, pharmacist3Offset, pharmacist3Alpha.value)
            translate(pharmacist3Offset.x - pharmacistSize.center.x * 1.3f, bottomImageY) {
                with(pharmacist) { draw(pharmacistSize, pharmacist3Alpha.value) }
            }
        }
    }

    private enum class CareOption {
        USUAL, SHARED
    }

    private enum class ScreenSize {
        COMPACT, LARGE
    }
}