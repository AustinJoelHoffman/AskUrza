package com.example.askurzaapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.data.local.db.AppDatabase
import com.example.askurzaapp.data.repository.LoyaltyRepositoryImpl
import com.example.askurzaapp.domain.repository.LoyaltyRepository
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme
import kotlinx.coroutines.launch

class DevActivity : BaseActivity() {
    
    private val repository: LoyaltyRepository by lazy {
        val database = AppDatabase.getDatabase(applicationContext)
        LoyaltyRepositoryImpl(database, database.loyaltyAbilityDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskUrzaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()
                    val isUpdating = remember { mutableStateOf(false) }

                    DevScreen(
                        onEscapeClick = {
                            startActivity(Intent(this, EscapeActivity::class.java))
                        },
                        onUpdateDatabaseClick = {
                            isUpdating.value = true
                            scope.launch {
                                repository.updatePlaneswalkers()
                                    .onSuccess {
                                        isUpdating.value = false
                                        Toast.makeText(this@DevActivity, "Database updated!", Toast.LENGTH_SHORT).show()
                                    }
                                    .onFailure {
                                        isUpdating.value = false
                                        Toast.makeText(this@DevActivity, "Update failed: ${it.message}", Toast.LENGTH_LONG).show()
                                    }
                            }
                        },
                        isUpdating = isUpdating.value
                    )
                }
            }
        }
    }
}

@Composable
fun DevScreen(
    onEscapeClick: () -> Unit,
    onUpdateDatabaseClick: () -> Unit,
    isUpdating: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = onEscapeClick,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = "Dev_Escape")
            }

            Button(
                onClick = onUpdateDatabaseClick,
                enabled = !isUpdating,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = if (isUpdating) "Updating..." else "Update Database (Scryfall)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DevScreenPreview() {
    AskUrzaAppTheme {
        DevScreen(onEscapeClick = {}, onUpdateDatabaseClick = {})
    }
}
