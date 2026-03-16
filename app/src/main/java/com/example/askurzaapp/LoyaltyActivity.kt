package com.example.askurzaapp

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class LoyaltyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val source = intent.getStringExtra("LOYALTY_SOURCE") ?: "unknown"
        setContent {
            AskUrzaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black.copy(alpha = 0.5f)
                ) {
                    LoyaltyMenuScreen(
                        source = source,
                        onResume = { finish() }
                    )
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

@Composable
fun LoyaltyMenuScreen(
    source: String,
    onResume: () -> Unit
) {
    val oracleText = remember { mutableStateOf<String?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                // 1. Fetch a single random planeswalker
                val randomCardUrl = "https://api.scryfall.com/cards/random?q=type%3Aplaneswalker"
                val connection = URL(randomCardUrl).openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "AskUrzaApp/1.0")
                connection.setRequestProperty("Accept", "application/json")
                
                val randomCardResponse = connection.inputStream.bufferedReader().use { it.readText() }
                val targetCard = JSONObject(randomCardResponse)

                // 2. Extract Oracle Text and Name
                var text = targetCard.optString("oracle_text", "")
                var cardName = targetCard.optString("name", "")
                
                if (text.isEmpty() && targetCard.has("card_faces")) {
                    val faces = targetCard.getJSONArray("card_faces")
                    if (faces.length() > 0) {
                        val face = faces.getJSONObject(0)
                        text = face.optString("oracle_text", "")
                        cardName = face.optString("name", "")
                    }
                }

                if (text.isNotEmpty()) {
                    // 3. Replace card name with "Urza"
                    var modifiedText = text.replace(cardName, "Urza")
                    
                    if (cardName.contains(",")) {
                        val shortName = cardName.substringBefore(",")
                        modifiedText = modifiedText.replace(shortName, "Urza")
                    }

                    // 4. Parse and select ability based on source
                    val loyaltyRegex = Regex("^[+\\u2212\\u2013\\-]?(\\d+|[Xx]):.*")
                    val lines = modifiedText.split("\n")
                    val loyaltyAbilities = lines.filter { it.trim().matches(loyaltyRegex) }
                    
                    if (loyaltyAbilities.isNotEmpty()) {
                        oracleText.value = when (source) {
                            "top" -> loyaltyAbilities.first()
                            "bottom" -> loyaltyAbilities.last()
                            "middle" -> {
                                if (loyaltyAbilities.size > 2) {
                                    loyaltyAbilities.subList(1, loyaltyAbilities.size - 1).random()
                                } else {
                                    loyaltyAbilities.random()
                                }
                            }
                            else -> modifiedText // Fallback
                        }
                    } else {
                        oracleText.value = modifiedText
                    }

                } else {
                    oracleText.value = "No oracle text found for $cardName"
                }

            } catch (e: Exception) {
                Log.e("LoyaltyActivity", "Error fetching data", e)
                oracleText.value = "Error: ${e.localizedMessage}"
            } finally {
                delay(1000)
                isLoading.value = false
            }
        }
    }

    // UI remains the same...
    Box(
        modifier = Modifier.fillMaxSize().clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onResume
        ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {}
                ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = onResume,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator()
                        
                        val textOptions = listOf(
                            "Consulting the Multiverse.",
                            "Consulting the Multiverse..",
                            "Consulting the Multiverse..."
                        )
                        val currentText = remember { mutableStateOf(textOptions[0]) }

                        LaunchedEffect(Unit) {
                            var index = 0
                            while (true) {
                                delay(500)
                                index = (index + 1) % textOptions.size
                                currentText.value = textOptions[index]
                            }
                        }
                        
                        Text(text = currentText.value, modifier = Modifier.padding(top = 8.dp))
                    } else {
                        Text(
                            text = oracleText.value ?: "Unknown",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoyaltyMenuScreenPreview() {
    AskUrzaAppTheme {
        LoyaltyMenuScreen(source = "preview", onResume = {})
    }
}
