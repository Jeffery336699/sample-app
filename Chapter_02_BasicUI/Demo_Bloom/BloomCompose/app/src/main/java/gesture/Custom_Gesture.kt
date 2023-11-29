package gesture

import android.util.Log
import android.view.ViewConfiguration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.AndroidViewConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt


val TAG = "CustomGesture"

@Composable
fun BaseDragGestureDemo() {
    val boxSize = 100.dp
    var offset by remember { mutableStateOf(Offset.Zero) }
    var viewConfig = AndroidViewConfiguration(ViewConfiguration.get(LocalContext.current))
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier
            .size(boxSize)
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .background(Color.Green)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        val downPointer = awaitFirstDown()
                        while (true) {
                            val event = awaitDragOrCancellation(downPointer.id) ?: break
                            offset += event.positionChange()
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun DragGestureDemo() {
    val boxSize = 100.dp
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier
            .size(boxSize)
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .background(Color.Blue)
            .pointerInput(Unit) {
                awaitPointerEventScope {

                }
                detectDragGestures(
                    onDragStart = {},
                    onDragEnd = {},
                    onDragCancel = {},
                    onDrag = { change: PointerInputChange, dragAmount: Offset ->
                        offset += dragAmount
                    }
                )
            }
        )
    }
}

@Composable
fun TapGestureDemo() {
    val boxSize = 100.dp
    val open by remember { mutableStateOf(true) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            if (open) {
                Box(
                    Modifier
                        .size(boxSize)
                        .background(Color.Red)
                        .pointerInput(Unit) {
                            forEachGesture {
                                awaitPointerEventScope {
                                    val event = awaitFirstDown()
                                    drag(event.id) {
                                        Log.d(TAG, "发生了拖动--${it.position}")
                                    }
                                }
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun TransformGestureDemo() {
    val boxSize = 100.dp
    var offset by remember { mutableStateOf(Offset.Zero) }
    var rotationAngle by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(Modifier
            .size(boxSize)
            .rotate(rotationAngle) // 需要注意offset与rotate、scale调用先后顺序
            .scale(scale)
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .background(Color.Green)
            .pointerInput(Unit) {
                detectTransformGestures(
                    panZoomLock = false,
                    onGesture = { centroid: Offset, pan: Offset, zoom: Float, rotation: Float ->
                        offset += pan
                        scale *= zoom
                        rotationAngle += rotation
                    }
                )
            }
        )
    }
}
