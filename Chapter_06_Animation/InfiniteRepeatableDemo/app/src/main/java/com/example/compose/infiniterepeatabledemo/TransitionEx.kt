package com.example.compose.infiniterepeatabledemo

import android.graphics.Color.alpha
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class SwitchState {
    object OPEN : SwitchState()
    object CLOSE : SwitchState()
}

@Composable
fun SwitchBlock() {
    var selectState: SwitchState by remember {
        mutableStateOf(SwitchState.OPEN)
    }

    val transition = updateTransition(selectState, label = "Switch")
    val selectBarPadding by transition.animateDp(transitionSpec = { tween(1000) }, label = "") {
        when (it) {
            SwitchState.OPEN -> 40.dp
            SwitchState.CLOSE -> 0.dp
        }
    }

    val textAlpha by transition.animateFloat(transitionSpec = { tween(1000) }, label = "") {
        when (it) {
            SwitchState.OPEN -> 0f
            SwitchState.CLOSE -> 1f
        }
    }

    Box(
        modifier = Modifier
            .size(250.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                selectState = when (selectState) {
                    SwitchState.OPEN -> SwitchState.CLOSE
                    SwitchState.CLOSE -> SwitchState.OPEN
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "点我",
            color = Color.Blue,
            fontSize = 18.sp,
            fontWeight = FontWeight.W900,
            modifier = Modifier
                .alpha(1 - textAlpha)
                .align(Alignment.Center)
        )
        println("selectBarPadding: $selectBarPadding")
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .padding(top = selectBarPadding) // 首次padding==0全部显示出来,当padding增大时,内容逐渐隐藏(细节得结合上面align(Alignment.BottomCenter))
                .background(Color(0xFFAF6878))
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(textAlpha),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White,
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = "已选",
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W900
                )
            }
        }
    }

}