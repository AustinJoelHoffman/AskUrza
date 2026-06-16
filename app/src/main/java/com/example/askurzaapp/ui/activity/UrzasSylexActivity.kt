package com.example.askurzaapp.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme
import kotlin.math.roundToInt

class UrzasSylexActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskUrzaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UrzasSylexScreen()
                }
            }
        }
    }
}

@Composable
fun UrzasSylexScreen() {
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        for (i in 0..10) {
            shakeOffset.animateTo(30f, animationSpec = tween(50))
            shakeOffset.animateTo(-30f, animationSpec = tween(50))
        }
        shakeOffset.animateTo(0f, animationSpec = tween(50))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(shakeOffset.value.roundToInt(), 0) },
        contentAlignment = Alignment.Center
    ) {
        Text("Urza's Sylex Activity Placeholder")
    }
}

@Preview(showBackground = true)
@Composable
fun UrzasSylexScreenPreview() {
    AskUrzaAppTheme {
        UrzasSylexScreen()
    }
}
