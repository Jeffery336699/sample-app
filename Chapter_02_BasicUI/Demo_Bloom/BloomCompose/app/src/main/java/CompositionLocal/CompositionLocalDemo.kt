package CompositionLocal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Optimize: 使用出的组件重组了，随着引起往上的parent组件也跟着重组了
// val currentCountLocal = compositionLocalOf { 0 }

// Optimize: content包裹的内容，全部重组了
val currentCountLocal = staticCompositionLocalOf { 0 }

@Composable
fun CompositionLocalScreen() {
    var currentCountState by remember {
        mutableStateOf(1)
    }
    CompositionLocalProvider(
        currentCountLocal provides currentCountState
    ) {
        Column {
            WrapBox(
                Modifier
                    .size(300.dp)
                    .background(Color.Magenta)
            ) {
                WrapBox(
                    Modifier
                        .size(200.dp)
                        .background(Color.Blue)
                        .clickable {
                            currentCountState++
                        }
                ) {
                    WrapBox(
                        Modifier
                            .size(100.dp)
                            .background(Color.Green)
                    ) {
                    }.also { println("100 Box, ") }
                }.also { println("200 Box, $currentCountState") }
            }.also { println("300 Box, ") }
        }
    }
}

@Composable
fun WrapBox(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier, content = content)
}