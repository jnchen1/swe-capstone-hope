package ui.followup_care

import HomeScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
data class FollowupCareScheduleScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "FOLLOW-UP CARE OPTION"
    private val defaultImageHeight = 240.dp

    @OptIn(ExperimentalMaterialApi::class)
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
                            color = Color(0xFFC6E0B5),
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
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
                    ) {
                        Button(
                            onClick = { /*TODO*/
                                /*if (navigator.items.contains(FollowupCareOptionScreen())) {
                                    navigator.pop()
                                } else {
                                    navigator.replace(FollowupCareOptionScreen())
                                }*/
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

                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "C. What is your follow-up schedule under each follow-up care option?",
                        style = MaterialTheme.typography.h3,
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
                            .wrapContentHeight(),
                        RoundedCornerShape(12.dp), Color.White, elevation = 4.dp
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            if (currentCareOption == CareOption.USUAL) {
                                UsualCareLayout(
                                    boxScope, { currentCareOption = CareOption.SHARED },
                                    Modifier.align(Alignment.CenterHorizontally)
                                )
                            } else {
                                SharedCareLayout(
                                    boxScope, Modifier.align(Alignment.CenterHorizontally)
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
        boxScope: BoxWithConstraintsScope,
        changeToSharedCare: () -> Unit,
        modifier: Modifier
    ) {
        val density = LocalDensity.current
        val boxTextStyle = with(density) {
            val testSize =
                (MaterialTheme.typography.caption.fontSize.value * boxScope.maxWidth.value * .005f).toSp()
            if (testSize > MaterialTheme.typography.body1.fontSize) MaterialTheme.typography.body1.fontSize
            else testSize
        }.let {
            MaterialTheme.typography.caption.copy(
                fontSize = it,
                textAlign = TextAlign.Center
            )
        }
        val bottomTextBoxOffsetX = boxScope.maxWidth * .225f
        val bottomTextBoxOffsetY =
            (boxScope.maxWidth * .15f).takeIf { it < defaultImageHeight } ?: defaultImageHeight

        val continueText = "Tap here to continue"
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

        Surface(
            modifier,
            color = Color(0xFFA49592)
        ) {
            Text("Usual care", Modifier.padding(16.dp, 4.dp), Color.White)
        }

        Box(Modifier.padding(4.dp, 8.dp).fillMaxWidth().wrapContentHeight()) {
            if (shouldShowMammogramText) {
                DashedBox(
                    Color(0xFFC61C71),
                    Modifier.align(Alignment.TopEnd)
                        .fillMaxWidth(.3f)
                ) {
                    val mammogramText = buildAnnotatedString {
                        append("You will receive ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("yearly mammogram")
                        }
                        append(" at the cancer centre\n")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                            pushStringAnnotation(
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = mammogramText,
                        style = boxTextStyle,
                        onClick = { offset ->
                            mammogramText.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowMammogramText = false
                                    shouldShowOncologist = true
                                }
                        })
                }
            }

            UsualCareAnimation(
                shouldShowOncologist,
                shouldShowDoctor,
                shouldWiggleDoctor,
                Modifier.padding(horizontal = 4.dp).fillMaxWidth()
                    .align(Alignment.Center)
            )

            if (shouldShowOncologistText) {
                DashedBox(
                    Color(0xFF336B87),
                    Modifier.padding(top = bottomTextBoxOffsetY).align(Alignment.Center)
                        .fillMaxWidth(.35f)
                ) {
                    val oncologistText = buildAnnotatedString {
                        append("You may see your oncologist\n(surgical, radiation, or medical)\nonce or twice\n")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                            pushStringAnnotation(
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = oncologistText,
                        style = boxTextStyle,
                        onClick = { offset ->
                            oncologistText.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowOncologistText = false
                                    shouldShowDoctor = true
                                }
                        })
                }
            }

            if (shouldShowDoctor1Text) {
                DashedBox(
                    Color(0xFFFF5B61),
                    Modifier.padding(start = bottomTextBoxOffsetX, top = bottomTextBoxOffsetY)
                        .fillMaxWidth(.325f)
                        .align(Alignment.Center)

                ) {
                    val doctorText = buildAnnotatedString {
                        append("You may see a GP or polyclinic doctor as many times as needed\n")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                            pushStringAnnotation(
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = doctorText,
                        style = boxTextStyle,
                        onClick = { offset ->
                            doctorText.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowDoctor1Text = false
                                    shouldWiggleDoctor = true
                                }
                        })
                }
            }

            if (shouldShowDoctor2Text) {
                DashedBox(
                    Color(0xFFFF5B61),
                    Modifier.padding(start = bottomTextBoxOffsetX, top = bottomTextBoxOffsetY)
                        .fillMaxWidth(.325f)
                        .align(Alignment.Center)

                ) {
                    val tapToSharedCare =
                        "Tap here or the above shared care button to see the difference"
                    val doctorText = buildAnnotatedString {
                        append("You may or may not see the same doctor each time\n")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                            pushStringAnnotation(
                                tag = tapToSharedCare,
                                annotation = tapToSharedCare
                            )
                            append(tapToSharedCare)
                        }
                    }
                    ClickableText(
                        text = doctorText,
                        style = boxTextStyle,
                        onClick = { offset ->
                            doctorText.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let { changeToSharedCare.invoke() }
                        })
                }
            }

        }
    }

    @Composable
    private fun SharedCareLayout(boxScope: BoxWithConstraintsScope, modifier: Modifier) {
        val boxTextStyle = with(LocalDensity.current) {
            val testSize =
                (MaterialTheme.typography.caption.fontSize.value * boxScope.maxWidth.value * .005f).toSp()
            if (testSize > MaterialTheme.typography.body1.fontSize) MaterialTheme.typography.body1.fontSize
            else testSize
        }.let {
            MaterialTheme.typography.caption.copy(fontSize = it, textAlign = TextAlign.Center)
        }
        val bottomTextBoxOffsetY =
            (boxScope.maxWidth * .15f).takeIf { it < defaultImageHeight } ?: defaultImageHeight

        val continueText = "Tap here to continue"
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

        Box(Modifier.padding(4.dp).fillMaxWidth().wrapContentHeight()) {

            SharedCareAnimation(
                shouldWiggleMammogram,
                shouldHidePhysician,
                shouldReplaceOncologist,
                shouldShowPharmacist,
                Modifier.padding(horizontal = 4.dp).fillMaxWidth().align(Alignment.Center)
            )

            if (shouldShowStartText) {
                DashedBox(
                    Color(0xFFC61C71),
                    Modifier.align(Alignment.TopStart)
                        .fillMaxWidth(.225f)
                ) {
                    val startText = buildAnnotatedString {
                        append("You will now see how the schedule ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("changes")
                        }
                        append(" under shared care\n")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                            pushStringAnnotation(
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = startText, style = boxTextStyle, onClick = { offset ->
                            startText.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowStartText = false
                                    shouldShowMammogramText = true
                                }
                        })
                }
            }

            if (shouldShowMammogramText) {
                shouldWiggleMammogram = true
                DashedBox(
                    Color(0xFFC61C71),
                    Modifier.align(Alignment.TopEnd).fillMaxWidth(.25f)
                ) {
                    val text = buildAnnotatedString {
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
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = text, style = boxTextStyle, onClick = { offset ->
                            text.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowMammogramText = false
                                    shouldShowPhysicianText = true
                                }
                        })
                }
            }

            if (shouldShowPhysicianText) {
                shouldHidePhysician = true
                DashedBox(
                    Color(0xFFC61C71),
                    Modifier.padding(top = bottomTextBoxOffsetY).align(Alignment.CenterStart)
                        .fillMaxWidth(.175f)
                ) {
                    val text = buildAnnotatedString {
                        append("You will see the ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("same family physician")
                        }
                        append(" from polyclinic\n")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                            pushStringAnnotation(
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = text, style = boxTextStyle, onClick = { offset ->
                            text.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowPhysicianText = false
                                    shouldShowReplaceText = true
                                }
                        })
                }
            }

            if (shouldShowReplaceText) {
                shouldReplaceOncologist = true
                DashedBox(
                    Color(0xFFC61C71),
                    Modifier.padding(top = bottomTextBoxOffsetY).align(Alignment.CenterStart)
                        .fillMaxWidth(.3f).alpha(replaceTextAlpha.value)
                ) {
                    val text = buildAnnotatedString {
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
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = text, style = boxTextStyle, onClick = { offset ->
                            text.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowReplaceText = false
                                    shouldShowPhysicianExplanationText = true
                                }
                        })
                }
            }

            if (shouldShowPhysicianExplanationText) {
                DashedBox(
                    Color(0xFFC61C71),
                    Modifier.padding(top = bottomTextBoxOffsetY).align(Alignment.CenterStart)
                        .fillMaxWidth(.3f)
                ) {
                    val text = buildAnnotatedString {
                        append("The family physician will see you for your ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("cancer and chronic diseases")
                        }
                        append("\n")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                            pushStringAnnotation(
                                tag = continueText,
                                annotation = continueText
                            )
                            append(continueText)
                        }
                    }
                    ClickableText(
                        text = text, style = boxTextStyle, onClick = { offset ->
                            text.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let {
                                    shouldShowPhysicianExplanationText = false
                                    shouldShowPharmacistText = true
                                }
                        })
                }
            }

            if (shouldShowPharmacistText) {
                shouldShowPharmacist = true
                DashedBox(
                    Color(0xFFC61C71),
                    Modifier.padding(top = bottomTextBoxOffsetY).align(Alignment.CenterStart)
                        .fillMaxWidth(.3f)
                ) {
                    val text = buildAnnotatedString {
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
                    Text(text = text, style = boxTextStyle)
                }
            }
        }
    }

    @Composable
    private fun DashedBox(
        borderColor: Color,
        modifier: Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Box(
            modifier.wrapContentHeight().drawBehind {
                drawRoundRect(
                    color = borderColor,
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
            Column(Modifier.padding(4.dp)) { content() }
        }
    }

    @Composable
    private fun UsualCareAnimation(
        shouldShowOncologist: Boolean,
        shouldShowDoctor: Boolean,
        shouldWiggleDoctor: Boolean,
        modifier: Modifier
    ) {
        val density = LocalDensity.current
        val lineColor = Color(0xFF595959)
        val orangeColor = Color(0xFFFF5B61)
        val blueGreyColor = Color(0xFF336B87)
        val pinkColor = Color(0xFFC61C71)

        val oncologist = painterResource("followup_care/care_oncologist.png")
        val doctor1 = painterResource("followup_care/care_doctor.png")
        val doctor2 = painterResource("followup_care/care_practioner.png")
        val mammogram = painterResource("followup_care/care_mammogram.png")

        val textMeasurer = rememberTextMeasurer()
        val ts = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Center)

        var shouldShowText by remember { mutableStateOf(false) }
        val arrowBody = remember { Animatable(0f) }
        val arrow = remember { Animatable(0f) }
        val mammogramAlpha = remember { Animatable(0f) }
        val blue1Alpha = remember { Animatable(0f) }
        val blue2Alpha = remember { Animatable(0f) }
        val orange1Alpha = remember { Animatable(0f) }
        val orange2Alpha = remember { Animatable(0f) }
        val orange3Alpha = remember { Animatable(0f) }
        val orange2Rotation = remember { Animatable(0f) }
        val orange1Rotation = remember { Animatable(0f) }
        LaunchedEffect(shouldShowText) {
            delay(100)
            shouldShowText = true
            arrowBody.animateTo(1f, animationSpec = tween(1500))
            arrow.animateTo(1f, animationSpec = tween(300))
            mammogramAlpha.animateTo(1f, animationSpec = tween(1000, easing = LinearEasing))
        }

        if (shouldShowOncologist) {
            LaunchedEffect(shouldShowOncologist) {
                blue1Alpha.animateTo(1f, animationSpec = tween(800, easing = LinearEasing))
                blue2Alpha.animateTo(1f, animationSpec = tween(800, easing = LinearEasing))
            }
        }

        if (shouldShowDoctor) {
            LaunchedEffect(shouldShowOncologist) {
                orange1Alpha.animateTo(1f, animationSpec = tween(800, easing = LinearEasing))
                orange2Alpha.animateTo(1f, animationSpec = tween(800, easing = LinearEasing))
                orange3Alpha.animateTo(1f, animationSpec = tween(800, easing = LinearEasing))
            }
        }

        if (shouldWiggleDoctor) {
            LaunchedEffect(shouldWiggleDoctor) {
                orange2Rotation.animateTo(360f, animationSpec = tween(500))
                (1..5).forEach { time ->
                    when (time) {
                        1, 3 -> orange1Rotation.animateTo(
                            -10f, tween(100, easing = LinearEasing)
                        )

                        2, 4 -> orange1Rotation.animateTo(
                            10f, tween(200, easing = LinearEasing)
                        )

                        else -> orange1Rotation.animateTo(
                            0f, tween(100, easing = LinearEasing)
                        )
                    }
                }
            }
        }

        Canvas(modifier = modifier.autoWrapHeight()) {
            val height = size.width * .35f
            val imageSize = with(density) {
                val imageHeight =
                    (size.width * .15f).dp.takeIf { it < defaultImageHeight } ?: defaultImageHeight
                val ratio =
                    oncologist.intrinsicSize.height / oncologist.intrinsicSize.width
                Size(imageHeight.value / ratio, imageHeight.value)
            }

            val textLayoutSize = textMeasurer.measure(
                "1 year period",
                style = ts.copy(
                    fontSize = ts.fontSize * size.width * .0009f,
                    fontWeight = FontWeight.Bold
                )
            )

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

            val circleRadius = textBoxSize.height * .25f

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
            drawCircle(orangeColor, circleRadius, orange1Offset, orange1Alpha.value)
            translate(orange1Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor1) {
                    if (!shouldWiggleDoctor) draw(imageSize, orange1Alpha.value)
                    else rotate(orange1Rotation.value, imageSize.center) {
                        draw(imageSize, orange1Alpha.value)
                    }
                }
            }

            val blue1Offset =
                Offset(textBoxSize.width + size.width * .1f, horizontalLineY)
            drawCircle(blueGreyColor, circleRadius, blue1Offset, blue1Alpha.value)
            translate(blue1Offset.x - imageSize.center.x) {
                with(oncologist) { draw(imageSize, blue1Alpha.value) }
            }

            val orange2Offset =
                Offset(textBoxSize.width + size.width * .25f, horizontalLineY)
            drawCircle(orangeColor, circleRadius, orange2Offset, orange2Alpha.value)
            translate(orange2Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor2) {
                    if (!shouldWiggleDoctor) draw(imageSize, orange2Alpha.value)
                    else rotate(orange2Rotation.value, imageSize.center) {
                        draw(imageSize, orange2Alpha.value)
                    }
                }
            }

            val blue2Offset = Offset(size.center.x + size.width * .225f, horizontalLineY)
            drawCircle(blueGreyColor, circleRadius, blue2Offset, blue2Alpha.value)
            translate(blue2Offset.x - imageSize.center.x) {
                with(oncologist) { draw(imageSize, blue2Alpha.value) }
            }

            val orange3Offset = Offset(size.width * .78f, horizontalLineY)
            drawCircle(orangeColor, circleRadius, orange3Offset, orange3Alpha.value)
            translate(orange3Offset.x - imageSize.center.x, bottomImageY) {
                with(doctor1) {
                    if (!shouldWiggleDoctor) draw(imageSize, orange3Alpha.value)
                    else rotate(orange1Rotation.value, imageSize.center) {
                        draw(imageSize, orange3Alpha.value)
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
        modifier: Modifier
    ) {
        val lineColor = Color(0xFF595959)
        val orangeColor = Color(0xFFFF5B61)
        val blueGreyColor = Color(0xFF336B87)
        val pinkColor = Color(0xFFC61C71)
        val salmonPinkColor = Color(0xFFF18D9E)

        val oncologist = painterResource("followup_care/care_oncologist.png")
        val doctor1 = painterResource("followup_care/care_doctor.png")
        val doctor2 = painterResource("followup_care/care_practioner.png")
        val mammogram = painterResource("followup_care/care_mammogram.png")
        val pharmacist = painterResource("followup_care/care_pharmacist.png")

        val textMeasurer = rememberTextMeasurer()
        val ts = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Center)
        val physicianAlpha = remember { Animatable(1f) }
        val doctorAlpha = remember { Animatable(0f) }
        val mammogramRotation = remember { Animatable(0f) }
        val doctorRotation = remember { Animatable(0f) }
        val dotX = remember { Animatable(0f) }
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
                dotX.animateTo(1f, tween(800))
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

        Canvas(modifier.autoWrapHeight()) {
            val height = size.width * .35f
            val imageSize = with(density) {
                val imageHeight =
                    (size.width * .15f).dp.takeIf { it < defaultImageHeight } ?: defaultImageHeight
                val ratio =
                    oncologist.intrinsicSize.height / oncologist.intrinsicSize.width
                Size(imageHeight.value / ratio, imageHeight.value)
            }
            val pharmacistSize = (imageSize.height).let { imageHeight ->
                val ratio =
                    pharmacist.intrinsicSize.height / pharmacist.intrinsicSize.width
                Size(imageHeight / ratio, imageHeight)
            }

            val textLayoutSize = textMeasurer.measure(
                "1 year period",
                style = ts.copy(
                    fontSize = ts.fontSize * size.width * .0009f,
                    fontWeight = FontWeight.Bold
                )
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

            val circleRadius = textBoxSize.height * .2f

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

            val orangeToBlue = size.width * .07f * dotX.value
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

    private fun Modifier.autoWrapHeight() = this then object : LayoutModifier {
        override fun MeasureScope.measure(
            measurable: Measurable, constraints: Constraints
        ): MeasureResult {
            val placeable = measurable.measure(constraints.copy(minHeight = constraints.minHeight))
            val maxWidth = placeable.width
            var yPosition = 0

            return layout(maxWidth, (maxWidth * .3).toInt()) {
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }

    private enum class CareOption {
        USUAL, SHARED
    }
}