/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.compose

import CounterComponent
import Custom_Layout.IntrinsicDemo
import Custom_Layout.IntrinsicRow
import Custom_Layout.SubcompositionDemo
import Layout_UI.ConstraintLayoutDemo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ui.HomePage
import com.example.compose.ui.LoginPage
import com.example.compose.ui.WelcomePage
import com.example.compose.ui.theme.BloomTheme
import CompositionLocal.*
import gesture.BaseDragGestureDemo
import gesture.ClickDemo
import gesture.CombinedClickDemo
import gesture.DragDemo
import gesture.DragGestureDemo
import gesture.DragPlayGround
import gesture.DragToDismiss
import gesture.PaddingDemo
import gesture.SwipeableDemo
import gesture.TapGestureDemo
import gesture.TransformGestureDemo
import gesture.TransformerDemo

class MainActivity : AppCompatActivity() {

    var theme: BloomTheme by mutableStateOf(BloomTheme.LIGHT)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setDecorFitsSystemWindows(false)
        super.onCreate(savedInstanceState)
        setContent {
            // WelcomePage()
            // LoginPage()
            // HomePage()
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
            CompositionLocalScreen()
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
