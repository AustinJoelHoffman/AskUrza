package com.example.askurzaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

class AskUrzaActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskUrzaAppTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Welcome to Ask Urza!")
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AskUrzaScreen(
                            onDevToolsClick = {
                                startActivity(Intent(this@AskUrzaActivity, DevActivity::class.java))
                            },
                            onLoyaltyClick = { source ->
                                val intent = Intent(this@AskUrzaActivity, LoyaltyActivity::class.java).apply {
                                    putExtra("LOYALTY_SOURCE", source)
                                }
                                startActivity(intent)
                            },
                            onUrzasSylexClick = {
                                startActivity(Intent(this@AskUrzaActivity, UrzasSylexActivity::class.java))
                            },
                            onReload = {
                                recreate()
                            }
                        )
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AskUrzaScreen(
    onDevToolsClick: () -> Unit,
    onLoyaltyClick: (String) -> Unit,
    onUrzasSylexClick: () -> Unit,
    onReload: () -> Unit
) {
    val loyaltyCounter = remember { mutableIntStateOf(4) }
    
    // Animation States
    val animationStage = remember { mutableIntStateOf(0) } // 0: None, 1: B1, 2: B2, 3: B3, 4: Cloud
    val lightningCloudRes = remember { mutableIntStateOf(R.drawable.cloud_angy) }

    LaunchedEffect(Unit) {
        while (true) {
            // Wait 20-60 seconds
            delay(Random.nextLong(20000, 60000))

            val loyalty = loyaltyCounter.intValue
            
            // Stage 1: Bubble 1
            animationStage.intValue = 1
            delay(300)

            // Stage 2: Bubble 2
            animationStage.intValue = 2
            delay(400)

            // Stage 3: Bubble 3
            animationStage.intValue = 3
            delay(500)

            // Stage 4: Cloud
            animationStage.intValue = 4
            
            // Lightning Sequence for Loyalty <= 1
            if (loyalty <= 1) {
                lightningCloudRes.intValue = R.drawable.lightning_white
                delay(100)
                lightningCloudRes.intValue = R.drawable.lightning_red
                delay(100)
                lightningCloudRes.intValue = R.drawable.cloud_angy
            }

            // Hold final state
            delay(3000)
            
            // Reset
            animationStage.intValue = 0
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Half
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
        ) {
            // Urza, Academy Headmaster
            Box(
                 modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (64).dp)
                    .aspectRatio(63f / 88f)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.urza_academy_headmaster_blank),
                    contentDescription = "Urza Academy Headmaster",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // Loyalty Counters
                Text(
                    text = loyaltyCounter.intValue.toString(),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 38.dp, bottom = 36.dp)
                )

                // +1 Loyalty Ability
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (85).dp)
                        .aspectRatio(63f / 14f)
                        .padding(16.dp)
                        .clickable(onClick = {
                            loyaltyCounter.intValue += 1
                            onLoyaltyClick("top")
                        })
                )

                // -1 Loyalty Ability
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (132).dp)
                        .aspectRatio(63f / 14f)
                        .padding(16.dp)
                        .clickable(onClick = {
                            if (loyaltyCounter.intValue > 1) {
                                loyaltyCounter.intValue -= 1
                                onLoyaltyClick("middle")
                            } else if (loyaltyCounter.intValue == 1) {
                                onReload()
                            }
                        })
                )

                // -6 Loyalty Ability
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (180).dp)
                        .aspectRatio(63f / 14f)
                        .padding(16.dp)
                        .clickable(onClick = {
                            if (loyaltyCounter.intValue == 1) {
                                // Sylex
                                loyaltyCounter.intValue = 4
                                onUrzasSylexClick()
                            } else if (loyaltyCounter.intValue > 6) {
                                loyaltyCounter.intValue -= 6
                                onLoyaltyClick("bottom")
                            } else if (loyaltyCounter.intValue == 6) {
                                onReload()
                            }
                        })
                )

                // Refactored call to the new animation composable
                if (animationStage.intValue > 0) {
                    ThoughtBubbleAnimation(
                        animationStage = animationStage.intValue,
                        loyalty = loyaltyCounter.intValue,
                        lightningCloudRes = lightningCloudRes.intValue
                    )
                }
            }

            // Dev_Tools Button
            Button(
                onClick = onDevToolsClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Text(text = "Dev_Tools")
            }
        }

        // Bottom Half
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}