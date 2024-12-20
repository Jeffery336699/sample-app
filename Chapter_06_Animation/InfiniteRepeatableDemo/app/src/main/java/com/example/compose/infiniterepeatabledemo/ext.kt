package com.example.compose.infiniterepeatabledemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed


/**
 * 为 Modifier 添加移除监听器的扩展函数。
 *
 * 此函数使用 DisposableEffect 在组件从组合中移除时执行操作。
 *
 * @param onDispose 组件移除时执行的操作。
 * @return 添加了移除监听器的 Modifier。
 */
@Composable
fun Modifier.onDispose(onDispose: () -> Unit): Modifier = this.then(
    Modifier.composed {
        val currentOnDispose by rememberUpdatedState(onDispose)
        DisposableEffect(Unit) {
            onDispose {
                currentOnDispose()
            }
        }
        this
    }
)