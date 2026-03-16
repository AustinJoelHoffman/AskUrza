package com.example.askurzaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme

class DevActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskUrzaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DevScreen(
                        onEscapeClick = {
                            startActivity(Intent(this, EscapeActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DevScreen(
    onEscapeClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Dev_Escape Button at the top
            Button(
                onClick = onEscapeClick,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = "Dev_Escape")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DevScreenPreview() {
    AskUrzaAppTheme {
        DevScreen(onEscapeClick = {})
    }
}