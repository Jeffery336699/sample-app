package Custom_Layout


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun SubcomposeRow(
    modifier: Modifier,
    text: @Composable () -> Unit,
    divider: @Composable (Int) -> Unit
){
    SubcomposeLayout(modifier = modifier) { constraints->
        var maxHeight = 0
        val placeables = subcompose("text", text).map {
            Log.i(TAG, "map#text: ")
            val placeable = it.measure(constraints)
            maxHeight = placeable.height.coerceAtLeast(maxHeight)
            placeable
        }
        Log.i(TAG, "maxHeight: $maxHeight")
        val dividerPlaceable = subcompose("divider") {
            divider(maxHeight)
        }.map {
            it.measure(constraints.copy(minWidth = 0))
        }
        assert(dividerPlaceable.size == 1) { "There Should Be Only One LayoutNode" }
        val midPos = constraints.maxWidth / 2
        Log.e(TAG, "constraints.maxWidth: ${constraints.maxWidth}") // 1080
        Log.e(TAG, "constraints.maxHeight: ${constraints.maxHeight}") // 2208
        // TODO: 此时这里是设置容器的宽高了
        layout(constraints.maxWidth, constraints.maxHeight){
            placeables.forEach {
                it.placeRelative(0, 0)
            }
            dividerPlaceable.forEach {
                it.placeRelative(midPos, 0)
            }
        }
    }
}

@Composable
fun SubcompositionDemo() {
    Box(Modifier.fillMaxSize()) {
        SubcomposeRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x6752c589)),
            text = {
                Text(text = "Left", Modifier.wrapContentWidth(Alignment.Start))
                Text(text = "Right", Modifier.wrapContentWidth(Alignment.End))
            }
        ) {
            val heightDp = with( LocalDensity.current) { it.toDp() }
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .width(4.dp)
                    .height(heightDp)
            )
        }
    }
}