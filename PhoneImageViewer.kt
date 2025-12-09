package com.smartpick.mysp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark
import kotlinx.coroutines.delay

@Composable
fun PhoneImageViewer(
    imageUrl: String,
    phoneName: String,
    modifier: Modifier = Modifier
) {
    var rotation by remember { mutableStateOf(0f) }
    var autoRotate by remember { mutableStateOf(true) }
    var dragAmount by remember { mutableStateOf(0f) }

    // Auto-rotation animation
    LaunchedEffect(autoRotate) {
        while (autoRotate) {
            rotation = (rotation + 2f) % 360f
            delay(50)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "360° View",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryBlue
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Image with rotation
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                .pointerInput(Unit) {
                    detectDragGestures { change, dragChange ->
                        change.consume()
                        autoRotate = false
                        dragAmount += dragChange.x
                        rotation = (dragAmount / 2f) % 360f
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            // Simple phone emoji instead of AsyncImage to avoid crashes
            Text(
                text = "📱",
                fontSize = 100.sp,
                modifier = Modifier.rotate(rotation)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Instructions
        Text(
            text = if (autoRotate) "Auto-rotating • Drag to rotate manually" else "Drag to rotate",
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Toggle auto-rotate button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (autoRotate) PrimaryBlue else Color.White.copy(alpha = 0.1f),
                    RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .clickable { autoRotate = !autoRotate },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (autoRotate) "⏸ Pause Auto-Rotate" else "▶ Resume Auto-Rotate",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}
