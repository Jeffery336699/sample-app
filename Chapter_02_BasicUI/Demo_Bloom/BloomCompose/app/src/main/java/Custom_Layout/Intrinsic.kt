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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

const val TAG = "Intrinsic"

@Composable
fun IntrinsicRow(modifier: Modifier, content: @Composable () -> Unit){
    Layout(
        content = content,
        modifier = modifier,
        measurePolicy = object: MeasurePolicy {
            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {
                Log.e(TAG, "MeasureScope.measure") // TODO: ②
                val devideConstraints = constraints.copy(minWidth = 0)
                val mainPlaceables = measurables.filter {
                    it.layoutId == "main"
                }.map {
                    it.measure(constraints)
                }
                val devidePlaceable = measurables.first { it.layoutId == "devider"}.measure(devideConstraints)
                val midPos = constraints.maxWidth / 2
                Log.i(TAG, "constraints.maxWidth: ${constraints.maxWidth}") // 1080
                Log.i(TAG, "constraints.maxHeight: ${constraints.maxHeight}") // 52
                return layout(constraints.maxWidth, constraints.maxHeight) {
                    mainPlaceables.forEach {
                        it.placeRelative(0, 0)
                    }
                    devidePlaceable.placeRelative(midPos, 0)
                }
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int {
                Log.e(TAG, "IntrinsicMeasureScope.minIntrinsicHeight") // TODO: ①
                var maxHeight = 0
                measurables.forEach {
                    maxHeight = it.minIntrinsicHeight(width).coerceAtLeast(maxHeight)
                }
                return maxHeight
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun IntrinsicDemo() {
    Box(Modifier.fillMaxSize()) {
        IntrinsicRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color(0x66c16712))
        ) {
            Text(text = "Left",
                Modifier
                    .wrapContentWidth(Alignment.Start)
                    .layoutId("main"))
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .layoutId("devider"))
            Text(text = "Right",
                Modifier
                    .wrapContentWidth(Alignment.End)
                    .layoutId("main"))
        }
    }
}