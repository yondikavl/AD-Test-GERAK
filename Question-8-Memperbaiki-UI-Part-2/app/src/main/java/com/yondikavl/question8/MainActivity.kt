package com.yondikavl.question8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.yondikavl.question8.ui.LogMoodScreen
import com.yondikavl.question8.ui.theme.LogMoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogMoodTheme {
                Surface {
                    LogMoodScreen(
                        onBack = { finish() },
                        onLog = { mood, tags, note ->
                            println("Mood: $mood, Tags: $tags, Note: $note")
                        }
                    )
                }
            }
        }
    }
}