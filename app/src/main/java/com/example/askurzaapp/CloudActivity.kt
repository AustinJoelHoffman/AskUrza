package com.example.askurzaapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme
import kotlinx.coroutines.delay

/**
 * A composable responsible for displaying the thought bubble animation stages.
 */
@Composable
fun BoxScope.ThoughtBubbleAnimation(animationStage: Int, loyalty: Int, lightningCloudRes: Int) {
    // Bubble 1
    if (animationStage >= 1) {
        val resId = if (loyalty <= 3) R.drawable.bubble_1_angy else R.drawable.bubble_1
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-30).dp, y = (130).dp)
                .size(100.dp),
            contentScale = ContentScale.Fit
        )
    }

    // Bubble 2
    if (animationStage >= 2) {
        val resId = if (loyalty <= 1) R.drawable.bubble_2_angy
        else if (loyalty <= 3) R.drawable.bubble_2_rain
        else R.drawable.bubble_2
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-55).dp, y = (105).dp)
                .size(140.dp),
            contentScale = ContentScale.Fit
        )
    }

    // Bubble 3
    if (animationStage >= 3) {
        val resId = if (loyalty <= 1) R.drawable.bubble_3_angy
        else if (loyalty <= 3) R.drawable.bubble_3_rain
        else R.drawable.bubble_3
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-75).dp, y = (60).dp)
                .size(200.dp),
            contentScale = ContentScale.Fit
        )
    }

    // Cloud
    if (animationStage >= 4) {
        val resId = if (loyalty <= 1) R.drawable.cloud_angy
        else if (loyalty <= 3) R.drawable.cloud_rain
        else R.drawable.cloud
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-100).dp, y = (-110).dp)
                .size(400.dp),
            contentScale = ContentScale.Fit
        )
    }

    // Lightning
    if (animationStage == 5) {
        Image(
            painter = painterResource(id = lightningCloudRes),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-75).dp, y = (30).dp)
                .size(400.dp),
            contentScale = ContentScale.Fit
        )
    }

    // Post-Lightning animationStage 6
}


// Animation Previews

@Preview(name = "Normal Animation", showBackground = true)
@Composable
fun ThoughtBubbleAnimation_Normal_Preview() {
    AskUrzaAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            val animationStage = remember { mutableIntStateOf(0) }
            LaunchedEffect(Unit) {
                while(true) {
                    animationStage.intValue = 0
                    delay(1000)
                    animationStage.intValue = 1
                    delay(300)
                    animationStage.intValue = 2
                    delay(400)
                    animationStage.intValue = 3
                    delay(500)
                    animationStage.intValue = 4
                    delay(3000)
                }
            }
            ThoughtBubbleAnimation(animationStage = animationStage.intValue, loyalty = 4, lightningCloudRes = R.drawable.cloud)
        }
    }
}

@Preview(name = "Rain Animation", showBackground = true)
@Composable
fun ThoughtBubbleAnimation_Rain_Preview() {
    AskUrzaAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            val animationStage = remember { mutableIntStateOf(0) }
            LaunchedEffect(Unit) {
                while(true) {
                    animationStage.intValue = 0
                    delay(1000)
                    animationStage.intValue = 1
                    delay(300)
                    animationStage.intValue = 2
                    delay(400)
                    animationStage.intValue = 3
                    delay(500)
                    animationStage.intValue = 4
                    delay(3000)
                }
            }
            ThoughtBubbleAnimation(animationStage = animationStage.intValue, loyalty = 2, lightningCloudRes = R.drawable.cloud_rain)
        }
    }
}

@Preview(name = "Angry/Lightning Animation", showBackground = true)
@Composable
fun ThoughtBubbleAnimation_Angry_Preview() {
    AskUrzaAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            val animationStage = remember { mutableIntStateOf(0) }
            val lightningCloudRes = remember { mutableIntStateOf(R.drawable.cloud_angy) }
            LaunchedEffect(Unit) {
                while(true) {
                    animationStage.intValue = 0
                    delay(1000)
                    animationStage.intValue = 1
                    delay(300)
                    animationStage.intValue = 2
                    delay(400)
                    animationStage.intValue = 3
                    delay(500)
                    animationStage.intValue = 4
                    lightningCloudRes.intValue = R.drawable.cloud_angy
                    delay(1500)
                    animationStage.intValue = 5
                    lightningCloudRes.intValue = R.drawable.lightning_white
                    delay(25)
                    lightningCloudRes.intValue = R.drawable.lightning_bolt
                    delay(75)
                    animationStage.intValue = 6
                    delay(1400)
                }
            }
            ThoughtBubbleAnimation(animationStage = animationStage.intValue, loyalty = 1, lightningCloudRes = lightningCloudRes.intValue)
        }
    }
}