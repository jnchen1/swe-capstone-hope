package ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.FirstPage
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LastPage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ThemeTopAppBar(
    title: String,
    color: Color = Color.Unspecified,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) = TopAppBar(
    title = {
        Text(
            text = title,
            color = color,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    },
    navigationIcon = navigationIcon,
    actions = actions
)

@Composable
fun ThemeBottomNavigation(
    showPrevPage: Boolean = false,
    showPrevSection: Boolean = false,
    prevAction: () -> Unit = {},
    showHome: Boolean = false,
    homeAction: () -> Unit = {},
    showNextPage: Boolean = false,
    showNextSection: Boolean = false,
    nextAction: () -> Unit = {},
) = BottomNavigation {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 4.dp)
    ) {
        val textMeasurer = rememberTextMeasurer()
        val textSize = MaterialTheme.typography.button.fontSize
        val density = LocalDensity.current

        if (!showPrevPage && !showPrevSection) {
            Spacer(Modifier.weight(1f))
        } else {
            Button(
                onClick = prevAction,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                modifier = Modifier.weight(1f),
                contentPadding = if (showPrevPage) Navigation.PREV_PAGE.padding else Navigation.PREV_SECTION.padding
            ) {
                Icon(
                    if (showPrevPage) Navigation.PREV_PAGE.icon else Navigation.PREV_SECTION.icon,
                    if (showPrevPage) Navigation.PREV_PAGE.text else Navigation.PREV_SECTION.text
                )

                if (showPrevPage) {
                    Text(text = "Back", modifier = Modifier.padding(start = 4.dp))
                } else {
                    BoxWithConstraints {
                        val boxScope = this
                        val prevTextLayout = textMeasurer.measure(
                            text = "Previous section",
                            style = MaterialTheme.typography.button,
                            overflow = TextOverflow.Clip
                        )
                        val sizeInDp = with(density) { prevTextLayout.size.width.toDp() }

                        Text(
                            text = "Previous section",
                            fontSize = if (boxScope.maxWidth - 4.dp < sizeInDp) textSize * .8 else textSize,
                            overflow = TextOverflow.Clip,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }

        if (!showHome) {
            Spacer(Modifier.weight(1f))
        } else {
            BottomNavigationItem(
                selected = false,
                onClick = homeAction,
                icon = { Icon(Icons.Rounded.Home, "Home", tint = Color.White) },
                label = { Text(text = "Home", color = Color.White) },
                modifier = Modifier.weight(1f)
            )
        }

        if (!showNextPage && !showNextSection) {
            Spacer(Modifier.weight(1f))
        } else {
            Button(
                onClick = nextAction,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                modifier = Modifier.weight(1f),
                contentPadding = if (showNextPage) Navigation.NEXT_PAGE.padding else Navigation.NEXT_SECTION.padding
            ) {
                if (showNextPage) {
                    Text(text = "Next")
                } else {
                    BoxWithConstraints {
                        val boxScope = this

                        val textLayout = textMeasurer.measure(
                            text = "Next section",
                            style = MaterialTheme.typography.button,
                            overflow = TextOverflow.Clip
                        )
                        val sizeInDp = with(density) { textLayout.size.width.toDp() }
                        val textAndSize =
                            if (boxScope.maxWidth < sizeInDp * 1.3f) Pair(
                                "Next\nsection", textSize * .8
                            )
                            else Pair("Next section", textSize)

                        Text(
                            text = textAndSize.first,
                            fontSize = textAndSize.second,
                            textAlign = TextAlign.End,
                            overflow = TextOverflow.Clip,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }

                Icon(
                    if (showNextPage) Navigation.NEXT_PAGE.icon else Navigation.NEXT_SECTION.icon,
                    if (showNextPage) Navigation.NEXT_PAGE.text else Navigation.NEXT_SECTION.text
                )
            }
        }
    }
}

private enum class Navigation(val icon: ImageVector, val text: String, val padding: PaddingValues) {
    PREV_PAGE(Icons.Rounded.ArrowBack, "Back", ButtonDefaults.ContentPadding),
    PREV_SECTION(
        Icons.Rounded.FirstPage,
        "Previous Section",
        PaddingValues(horizontal = 8.dp, vertical = 2.dp)
    ),
    NEXT_PAGE(Icons.Rounded.ArrowForward, "Next", ButtonDefaults.ContentPadding),
    NEXT_SECTION(
        Icons.Rounded.LastPage,
        "Next Section",
        PaddingValues(horizontal = 8.dp, vertical = 2.dp)
    ),
}