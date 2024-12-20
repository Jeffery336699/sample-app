package com.example.compose.waveloading

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // WaveLoadingDemo()
            // DrawBehind()
            Box(
                Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colors.primary)
                    .clickable {
                        Toast
                            .makeText(
                                applicationContext,
                                "Hello World!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }) {
                Text(
                    "ssf",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(100.dp)
                        .width(100.dp),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h5,
                    fontSize = 20.sp,
                    letterSpacing = 2.sp,
                )
                Box(modifier = Modifier.size(50.dp).background(MaterialTheme.colors.secondary)){
                    Text(
                        "ss",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6,
                        fontSize = 10.sp,
                        letterSpacing = 1.sp,
                    )
                }
            }
        }
    }
}