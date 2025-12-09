package com.smartpick.mysp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.PrimaryPurple
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var showAppName by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(500) // Delay before showing app name
        showAppName = true
        delay(3500) // Total splash duration
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        DarkBackground,
                        Color(0xFF1A0F2E),
                        DarkBackground
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated gradient background lines
        GradientWaveBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo with glow and animation
            LogoWithGlow()

            Spacer(modifier = Modifier.height(40.dp))

            // Typewriter-style app name reveal
            if (showAppName) {
                TypewriterText()
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Loading animation
            LoadingAnimation()
        }
    }
}

@Composable
private fun GradientWaveBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "waveOffset"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.1f)
    ) {
        val width = size.width
        val height = size.height

        // Draw gradient lines
        for (i in 0..5) {
            val y = (height / 6) * i + offsetY
            drawLine(
                color = PrimaryBlue.copy(alpha = 0.3f),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 2f
            )
        }
    }
}

@Composable
private fun LogoWithGlow() {
    val infiniteTransition = rememberInfiniteTransition(label = "logo")
    
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .size(120.dp)
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PrimaryBlue.copy(alpha = glowAlpha),
                            PrimaryPurple.copy(alpha = glowAlpha * 0.5f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
                .blur(20.dp)
        )

        // Inner logo circle
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PrimaryBlue, PrimaryPurple)
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SP",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun TypewriterText() {
    val text = "Smart Pick"
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        text.forEachIndexed { index, _ ->
            delay(80) // Typewriter speed
            displayedText = text.substring(0, index + 1)
        }
    }

    Text(
        text = displayedText,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.alpha(0.9f)
    )
}

@Composable
private fun LoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer spinning circle
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            PrimaryBlue,
                            PrimaryPurple,
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
                .scale(1f)
                .alpha(0.3f),
            contentAlignment = Alignment.Center
        ) {
            // Empty content
        }

        // Inner rotating indicator
        Canvas(
            modifier = Modifier
                .size(40.dp)
                .graphicsLayer(rotationZ = rotation)
        ) {
            drawArc(
                color = PrimaryBlue,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = false,
                size = Size(40.dp.toPx(), 40.dp.toPx()),
                style = Stroke(width = 3f)
            )
        }
    }
}
