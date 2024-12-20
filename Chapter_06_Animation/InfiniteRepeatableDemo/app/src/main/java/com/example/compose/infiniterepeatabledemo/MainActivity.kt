package com.example.compose.infiniterepeatabledemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.compose.infiniterepeatabledemo.ui.theme.InfiniteRepeatableDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfiniteRepeatableDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // InfinitRepetableDemo()
                        // AdvancedAnimationsScreen()
                        // ContentTransformScreen()
                        // CrossfadeScreen()
                        // AnimationContentSizeScreen()
                        // AnimateXxAsStateScreen()
                        // AnimatableScreen()
                        SwitchBlock()
                    }
                }
            }
        }
    }
}
