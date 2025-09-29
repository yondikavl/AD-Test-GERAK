package com.yondikavl.question8.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yondikavl.question8.ui.theme.LogMoodTheme
import com.yondikavl.question8.R
import com.yondikavl.question8.ui.theme.Poppins

private val Background = Color(0xFFF6F8F9)
private val PrimaryText = Color(0xFF111216)
private val MutedText = Color(0xFF6B7175)
private val Accent = Color(0xFFF39C68)
private val ChipSelected = Color(0xFF39A9CF)
private val ChipUnselected = Color(0xFFECECEC)
private val BorderChip = Color(0xFFD6D6D6)
private val PlaceHolder = Color(0xFF7E8186)
private val button = Color(0xFF0E89B3)

data class Mood(val id: Int, val iconRes: Int, val label: String)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LogMoodScreen(
    onBack: () -> Unit = {},
    onLog: (mood: Mood?, tags: Set<String>, note: String) -> Unit = { _, _, _ -> }
) {
    val moods = listOf(
        Mood(0, R.drawable.ic_angry, "Angry"),
        Mood(1, R.drawable.ic_sad, "Sad"),
        Mood(2, R.drawable.ic_neutral, "Neutral"),
        Mood(3, R.drawable.ic_pleasant, "Pleasant"),
        Mood(4, R.drawable.ic_happy, "Happy")
    )

    var selectedMoodIndex by remember { mutableIntStateOf(3) }
    val tags = listOf("Work", "Exercise", "Family", "Hobbies", "Finances",  "Sleep", "Drink", "Food", "Relationships", "Education", "Weather", "Music", "Friends")
    var selectedTags by remember { mutableStateOf(setOf(tags.first())) }
    var note by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Back",
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Text(
                            text = "Log Mood",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )

                        Box(modifier = Modifier.size(68.dp)) { }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },
        containerColor = Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "How are you feeling?",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            MoodSelector(
                moods = moods,
                selectedIndex = selectedMoodIndex,
                onSelected = { selectedMoodIndex = it }
            )

            Spacer(modifier = Modifier.height(6.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "State of Mind",
                    fontSize = 12.sp,
                    color = MutedText
                )
                Text(
                    text = moods.getOrNull(selectedMoodIndex)?.label ?: "",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "What's affecting your mood?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    val selected = selectedTags.contains(tag)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (selected) ChipSelected else ChipUnselected,
                        tonalElevation = 2.dp,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 40.dp)
                            .border(
                                width = 2.dp,
                                color = if (selected) button else BorderChip,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                selectedTags = if (selected) {
                                    selectedTags - tag
                                } else {
                                    selectedTags + tag
                                }
                            }
                    ) {
                        Text(
                            text = tag,
                            color = if (selected) Color.White else PrimaryText,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Let's write about it",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = {
                    Text(
                        "How is your day going and how has it affected your mood?",
                        fontSize = 14.sp,
                        color = PlaceHolder
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                singleLine = false,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFFD6D6D6),
                    unfocusedIndicatorColor = Color(0xFFD6D6D6),
                    focusedTextColor = PrimaryText,
                    unfocusedTextColor = PrimaryText,
                    focusedPlaceholderColor = MutedText,
                    unfocusedPlaceholderColor = MutedText
                )
            )


            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val mood = moods.getOrNull(selectedMoodIndex)
                    onLog(mood, selectedTags, note)
                },
                modifier = Modifier
                    .height(48.dp)
                    .widthIn(min = 160.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = button)
            ) {
                Text(
                    text = "Log Mood",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    fontFamily = Poppins
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MoodSelector(
    moods: List<Mood>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        moods.forEachIndexed { index, mood ->
            val selected = index == selectedIndex

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .size(if (selected) 68.dp else 56.dp)
                    .clickable { onSelected(index) }
            ) {
                if (selected) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .drawBehind {
                                drawCircle(
                                    brush = Brush.radialGradient(
                                        colors = listOf(Accent.copy(alpha = 0.22f), Color.Transparent),
                                        center = this.center,
                                        radius = size.minDimension / 1.2f
                                    ),
                                    radius = size.minDimension / 2
                                )

                                val ringRadius = size.minDimension / 2 - 8f
                                drawCircle(
                                    color = Color(0xFFDAEDF4),
                                    radius = ringRadius,
                                    style = Stroke(width = 14f)
                                )

                                val triangleSize = 24f
                                val triangleOffset = 8f

                                val topPath = Path().apply {
                                    moveTo(center.x, center.y - ringRadius - triangleOffset)
                                    lineTo(center.x - triangleSize / 2, center.y - ringRadius - triangleSize)
                                    lineTo(center.x + triangleSize / 2, center.y - ringRadius - triangleSize)
                                    close()
                                }
                                drawPath(topPath, color = ChipSelected)

                                val bottomPath = Path().apply {
                                    moveTo(center.x, center.y + ringRadius + triangleOffset)
                                    lineTo(center.x - triangleSize / 2, center.y + ringRadius + triangleSize)
                                    lineTo(center.x + triangleSize / 2, center.y + ringRadius + triangleSize)
                                    close()
                                }
                                drawPath(bottomPath, color = ChipSelected)
                            }
                    )
                }

                if (selected) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFB7E3F2),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = mood.iconRes),
                                contentDescription = mood.label,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                } else {
                    Image(
                        painter = painterResource(id = mood.iconRes),
                        contentDescription = mood.label,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F9)
@Composable
fun LogMoodPreview() {
    LogMoodTheme {
        LogMoodScreen()
    }
}
