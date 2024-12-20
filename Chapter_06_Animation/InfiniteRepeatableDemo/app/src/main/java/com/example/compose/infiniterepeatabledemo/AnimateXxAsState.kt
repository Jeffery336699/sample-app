package com.example.compose.infiniterepeatabledemo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimateXxAsStateScreen() {
    var change by remember {
        mutableStateOf(false)
    }
    var flg by remember {
        mutableStateOf(false)
    }

    val buttonSize by animateDpAsState(targetValue = if (change) 32.dp else 24.dp)
    // Optimize: 一串渐变的值构成了动画
    println("buttonSize: $buttonSize")
    val buttonColor by animateColorAsState(
        targetValue = if (flg) Color.Red else Color.Gray,
        animationSpec = spring(stiffness = Spring.StiffnessHigh)
    )

    if (buttonSize == 32.dp) {
        change = false
    }
    IconButton(
        onClick = {
            change = true
            flg = !flg
        }
    ) {
        Icon(
            Icons.Rounded.Favorite,
            contentDescription = null,
            tint = buttonColor,
            modifier = Modifier.size(buttonSize)
        )
    }
}

/**
 * animateXxAsState 改造成它的底层实现 Animatable形式
 */
@Composable
fun AnimatableScreen() {
    var change by remember {
        mutableStateOf(false)
    }
    var flg by remember {
        mutableStateOf(false)
    }

    val buttonSizeVariable = remember { Animatable(24.dp, Dp.VectorConverter) }
    val buttonColorVariable = remember { androidx.compose.animation.Animatable(Color.Gray) }

    LaunchedEffect(change, flg) {
        buttonSizeVariable.animateTo(if (change) 32.dp else 24.dp)
        buttonColorVariable.animateTo(if (flg) Color.Red else Color.Gray)
    }

    if (buttonSizeVariable.value == 32.dp) {
        change = false
    }
    IconButton(
        onClick = {
            change = true
            flg = !flg
        }
    ) {
        Icon(
            Icons.Rounded.Favorite,
            contentDescription = null,
            tint = buttonColorVariable.value,
            modifier = Modifier.size(buttonSizeVariable.value)
        )
    }
}
















