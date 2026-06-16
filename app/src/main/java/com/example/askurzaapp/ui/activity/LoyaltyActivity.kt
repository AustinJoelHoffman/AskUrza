package com.example.askurzaapp.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.data.local.db.AppDatabase
import com.example.askurzaapp.data.repository.LoyaltyRepositoryImpl
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme
import com.example.askurzaapp.ui.viewmodel.LoyaltyUiState
import com.example.askurzaapp.ui.viewmodel.LoyaltyViewModel
import com.example.askurzaapp.ui.viewmodel.LoyaltyViewModelFactory
import kotlinx.coroutines.delay

class LoyaltyActivity : ComponentActivity() {

    private val viewModel: LoyaltyViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        LoyaltyViewModelFactory(LoyaltyRepositoryImpl(database, database.loyaltyAbilityDao()))
    }

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
                        viewModel = viewModel,
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
    viewModel: LoyaltyViewModel,
    onResume: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchLoyaltyAbility(source)
    }

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
                    when (val state = uiState) {
                        is LoyaltyUiState.Loading -> {
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
                        }
                        is LoyaltyUiState.Success -> {
                            Text(
                                text = state.ability.text,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        is LoyaltyUiState.Error -> {
                            Text(
                                text = "Error: ${state.message}",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
