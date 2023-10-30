package ui.followup_care

import HomeScreen
import androidx.compose.animation.core.Animatable
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
import kotlinx.coroutines.launch
import model.HealthcareProfessional.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
data class FollowupCareScheduleScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "FOLLOW-UP CARE OPTION"

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
                            onClick = {
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

                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceAround) {
                        Button(onClick = {
//                            shouldShowOncologist = true
                        }) {
                            Text("Oncologist")
                        }
                        Button(onClick = {
//                            shouldShowDoctor = true
                        }) {
                            Text("Doctor")
                        }
                        Button(onClick = {
//                            shouldWiggleDoctor = true
                        }) {
                            Text("Wiggle")
                        }
                    }

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

                    Surface(
                        Modifier.padding(bottom = 8.dp).fillMaxWidth()
                            .wrapContentHeight(),
                        RoundedCornerShape(12.dp), Color.White, elevation = 4.dp
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            UsualCareLayout(boxScope, Modifier.align(Alignment.CenterHorizontally))
                        }
                    }

                }
            }
        }
    }

    @Composable
    private fun UsualCareLayout(boxScope: BoxWithConstraintsScope, modifier: Modifier) {
        val boxTextStyle = with(LocalDensity.current) {
            (MaterialTheme.typography.caption.fontSize.value * boxScope.maxWidth.value * .005f).toSp()
        }.let {
            MaterialTheme.typography.caption.copy(
                fontSize = it,
                textAlign = TextAlign.Center
            )
        }
        val doctorTextBoxOffsetY = boxScope.maxWidth * .5f

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

        Box(
            Modifier.padding(4.dp).fillMaxWidth()
                .wrapContentHeight()
        ) {
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
                    Modifier.align(Alignment.BottomCenter)
                        .fillMaxWidth(.45f)
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
                    Modifier.padding(start = doctorTextBoxOffsetY)
                        .fillMaxWidth(.6f)
                        .align(Alignment.BottomStart)

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
                    Modifier.padding(start = doctorTextBoxOffsetY)
                        .fillMaxWidth(.6f)
                        .align(Alignment.BottomStart)

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
                                .firstOrNull()?.let {
                                    /*TODO*/
                                }
                        })
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
            launch {
                delay(100)
                shouldShowText = true
                arrowBody.animateTo(1f, animationSpec = tween(1500))
                arrow.animateTo(1f, animationSpec = tween(300))
                mammogramAlpha.animateTo(1f, animationSpec = tween(1000, easing = LinearEasing))
            }
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
                (1..3).forEach {
                    when (it) {
                        1 -> orange1Rotation.animateTo(-30f, tween(100))
                        2 -> orange1Rotation.animateTo(30f, tween(200))
                        else -> orange1Rotation.animateTo(0f, tween(100))
                    }
                }
            }
        }

        Canvas(modifier = modifier.autoWrapHeight()) {
            val height = size.width * .35f
            val imageSize = (size.width * .15f).let { imageHeight ->
                val ratio =
                    oncologist.intrinsicSize.height / oncologist.intrinsicSize.width
                Size(imageHeight / ratio, imageHeight)
            }

            val textLayoutSize = textMeasurer.measure(
                "1 year period",
                style = ts.copy(
                    fontSize = ts.fontSize * size.width * .0009f,
                    fontWeight = FontWeight.Bold
                ),
                constraints = Constraints.fixedWidth((size.width * .2).toInt())
            )

            val textBoxSize =
                Size(textLayoutSize.size.width * 1.1f, textLayoutSize.size.height * 1.2f)
            val textBoxOffset = Offset(0f, height * .5f - textBoxSize.center.y)

            val horizontalLineY = height * .5f
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

            val circleRadius = size.width * 0.025f

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
                Offset(textBoxSize.width + size.width * .05f, horizontalLineY)
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
                Offset(textBoxSize.width + size.width * .15f, horizontalLineY)
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

            val blue2Offset = Offset(size.center.x + size.width * .25f, horizontalLineY)
            drawCircle(blueGreyColor, circleRadius, blue2Offset, blue2Alpha.value)
            translate(blue2Offset.x - imageSize.center.x) {
                with(oncologist) { draw(imageSize, blue2Alpha.value) }
            }

            val orange3Offset = Offset(size.width * .9f, horizontalLineY)
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

    private fun Modifier.autoWrapHeight() = this then object : LayoutModifier {
        override fun MeasureScope.measure(
            measurable: Measurable, constraints: Constraints
        ): MeasureResult {
            val placeable = measurable.measure(constraints.copy(minHeight = constraints.minHeight))
            val maxWidth = placeable.width
            var yPosition = 0

            return layout(maxWidth, (maxWidth * .4).toInt()) {
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}