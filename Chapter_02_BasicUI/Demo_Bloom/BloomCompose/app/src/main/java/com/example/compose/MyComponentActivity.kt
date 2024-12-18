package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ui.HomePage
import com.example.compose.ui.LoginPage
import com.example.compose.ui.WelcomePage
import com.example.compose.ui.theme.BloomTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

class MyComponentActivity : ComponentActivity() {
    var theme: BloomTheme by mutableStateOf(BloomTheme.LIGHT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.primary
                ) {
                    // WelcomePage()
                    // LoginPage()
                    HomePage()
                    // SubcompositionDemo()
                    // ConstraintLayoutDemo()
                    // CounterComponent()
                    // IntrinsicDemo()
                    // BaseDragGestureDemo()
                    // DragGestureDemo()
                    // TapGestureDemo()
                    // TransformGestureDemo()
                    // DragToDismiss()
                    // PaddingDemo()
                    // CompositionLocalScreen()
                    // DefaultPreview2()
                }
        }
    }
}


@Composable
fun WelcomePageLightPreview() {
    WelcomePage()
}

@Composable
fun LoginPageLightPreview() {
    LoginPage()
}

@Composable
fun HomePageLightPreview() {
    HomePage()
}

/**
 * snapshotFlow演示学习
 */
@Composable
fun SnapshotFlowExample() {
    // 跨重组数据保存与监听
    val (text, setText) = remember { mutableStateOf("") }
    val (messages, setMessages) = remember { mutableStateOf(listOf<String>()) }
    // key1=Unit的话就不会触发重组了，里面的lamdam就仅仅在第一次onActive时才会执行
    LaunchedEffect(key1 = text) {
        println("snapshotFlow 11111")
        snapshotFlow { text }
            .collect { newText ->
                if (newText.isNotEmpty()) {
                    setMessages(messages + newText)
                    setText("")
                }
            }
        // 这里执行不到因为上述采取的是每次都刷新LaunchedEffect，所以不会执行到这
        println("snapshotFlow 222222")
    }

    Column {
        TextField(
            value = text,
            onValueChange = setText,
            label = { Text("Enter message") }
        )
        messages.forEach { message ->
            Text(message)
        }
    }
}

@Composable
fun DefaultPreview2() {
    SnapshotFlowExample()
}

@Composable
fun ProduceStateExample() {
    val loadingText: State<String> = produceState(initialValue = "Loading...",1,2) {
        delay(2000) // 模拟网络请求延迟
        value = "Data Loaded"
    }

    if (loadingText.value == "Loading...") {
        Text("Loading...")
    } else {
        Text(loadingText.value)
    }
}

@Preview(showBackground = true)
@Composable
fun ProduceStateExamplePreview() {
    ProduceStateExample()
}

