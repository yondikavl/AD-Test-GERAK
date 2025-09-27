package com.yondikavl.question_7_memperbaiki_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yondikavl.question_7_memperbaiki_ui.ui.theme.MissionBadgeTheme
import com.yondikavl.question_7_memperbaiki_ui.ui.theme.Poppins

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MissionBadgeTheme {
                MissionBadgeScreen()
            }
        }
    }
}

@Composable
fun MissionBadgeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FF))
            .padding(24.dp)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 24.dp)
                .size(40.dp)
                .border(1.5.dp, Color(color = 0xFFCED3DF), RoundedCornerShape(12.dp))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color(color = 0xFF292D32)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.card_badge),
            contentDescription = "Badge Card",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
//                .padding(horizontal = 24.dp)
                .aspectRatio(0.6f),
            contentScale = ContentScale.FillWidth
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(
                        width = 1.5.dp,
                        color = Color(color = 0xFFCED3DF),
                        shape = RoundedCornerShape(24.dp)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF8F8FF))
            ) {
                Text(text = "Download", color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(
                        width = 1.5.dp,
                        color = Color(color = 0xFF292D32),
                        shape = RoundedCornerShape(24.dp)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(color = 0xFF292D32))
            ) {
                Text(text = "Share", color = Color.White, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }
        }
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MissionBadgePreview() {
    MissionBadgeScreen()
}
