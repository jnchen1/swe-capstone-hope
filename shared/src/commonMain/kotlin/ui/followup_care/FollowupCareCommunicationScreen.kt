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
                    showPrevPage = true, prevAction = {
                        if (navigator.items.contains(FollowupCareOptionScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(FollowupCareOptionScreen())
                        }
                                                      },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true, nextAction = { navigator.push(FollowupCareScheduleScreen()) }
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
                                    isPortrait,
                                    Modifier.align(Alignment.CenterHorizontally)
                                )
                            } else {
                                SharedCareLayout(
                                    isPortrait,
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
        isPortrait: Boolean,
        modifier: Modifier = Modifier
    ) {


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
        isPortrait: Boolean,
        modifier: Modifier
    ) {

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
    private enum class CareOption {
        USUAL, SHARED
    }

    private enum class ScreenSize {
        COMPACT, LARGE
    }
}