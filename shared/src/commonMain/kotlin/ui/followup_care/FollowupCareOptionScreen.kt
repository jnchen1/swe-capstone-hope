package ui.followup_care

import HomeScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar

@OptIn(ExperimentalMaterialApi::class)
data class FollowupCareOptionScreen(
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

        var shouldShowUsual by remember { mutableStateOf(false) }
        var shouldShowShared by remember { mutableStateOf(false) }
        var shouldShowUsualText by remember { mutableStateOf(false) }
        var shouldShowSharedText by remember { mutableStateOf(false) }

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle, option.color) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevPage = true, prevAction = {
                        if (navigator.items.contains(FollowupCareIntroScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(FollowupCareIntroScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true,
                    nextAction = { navigator.push(FollowupCareProfessionalScreen()) }
                )
            }
        ) {
            BoxWithConstraints {
                val boxScope = this
                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "What are your options?",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Text(
                        text = "Tap on each button to read more about each option.",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        backgroundColor = Color(0xFFA49592),
                        modifier = Modifier.padding(horizontal = 8.dp).padding(top = 8.dp),
                        onClick = {
                            shouldShowUsual = true
                            shouldShowShared = false
                            shouldShowUsualText = true
                        }
                    ) {
                        Text(
                            "Usual care",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp, 8.dp)
                        )
                    }

                    val usualTextAlpha by animateFloatAsState(
                        targetValue = if (shouldShowUsualText) 1f else 0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing,
                        )
                    )
                    Text(
                        buildAnnotatedString {
                            append("Typically, each health care professional care for ≥1 aspect(s) of your follow-up care ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("independently")
                            }
                            append(".")
                        },
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 16.dp).alpha(usualTextAlpha)
                    )

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        backgroundColor = Color(0xFFE99787),
                        modifier = Modifier.padding(horizontal = 8.dp).padding(top = 16.dp),
                        onClick = {
                            shouldShowUsual = false
                            shouldShowShared = true
                            shouldShowSharedText = true
                        }
                    ) {
                        Text(
                            "Shared care",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp, 8.dp)
                        )
                    }
                    val sharedTextAlpha by animateFloatAsState(
                        targetValue = if (shouldShowSharedText) 1f else 0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )
                    Text(
                        buildAnnotatedString {
                            append("A care team of oncologist, polyclinic family physician, and community pharmacist, care for ≥1 aspect(s) of your follow-up care ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("together")
                            }
                            append(".")
                        },
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 16.dp).alpha(sharedTextAlpha)
                    )

                    val animationModifier = Modifier.padding(16.dp).padding(top = 20.dp)
                        .align(androidx.compose.ui.Alignment.CenterHorizontally).then(
                            if (boxScope.maxWidth > boxScope.maxHeight) Modifier.fillMaxWidth(.7f)
                            else Modifier.fillMaxWidth()
                        )

                    if (shouldShowUsual) {
                        Animation(shouldShowUsual, false, animationModifier)
                    } else if (shouldShowShared) {
                        Animation(false, shouldShowShared, animationModifier)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun Animation(shouldShowUsual: Boolean, shouldShowShared: Boolean, modifier: Modifier) {
        val textMeasurer = rememberTextMeasurer()
        val ts = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Center, fontSize = 14.sp)
        val oncologistImage = painterResource("followup_care/care_oncologist.png")
        val doctorImage = painterResource("followup_care/care_doctor.png")
        val pharmacistImage = painterResource("followup_care/care_pharmacist.png")
        var shouldShowOncologist by remember { mutableStateOf(false) }
        var shouldShowDoctor by remember { mutableStateOf(false) }
        var shouldShowPharmacist by remember { mutableStateOf(false) }

        val puzzleAnimation = remember { Animatable(0f) }
        val oncologistPuzzleAlpha = remember { Animatable(0f) }
        val doctorPuzzleAlpha = remember { Animatable(0f) }
        val pharmacistPuzzleAlpha = remember { Animatable(0f) }

        if (shouldShowUsual && !shouldShowShared) {
            LaunchedEffect(key1 = shouldShowUsual) {
                (1..3).forEach { index ->
                    when (index) {
                        1 -> shouldShowOncologist = true
                        2 -> shouldShowDoctor = true
                        3 -> shouldShowPharmacist = true
                    }
                    delay(500)
                }
            }
        } else {
            shouldShowOncologist = true
            shouldShowDoctor = true
            shouldShowPharmacist = true

            LaunchedEffect(shouldShowShared) {
                oncologistPuzzleAlpha.animateTo(1f, animationSpec = tween(800))
                doctorPuzzleAlpha.animateTo(1f, animationSpec = tween(800))
                pharmacistPuzzleAlpha.animateTo(1f, animationSpec = tween(800))

                puzzleAnimation.animateTo(1f, animationSpec = tween(1500))
            }
        }

        Canvas(modifier = modifier.autoWrapHeight()) {
            val puzzleSize = size.width * .3f

            val imageSize = (puzzleSize * .65f).let { height ->
                val ratio =
                    oncologistImage.intrinsicSize.height / oncologistImage.intrinsicSize.width
                Size(height / ratio, height)
            }

            if (shouldShowOncologist) {
                val leftOffset = puzzleSize * .25f * puzzleAnimation.value
                val textLayoutSize = textMeasurer.measure(
                    "Oncologist",
                    softWrap = true,
                    style = ts.copy(fontSize = ts.fontSize * puzzleSize * .0035),
                    constraints = Constraints.fixedWidth(puzzleSize.toInt())
                )

                val path = Path().apply {
                    val left = 0f + leftOffset
                    val right = puzzleSize + leftOffset
                    moveTo(left, 0f)
                    lineTo(right, 0f)
                    lineTo(right, puzzleSize * .25f)

                    arcTo(
                        rect = Rect(
                            left = puzzleSize * .75f,
                            top = puzzleSize * .25f,
                            right = right + puzzleSize * .25f,
                            bottom = puzzleSize * .75f
                        ),
                        startAngleDegrees = -90f,
                        sweepAngleDegrees = 180f,
                        forceMoveTo = false
                    )

                    lineTo(right, puzzleSize * .75f)
                    lineTo(right, puzzleSize)
                    lineTo(left, puzzleSize)
                    close()
                }
                drawPath(path, Color(0xFFE6C546), oncologistPuzzleAlpha.value)

                translate(puzzleSize * .5f - imageSize.center.x + leftOffset) {
                    with(oncologistImage) {
                        draw(imageSize)
                    }
                }

                drawText(
                    textLayoutSize,
                    topLeft = Offset(
                        puzzleSize * .5f - textLayoutSize.size.center.x + leftOffset,
                        imageSize.height
                    )
                )
            }

            if (shouldShowDoctor) {
                val path = Path().apply {
                    val left = size.center.x - puzzleSize * .5f
                    val right = size.center.x + puzzleSize * .5f
                    moveTo(left, 0f)
                    lineTo(right, 0f)
                    lineTo(right, puzzleSize * .25f)

                    arcTo(
                        rect = Rect(
                            left = right - puzzleSize * .25f,
                            top = puzzleSize * .25f,
                            right = right + puzzleSize * .25f,
                            bottom = puzzleSize * .75f
                        ),
                        startAngleDegrees = -90f,
                        sweepAngleDegrees = 180.0f,
                        forceMoveTo = false
                    )

                    lineTo(right, puzzleSize)
                    lineTo(left, puzzleSize)
                    lineTo(left, puzzleSize * .75f)

                    arcTo(
                        rect = Rect(
                            left = left - puzzleSize * .25f,
                            top = puzzleSize * .25f,
                            right = left + puzzleSize * .25f,
                            bottom = puzzleSize * .75f
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = true
                    )

                    lineTo(left, puzzleSize * .25f)
                    close()
                }
                drawPath(path, Color(0xFFCB6D53), doctorPuzzleAlpha.value)

                translate(size.center.x - (puzzleSize * .45f - imageSize.center.x)) {
                    with(doctorImage) {
                        draw(imageSize)
                    }
                }
                val textLayoutSize = textMeasurer.measure(
                    "Primary Care Doctor",
                    softWrap = true,
                    style = ts.copy(fontSize = ts.fontSize * puzzleSize * .0035),
                    constraints = Constraints.fixedWidth(puzzleSize.toInt())
                )
                drawText(
                    textLayoutSize,
                    topLeft = Offset(
                        size.center.x - textLayoutSize.size.center.x,
                        imageSize.height
                    )
                )
            }

            if (shouldShowPharmacist) {
                val rightOffSet = puzzleSize * .25f * puzzleAnimation.value
                val path = Path().apply {
                    val left = size.width - puzzleSize - rightOffSet
                    val right = size.width - rightOffSet
                    moveTo(left, 0f)
                    lineTo(right, 0f)
                    lineTo(right, puzzleSize)
                    lineTo(left, puzzleSize)
                    lineTo(left, puzzleSize * .75f)

                    arcTo(
                        rect = Rect(
                            left = left - puzzleSize * .25f,
                            top = puzzleSize * .25f,
                            right = left + puzzleSize * .25f,
                            bottom = puzzleSize * .75f
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = false
                    )

                    lineTo(left, puzzleSize * .25f)
                    close()
                }
                drawPath(path, Color(0xFF7EB4B9), pharmacistPuzzleAlpha.value)

                translate(size.width - puzzleSize * .5f - imageSize.width * .5f - rightOffSet) {
                    with(pharmacistImage) {
                        draw(
                            Size(
                                imageSize.width * 1.2f,
                                imageSize.height * 1.1f
                            )
                        )
                    }
                }
                val textLayoutSize = textMeasurer.measure(
                    "Pharmacist",
                    softWrap = true,
                    style = ts.copy(fontSize = ts.fontSize * puzzleSize * .0035),
                    constraints = Constraints.fixedWidth(puzzleSize.toInt())
                )
                drawText(
                    textLayoutSize,
                    topLeft = Offset(
                        size.width - textLayoutSize.size.width - rightOffSet,
                        imageSize.height
                    )
                )
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
}