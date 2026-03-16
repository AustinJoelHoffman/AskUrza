package com.example.askurzaapp

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme

class EscapeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskUrzaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black.copy(alpha = 0.5f) // Semi-transparent overlay
                ) {
                    EscapeMenuScreen(
                        onResume = { finish() },
                        onMainMenu = {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        },
                        onExit = { finishAffinity() }
                    )
                }
            }
        }
    }

    // Resume background tasks by pressing the escape key
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

@Composable
fun EscapeMenuScreen(
    onResume: () -> Unit,
    onMainMenu: () -> Unit,
    onExit: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Escape Card
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
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
                // Close Button
                IconButton(
                    onClick = onResume,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }

                // Menu Buttons
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 16.dp), // Space for close button
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onResume,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text(text = "Resume")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onMainMenu,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text(text = "Main Menu")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onExit,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text(text = "Exit")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EscapeMenuScreenPreview() {
    AskUrzaAppTheme {
        EscapeMenuScreen({}, {}, {})
    }
}