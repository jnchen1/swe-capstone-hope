import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class AnimationScreen(
    val wrapContent: Boolean = false
) : Screen {

    override val key: ScreenKey = uniqueScreenKey
    private val screenTitle = "TEST"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { println("Navigator: Start screen $screenTitle") },
            onDisposed = { println("Navigator: Dispose screen $screenTitle") }
        )

        val navigator = LocalNavigator.currentOrThrow
        var shouldShowLongTermEffect by remember { mutableStateOf(false) }
        var shouldShowLateEffect by remember { mutableStateOf(false) }

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
            },
            bottomBar = {
                BottomAppBar {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
                    ) {
                        Button(
                            onClick = { navigator.popUntilRoot() },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "previous page arrow"
                            )

                            Text(text = "Back", modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        shouldShowLongTermEffect = true
                        shouldShowLateEffect = false
                    }) {
                        Text("Long Effect")
                    }
                    Button(onClick = {
                        shouldShowLongTermEffect = false
                        shouldShowLateEffect = true
                    }) {
                        Text("Late Effect")
                    }
                }

                if (shouldShowLongTermEffect) {
                    EffectAnimation(
                        shouldShowLongTermEffect,
                        false,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    EffectAnimation(
                        false,
                        shouldShowLateEffect,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun EffectAnimation(
        shouldShowLongTermEffect: Boolean = false,
        shouldShowLateEffect: Boolean = false,
        modifier: Modifier = Modifier
    ) {
        val textMeasurer = rememberTextMeasurer()
        val ts = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Center)
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

            val iconSize = (height * .3f).let {
                val iconRatio =
                    iconPainter.intrinsicSize.height / iconPainter.intrinsicSize.width
                Size(it, it * iconRatio)
            }

            if (shouldShowLongTermEffect) {
                textLayoutSize = textMeasurer.measure(
                    "Long term effect",
                    ts.copy(Color.White, fontSize = ts.fontSize * size.width * .00085f)
                )

                drawRect(
                    Color(0xFF426E86),
                    topLeft = Offset(
                        size.width * .15f + iconSize.center.x,
                        height * .625f - iconSize.center.y * .9f
                    ),
                    size = Size(
                        (size.width * .52f) * arrowBody.value,
                        textLayoutSize.size.height.toFloat()
                    )
                )

                val centerY =
                    height * .625f - iconSize.center.y * .9f + textLayoutSize.size.center.y.toFloat()
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
                        size.width * .36f,
                        height * .625f - iconSize.center.y * .9f
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
                    "Late effect",
                    ts.copy(Color.White, fontSize = ts.fontSize * size.width * .00085f)
                )

                drawRect(
                    Color(0xFF5B7065),
                    topLeft = Offset(
                        size.width * .55f + iconSize.center.x,
                        height * .625f - iconSize.center.y * .9f
                    ),
                    size = Size(
                        (size.width * .3f) * arrowBody.value,
                        textLayoutSize.size.height.toFloat()
                    )
                )

                val centerY =
                    height * .625f - iconSize.center.y * .9f + textLayoutSize.size.center.y.toFloat()
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
                        iconSize.center.x + size.width * .655f - textLayoutSize.size.center.y,
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
}

private fun Modifier.autoWrapHeight() = this then object : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
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