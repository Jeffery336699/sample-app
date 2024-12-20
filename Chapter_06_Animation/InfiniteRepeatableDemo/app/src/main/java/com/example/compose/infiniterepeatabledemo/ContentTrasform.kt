package com.example.compose.infiniterepeatabledemo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun ExpandedText() {
    val text: String = """
                春江花月夜
        春江潮水连海平，海上明月共潮生。
        滟滟随波千万里，何处春江无月明！
        江流宛转绕芳甸，月照花林皆似霰。
        空里流霜不觉飞，汀上白沙看不见。
        江天一色无纤尘，皎皎空中孤月轮。
        江畔何人初见月？江月何年初照人？
        人生代代无穷已，江月年年望相似。
        不知江月待何人，但见长江送流水。
        白云一片去悠悠，青枫浦上不胜愁。
        谁家今夜扁舟子？何处相思明月楼？
        可怜楼上月裴回，应照离人妆镜台。
        玉户帘中卷不去，捣衣砧上拂还来。
        此时相望不相闻，愿逐月华流照君。
        鸿雁长飞光不度，鱼龙潜跃水成文。
        昨夜闲潭梦落花，可怜春半不还家。
        江水流春去欲尽，江潭落月复西斜。
        斜月沉沉藏海雾，碣石潇湘无限路。
        不知乘月几人归，落月摇情满江树。
    """.trimIndent()
    Text(text = text)
}

@Composable
fun ContentIcon() {
    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = null,
        modifier = Modifier.size(120.dp),
        tint = Color.Black
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ContentTransformScreen() {
    var expanded by remember {
        mutableStateOf(false)
    }
    Surface(
        onClick = { expanded = !expanded },
        color = MaterialTheme.colors.primary,
        modifier = Modifier.wrapContentSize()
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                // Optimize: ContentTransform: 本质上就是currentContent的ExitTransition和targetContent的EnterTransition的组合。
                //  在使用ContentTransform时，还可以使用using操作符连接SizeTransform，并可以预先获取到currentContent和targetContent的大小。并允许我们来定制尺寸变化的过渡动画效果。
                fadeIn(
                    animationSpec = tween(
                        150,
                        150
                    )
                ) with fadeOut(animationSpec = tween(150)) using SizeTransform { initialSize, targetSize ->
                    if (targetState) {
                        // 展开时，先水平展开
                        keyframes {
                            // 值 at 时间戳
                            IntSize(targetSize.width, initialSize.height) at 150
                            durationMillis = 300
                        }
                    } else {
                        // 收起时，先垂直收起
                        keyframes {
                            IntSize(initialSize.width, targetSize.height) at 150
                            durationMillis = 300
                        }
                    }
                }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                ExpandedText()
            } else {
                ContentIcon()
            }
        }
    }
}