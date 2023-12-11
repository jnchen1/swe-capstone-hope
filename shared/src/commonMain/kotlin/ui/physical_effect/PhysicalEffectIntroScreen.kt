package ui.physical_effect

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
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
import androidx.compose.ui.unit.toOffset
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import model.HomeOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ThemeBottomNavigation
import ui.ThemeTopAppBar
import ui.survivorship.WhatIsSurvivorshipThirdScreen

data class PhysicalEffectIntroScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    val option = HomeOptions.PHYSICAL_EFFECT
    private val screenTitle = option.title

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow
        var shouldShowLongTermAnimation by remember { mutableStateOf(false) }
        var shouldShowLateAnimation by remember { mutableStateOf(false) }

        Scaffold(
            topBar = { ThemeTopAppBar(screenTitle, option.color) },
            bottomBar = {
                ThemeBottomNavigation(
                    showPrevSection = true, prevAction = {
                        if (navigator.items.contains(WhatIsSurvivorshipThirdScreen())) {
                            navigator.pop()
                        } else {
                            navigator.replace(WhatIsSurvivorshipThirdScreen())
                        }
                    },
                    showHome = true, homeAction = { navigator.popUntil { it == HomeScreen() } },
                    showNextPage = true, nextAction = { navigator.push(PhysicalEffectExamplesScreen()) }
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
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(8.dp).padding(top = 8.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Cancer treatment(s)")
                            }
                            append(" can cause ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("mild to severe")
                            }
                            append(" physical side effects. There are ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("2 types")
                            }
                            append(" of side effects that may affect you now, click on each button below to see the animation.")
                        }
                    )

                    var shouldShowLongTermText by remember { mutableStateOf(false) }
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        backgroundColor = Color(0xFF426E86),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            shouldShowLongTermText = true
                            shouldShowLongTermAnimation = true
                            shouldShowLateAnimation = false
                        }
                    ) {
                        Text(
                            "Long-term effects",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp, 8.dp)
                        )
                    }
                    val longTermTextAlpha by animateFloatAsState(
                        targetValue = if (shouldShowLongTermText) 1f else 0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )
                    Text(
                        buildAnnotatedString {
                            append("These effects appear ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("during")
                            }
                            append(" treatment and ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("continue")
                            }
                            append(" even after finishing treatment(s).")
                        },
                        Modifier.fillMaxWidth().padding(start = 12.dp, end = 8.dp, top = 4.dp)
                            .alpha(longTermTextAlpha),
                        style = MaterialTheme.typography.body1
                    )

                    var shouldShowLateText by remember { mutableStateOf(false) }
                    val lateTextAlpha by animateFloatAsState(
                        targetValue = if (shouldShowLateText) 1f else 0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        backgroundColor = Color(0xFF5B7065),
                        modifier = Modifier.padding(horizontal = 8.dp).padding(top = 16.dp),
                        onClick = {
                            shouldShowLateText = true
                            shouldShowLongTermAnimation = false
                            shouldShowLateAnimation = true
                        }
                    ) {
                        Text(
                            "Late effects",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp, 8.dp)
                        )
                    }

                    Text(
                        buildAnnotatedString {
                            append("These effects appear ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("after")
                            }
                            append(" treatment is completed.")
                        },
                        Modifier.fillMaxWidth().padding(start = 12.dp, end = 8.dp, top = 4.dp)
                            .alpha(lateTextAlpha),
                        style = MaterialTheme.typography.body1
                    )

                    val animationModifier = Modifier
                        .padding(16.dp).padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally).then(
                            if (boxScope.maxWidth > boxScope.maxHeight) Modifier.fillMaxWidth(.7f)
                            else Modifier.fillMaxWidth()
                        )

                    if (shouldShowLongTermAnimation) {
                        EffectAnimation(shouldShowLongTermAnimation, false, animationModifier)
                    } else {
                        EffectAnimation(false, shouldShowLateAnimation, animationModifier)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun EffectAnimation(
        shouldShowLongTermEffect: Boolean = false,
        shouldShowLateEffect: Boolean = false,
        modifier: Modifier = Modifier
    ) {
        val textMeasurer = rememberTextMeasurer()
        val ts = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Center, fontSize = 14.sp)
        val lineColor = Color(0xFF7F7F7F)
        val iconPainter = painterResource("effect.png")

        val rotationAngle = remember { Animatable(0f) }
        val arrowBody = remember { Animatable(0f) }
        val arrow = remember { Animatable(0f) }

        if (shouldShowLongTermEffect || shouldShowLateEffect) {
            LaunchedEffect(rotationAngle) {
                launch {
                    rotationAngle.animateTo(360f, animationSpec = tween(500))
                    arrowBody.animateTo(1f, animationSpec = tween(1500))
                    arrow.animateTo(1f, animationSpec = tween(500))
                }
            }
        }

        Canvas(modifier = modifier.autoWrapHeight()) {
            val height = size.width * .35f

            var textLayoutSize = textMeasurer.measure(
                "Start of treatment",
                softWrap = true,
                style = ts.copy(fontSize = ts.fontSize * size.width * .0009f),
                constraints = Constraints.fixedWidth((size.width * .2).toInt())
            )
            val ovalSize =
                Size(textLayoutSize.size.width * 1.2f, textLayoutSize.size.height * 1.4f)

            drawOval(Color(0xFFC1E1DC), size = ovalSize)
            drawText(
                textLayoutSize,
                topLeft = ovalSize.center - textLayoutSize.size.center.toOffset()
            )

            val endOffSet = Offset(size.width * .4f, 0f)
            drawOval(Color(0xFFFDD475), size = ovalSize, topLeft = endOffSet)
            textLayoutSize = textMeasurer.measure(
                "End of treatment",
                softWrap = true,
                style = ts.copy(fontSize = ts.fontSize * size.width * .0009f),
                constraints = Constraints.fixedWidth((size.width * .2).toInt())
            )
            drawText(
                textLayoutSize,
                topLeft = endOffSet + ovalSize.center - textLayoutSize.size.center.toOffset()
            )

            val horizontalLineY = height * .75f
            drawLine(
                lineColor,
                start = Offset(ovalSize.center.x, ovalSize.height),
                end = Offset(ovalSize.center.x, horizontalLineY),
                strokeWidth = 4f
            )
            drawLine(
                lineColor,
                start = Offset(endOffSet.x + ovalSize.center.x, ovalSize.height),
                end = Offset(endOffSet.x + ovalSize.center.x, horizontalLineY),
                strokeWidth = 4f
            )
            drawLine(
                lineColor,
                start = Offset(ovalSize.center.x, horizontalLineY),
                end = Offset(size.width, horizontalLineY),
                strokeWidth = 4f
            )
            val path = Path().apply {
                moveTo(size.width, horizontalLineY)
                lineTo(size.width * .96f, horizontalLineY - height * .055f)
                lineTo(size.width * .96f, horizontalLineY + height * .055f)
                close()
            }
            drawPath(path, lineColor)
            drawCircle(
                lineColor, height * .055f,
                center = Offset(ovalSize.center.x, horizontalLineY)
            )
            drawCircle(
                lineColor, height * .055f,
                center = Offset(endOffSet.x + ovalSize.center.x, horizontalLineY)
            )
            textLayoutSize = textMeasurer.measure(
                "6 - 12 months",
                softWrap = true,
                style = ts.copy(fontSize = ts.fontSize * size.width * .0009f),
            )
            drawText(
                textLayoutSize,
                topLeft = Offset(
                    endOffSet.x + ovalSize.center.x - textLayoutSize.size.width * .5f,
                    horizontalLineY + height * .05f
                )
            )

            val iconSize = (height * .3f).let {
                val iconRatio =
                    iconPainter.intrinsicSize.height / iconPainter.intrinsicSize.width
                Size(it, it * iconRatio)
            }
            val textSize = ts.fontSize * iconSize.height * 0.0125f

            if (shouldShowLongTermEffect) {
                textLayoutSize = textMeasurer.measure(
                    "Long term effect",
                    ts.copy(Color.White, fontSize = textSize)
                )

                drawRect(
                    Color(0xFF426E86),
                    topLeft = Offset(
                        size.width * .15f + iconSize.center.x,
                        height * .625f - iconSize.height * .45f
                    ),
                    size = Size(
                        (size.width * .52f) * arrowBody.value,
                        textLayoutSize.size.height.toFloat()
                    )
                )

                val centerY =
                    height * .625f - iconSize.height * .45f + textLayoutSize.size.center.y.toFloat()
                path.apply {
                    val startX = size.width * .669f + iconSize.width * .5f
                    reset()
                    moveTo(startX, centerY + textLayoutSize.size.height * .85f)
                    lineTo(startX + (size.width * .055f) * arrow.value, centerY)
                    lineTo(startX, centerY - textLayoutSize.size.height * .85f)
                    close()
                }
                drawPath(path, Color(0xFF426E86))

                drawText(
                    textLayoutSize,
                    topLeft = Offset(
                        size.width * .15f + iconSize.width * 1.1f,
                        height * .625f - iconSize.height * .45f
                    )
                )

                translate(
                    left = size.width * .15f,
                    top = height * .625f - iconSize.height * .65f
                ) {
                    with(iconPainter) {
                        rotate(rotationAngle.value, pivot = iconSize.center) {
                            draw(iconSize)
                        }
                    }
                }
            }

            if (shouldShowLateEffect) {
                textLayoutSize = textMeasurer.measure(
                    "Late effect", ts.copy(Color.White, fontSize = textSize)
                )

                drawRect(
                    Color(0xFF5B7065),
                    topLeft = Offset(
                        size.width * .55f + iconSize.center.x,
                        height * .625f - iconSize.height * .45f
                    ),
                    size = Size(
                        (size.width * .3f) * arrowBody.value,
                        textLayoutSize.size.height.toFloat()
                    )
                )

                val centerY =
                    height * .625f - iconSize.height * .45f + textLayoutSize.size.center.y.toFloat()
                path.apply {
                    val startX = size.width * .84f + iconSize.center.x
                    reset()
                    moveTo(startX, centerY + textLayoutSize.size.height * .85f)
                    lineTo(startX + size.width * .055f * arrow.value, centerY)
                    lineTo(startX, centerY - textLayoutSize.size.height * .85f)
                    close()
                }
                drawPath(path, Color(0xFF5B7065))

                drawText(
                    textLayoutSize,
                    topLeft = Offset(
                        size.width * .55f + iconSize.width * 1.1f,
                        height * .625f - iconSize.center.y * .9f
                    )
                )

                translate(
                    left = size.width * .55f,
                    top = height * .625f - iconSize.height * .65f
                ) {
                    with(iconPainter) {
                        rotate(rotationAngle.value, pivot = iconSize.center) {
                            draw(iconSize)
                        }
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

            return layout(maxWidth, (maxWidth * .35).toInt()) {
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}