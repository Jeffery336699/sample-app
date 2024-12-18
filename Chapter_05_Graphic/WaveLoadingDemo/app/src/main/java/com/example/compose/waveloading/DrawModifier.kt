package com.example.compose.waveloading

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.waveloading.R

@Composable
fun DrawBehind() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(8.dp)
            ,modifier = Modifier
                .size(100.dp)
                // Optimize: drawBehind{}闭包里面的是在最下层——先绘制装饰物(eg小圆点),后绘制内容(内容指eg Image)
                .drawBehind {
                    drawCircle(
                        Color(0xffe7614e),
                        18.dp.toPx() / 2,
                        center = Offset(drawContext.size.width, 0f)
                    )
                }
        ) {
            Image(painter = painterResource(id = R.drawable.logo_nba), contentDescription = "Diana")
        }
    }
}

@Preview
@Composable
fun DrawFuwa() {
    Layout(
        modifier = Modifier.fillMaxSize(),
        content = { Text(text = "Hello World") }) { measurables, constraints ->
        // 1.执行measure操作
        val placeable = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val width = placeable.sumOf { it.measuredWidth }
        // 2.执行layout操作
        // Optimize: 需要给上面返回一个最终的MeasureResult,layout方法返回的结果就是一个MeasureResult
        //  有点类似传统View体系中的onMeasure方法最后需要给上层返回自己最终的测量结果setMeasuredDimension(width, height)一样
        layout(constraints.maxWidth, constraints.maxHeight) {
            measurables.forEach {
                it.measure(constraints)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val transition = rememberInfiniteTransition()
        // animationSpec在动画中是一个非常重要的参数，他可以指定不同的动画类型，比如补间动画，循环动画，延迟动画等
        val alpha by transition.animateFloat(initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing), repeatMode = RepeatMode.Reverse)
        )
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .size(340.dp, 300.dp)
                .onGloballyPositioned { }
                .drawWithCache {
                    val beibeiImage =
                        ImageBitmap.imageResource(context.resources, R.drawable.beibei)
                    val jingjingImage =
                        ImageBitmap.imageResource(context.resources, R.drawable.jingjing)
                    val huanhuanImage =
                        ImageBitmap.imageResource(context.resources, R.drawable.huanhuan)
                    val yingyingImage =
                        ImageBitmap.imageResource(context.resources, R.drawable.yingying)
                    val niniImage = ImageBitmap.imageResource(context.resources, R.drawable.nini)
                    Log.i("Jeffery", "drawWithCache: ") // TODO: 仅仅执行一次
                    onDrawBehind {
                        Log.i("Jeffery", "onDrawBehind: ") // TODO: 随着State变化,执行多次
                        drawImage(
                            image = beibeiImage,
                            dstSize = IntSize(100.dp.roundToPx(), 100.dp.roundToPx()),
                            dstOffset = IntOffset.Zero,
                            alpha = alpha
                        )
                        drawImage(
                            image = jingjingImage,
                            dstSize = IntSize(100.dp.roundToPx(), 100.dp.roundToPx()),
                            dstOffset = IntOffset(120.dp.roundToPx(), 0),
                            alpha = alpha
                        )
                        drawImage(
                            image = huanhuanImage,
                            dstSize = IntSize(100.dp.roundToPx(), 100.dp.roundToPx()),
                            dstOffset = IntOffset(240.dp.roundToPx(), 0),
                            alpha = alpha
                        )
                        drawImage(
                            image = yingyingImage,
                            dstSize = IntSize(100.dp.roundToPx(), 100.dp.roundToPx()),
                            dstOffset = IntOffset(60.dp.roundToPx(), 120.dp.roundToPx()),
                            alpha = alpha
                        )
                        drawImage(
                            image = niniImage,
                            dstSize = IntSize(100.dp.roundToPx(), 100.dp.roundToPx()),
                            dstOffset = IntOffset(180.dp.roundToPx(), 120.dp.roundToPx()),
                            alpha = alpha
                        )
                    }
                }
        )
    }
}