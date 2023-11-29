package com.example.compose.insetsDemo

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun InsetsDemo() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(Color.Cyan, useDarkIcons)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("TopAppBar")
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentPadding = WindowInsets.statusBars.asPaddingValues()
            )
        },
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars), onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.Black
    ) { }
}
