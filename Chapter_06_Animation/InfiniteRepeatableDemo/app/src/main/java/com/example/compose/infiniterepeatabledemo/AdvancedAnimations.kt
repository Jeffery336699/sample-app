package com.example.compose.infiniterepeatabledemo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AdvancedAnimationsScreen() {
    // 1. AnimatedVisibility普通用法
    // Box(
    //     contentAlignment = Alignment.Center
    // ) {
    //     var remember by remember {
    //         mutableStateOf(true)
    //     }
    //     AnimatedVisibility(
    //         visible = remember,
    //         modifier = Modifier.onDispose {
    //             // 组件移除时执行的操作
    //             println("Button removed")
    //         }
    //     ) {
    //         Button(
    //             modifier = Modifier.size(200.dp),
    //             onClick = { remember = !remember },
    //             colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
    //         ) {
    //             Text("Toggle Visibility")
    //         }
    //     }
    // }

    // 2. Optimize: AnimatedVisibility传入MutableTransitionState监听动画状态
    Box(
        contentAlignment = Alignment.Center
    ) {
        val state = remember {
            MutableTransitionState(false).apply {
                // 启动动画立马执行
                targetState = true
            }
        }
        /**
         * 3. 获取动画状态，从出现到屏幕到被移除
         *  I  Appearing
         *  I  Visible
         *  I  Disappearing
         *  I  Invisible
         *  I  Button removed
         */
        println("${state.getAnimationState()}")
        AnimatedVisibility(
            state,
            modifier = Modifier.onDispose {
                // 组件移除时执行的操作
                println("Button removed")
            }
        ) {
            // 4. 动态改变背景颜色，由transition.animateColor()实现，衍生出其他动画效果
            val background by transition.animateColor(label = "") { state->
                if (state == EnterExitState.Visible) Color.Blue else Color.Gray
            }
            /**
             * // Optimize: background颜色动态改变
             * System.out(灰)               I  background=Color(0.53333336, 0.53333336, 0.53333336, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.33333334, 0.4392157, 0.7294118, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.22352941, 0.36862746, 0.827451, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.043137256, 0.18039216, 0.9607843, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.015686275, 0.13333334, 0.9764706, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.0, 0.0, 1.0, 1.0, sRGB IEC61966-2.1)
             * System.out               I  Visible
             * System.out(蓝)               I  background=Color(0.0, 0.0, 1.0, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.0, 0.0, 1.0, 1.0, sRGB IEC61966-2.1)
             * System.out               I  Disappearing
             * System.out               I  background=Color(0.0, 0.0, 1.0, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.0, 0.0, 1.0, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.0627451, 0.20784314, 0.9490196, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.19607843, 0.34509805, 0.84705883, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.30980393, 0.42352942, 0.7529412, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.4117647, 0.47843137, 0.65882355, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.45882353, 0.5019608, 0.6117647, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.4862745, 0.5137255, 0.58431375, 1.0, sRGB IEC61966-2.1)
             * System.out               I  background=Color(0.5058824, 0.52156866, 0.5647059, 1.0, sRGB IEC61966-2.1)
             * System.out(灰)               I  background=Color(0.53333336, 0.53333336, 0.53333336, 1.0, sRGB IEC61966-2.1)
             * System.out               I  Invisible
             */
            println("background=$background")
            Button(
                modifier = Modifier.size(200.dp),
                onClick = { state.targetState = !state.targetState },
                colors = ButtonDefaults.buttonColors(backgroundColor = background)
            ) {
                Text("${state.getAnimationState()}")
            }
        }
    }
}

enum class AnimationState {
    Visible, Invisible, Appearing, Disappearing
}

/**
 * 获取动画状态
 */
fun MutableTransitionState<Boolean>.getAnimationState(): AnimationState {
    // 注意取得值是currentState，而不是targetState，它将走向的状态是与现在的状态相反的!
    return when {
        this.isIdle && this.currentState -> AnimationState.Visible // 动画执行完毕，且可见
        this.isIdle && !this.currentState -> AnimationState.Invisible // 动画执行完毕，且不可见
        !this.isIdle && this.currentState -> AnimationState.Disappearing // 动画执行中，且逐渐不可见
        !this.isIdle && !this.currentState -> AnimationState.Appearing // 动画执行中，且逐渐可见
        else -> AnimationState.Appearing
    }
}










