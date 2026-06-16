package com.example.askurzaapp.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.askurzaapp.R
import com.example.askurzaapp.ui.theme.AskUrzaAppTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class AboutUrzaActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AskUrzaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AboutUrzaScreen()
                }
            }
        }
    }
}

@Composable
fun AboutUrzaScreen() {
    val scrollState = rememberScrollState()
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.urza_silver_boader),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp, top = 48.dp, bottom = 48.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Urza",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val rulesText = """
Urza, Academy Headmaster is just like any other planeswalker, except you're never quite sure what his abilities are going to do. Urza is very unpredictable. To find out what happens, head to AskUrza.com. There you will find three buttons: +1, -1, and -6. Click the button for the loyalty ability that's resolving, and follow whatever instructions appear.
(2018-01-19)

Urza's abilities work a little differently with respect to targeting. Unlike other abilities, you may have to choose the targets for an ability as part of that ability resolving. If the ability that comes up requires a target and there are no legal targets available, click again until that's not true.
(2018-01-19)

If an ability comes up that requires a target and there are legal targets available, you must use that ability even if you may not want to. However, the effects are all good for you or bad for your opponent. In some cases, you may not be able to take advantage. For example, if it ends up that creatures you control get +1/+1 until end of turn but you don't control any creatures, you're out of luck. But nothing bad will happen.
(2018-01-19)

Players can respond to the activation of any of Urza's abilities, but not after players know what effect is going to happen.
(2018-01-19)
            """.trimIndent()

            Text(
                text = rulesText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val annotatedString = buildAnnotatedString {
                append("For more information, visit: ")
                pushStringAnnotation(tag = "URL", annotation = "https://scryfall.com/card/ust/136/urza-academy-headmaster")
                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                    append("Scryfall")
                }
                pop()
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let {
                            showDialog.value = true
                        }
                }
            )
        }
    }

    if (showDialog.value) {
        ExternalLinkDialog(
            onDismiss = { showDialog.value = false },
            onProceed = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://scryfall.com/card/ust/136/urza-academy-headmaster"))
                context.startActivity(intent)
                showDialog.value = false
            }
        )
    }
}

@Composable
fun ExternalLinkDialog(
    onDismiss: () -> Unit,
    onProceed: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val shakeOffset = remember { Animatable(0f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Visiting external websites is scary") },
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Leave")
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                for (i in 0..5) {
                                    shakeOffset.animateTo(10f, animationSpec = tween(50))
                                    shakeOffset.animateTo(-10f, animationSpec = tween(50))
                                }
                                shakeOffset.animateTo(0f, animationSpec = tween(50))
                            }
                        },
                        modifier = Modifier.offset { IntOffset(shakeOffset.value.roundToInt(), 0) }
                    ) {
                        Text("Cower")
                    }

                    Button(onClick = onProceed) {
                        Text("Proceed")
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AboutUrzaScreenPreview() {
    AskUrzaAppTheme {
        AboutUrzaScreen()
    }
}
