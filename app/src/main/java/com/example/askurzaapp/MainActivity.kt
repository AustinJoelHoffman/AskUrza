package com.example.askurzaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskUrzaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        onAskUrzaClick = {
                            startActivity(Intent(this, AskUrzaActivity::class.java))
                        },
                        onAboutUrzaClick = {
                            startActivity(Intent(this, AboutUrzaActivity::class.java))
                        },
                        onExitClick = {
                            finishAffinity()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onAskUrzaClick: () -> Unit,
    onAboutUrzaClick: () -> Unit,
    onExitClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Silver App Border
        Image(
            painter = painterResource(id = R.drawable.urza_silver_boader),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Menu Content
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Main Menu",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onAskUrzaClick) {
                    Text(text = "Ask Urza")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onAboutUrzaClick) {
                    Text(text = "About Urza")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onExitClick) {
                    Text(text = "Exit")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AskUrzaAppTheme {
        MainScreen({}, {}, {})
    }
}