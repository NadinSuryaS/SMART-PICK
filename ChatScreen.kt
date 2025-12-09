package com.smartpick.mysp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.GeminiApiService
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val content: String,
    val isUserMessage: Boolean,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val language: String = "en"
) {
    fun getFormattedTime(): String {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}

@Composable
fun ChatScreen(
    onBackClick: () -> Unit
) {
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isVoiceMode by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("en") }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F0620),
                        Color(0xFF1A0F2E),
                        Color(0xFF0D1B2A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Premium Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF1A0F2E).copy(alpha = 0.8f),
                                Color(0xFF2D1B4E).copy(alpha = 0.5f)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = PrimaryBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(
                                text = "✨ Smart Pick Assistant",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "AI-Powered Phone Recommendations",
                                fontSize = 11.sp,
                                color = Color(0xFFC8C8C8)
                            )
                        }
                    }
                    
                    // Delete Chat Button
                    IconButton(
                        onClick = {
                            messages = emptyList()
                        }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Chat",
                            tint = Color(0xFFFF6B6B),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            // Language Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.02f))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Language:",
                    fontSize = 12.sp,
                    color = Color(0xFFC8C8C8),
                    fontWeight = FontWeight.SemiBold
                )
                listOf("en" to "English", "hi" to "हिंदी", "ta" to "தமிழ்").forEach { (code, label) ->
                    FilterChip(
                        selected = selectedLanguage == code,
                        onClick = { selectedLanguage = code },
                        label = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (selectedLanguage == code) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.height(32.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.08f),
                            labelColor = Color(0xFFC8C8C8)
                        )
                    )
                }
            }

            // Messages List with Glass Effect
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { message ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it })
                    ) {
                        ChatMessageBubble(message = message)
                    }
                }
                
                if (isLoading) {
                    item {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { it })
                        ) {
                            BotLoadingIndicator()
                        }
                    }
                }
            }

            // Scroll to bottom
            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty()) {
                    listState.animateScrollToItem(messages.size - 1)
                }
            }

            // Premium Input Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF1A0F2E).copy(alpha = 0.8f),
                                Color(0xFF2D1B4E).copy(alpha = 0.5f)
                            )
                        )
                    )
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(alpha = 0.06f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp),
                        placeholder = {
                            Text(
                                text = "Ask about phones…",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = PrimaryBlue,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp)
                    )

                    // Voice Mode Button
                    IconButton(
                        onClick = { isVoiceMode = !isVoiceMode },
                        modifier = Modifier.size(40.dp),
                        enabled = !isLoading
                    ) {
                        Text(
                            text = "🎤",
                            fontSize = 20.sp,
                            modifier = Modifier.alpha(if (isVoiceMode) 1f else 0.5f)
                        )
                    }

                    // Send Button
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank() && !isLoading) {
                                val userMessage = ChatMessage(
                                    content = inputText,
                                    isUserMessage = true,
                                    language = selectedLanguage
                                )
                                messages = messages + userMessage
                                val userInput = inputText
                                inputText = ""
                                isLoading = true

                                scope.launch {
                                    try {
                                        val response = GeminiApiService.sendMessage(
                                            userInput,
                                            selectedLanguage
                                        )
                                        val assistantMessage = ChatMessage(
                                            content = response,
                                            isUserMessage = false,
                                            language = selectedLanguage
                                        )
                                        messages = messages + assistantMessage
                                    } catch (e: Exception) {
                                        val errorMessage = ChatMessage(
                                            content = "Sorry, I encountered an error. Please try again.",
                                            isUserMessage = false,
                                            language = selectedLanguage
                                        )
                                        messages = messages + errorMessage
                                    } finally {
                                        isLoading = false
                                    }
                                }
                            }
                        },
                        modifier = Modifier.size(40.dp),
                        enabled = !isLoading && inputText.isNotBlank()
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Send",
                            tint = if (isLoading || inputText.isBlank()) {
                                Color.White.copy(alpha = 0.3f)
                            } else {
                                PrimaryBlue
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BotLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = Color.White.copy(alpha = 0.06f),
                    shape = RoundedCornerShape(18.dp)
                )
                .clip(RoundedCornerShape(18.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "🤖",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 4.dp)
            )
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(
                            PrimaryBlue,
                            RoundedCornerShape(3.dp)
                        )
                )
            }
            Text(
                text = "Thinking...",
                fontSize = 12.sp,
                color = Color(0xFFC8C8C8),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (message.isUserMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        if (!message.isUserMessage) {
            // Bot Message with Icon
            Row(
                modifier = Modifier.fillMaxWidth(0.90f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "✨",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = Color.White.copy(alpha = 0.06f),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .clip(RoundedCornerShape(18.dp))
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(18.dp),
                            ambientColor = PrimaryBlue.copy(alpha = 0.2f)
                        )
                        .padding(14.dp)
                ) {
                    Column {
                        Text(
                            text = message.content,
                            fontSize = 13.sp,
                            color = Color.White,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = message.getFormattedTime(),
                            fontSize = 10.sp,
                            color = Color(0xFFC8C8C8),
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        } else {
            // User Message
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .background(
                        color = PrimaryBlue,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .clip(RoundedCornerShape(18.dp))
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = PrimaryBlue.copy(alpha = 0.3f)
                    )
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = message.content,
                        fontSize = 13.sp,
                        color = Color.White,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = message.getFormattedTime(),
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
    }
}
