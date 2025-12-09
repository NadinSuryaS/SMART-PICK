package com.smartpick.mysp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.PrimaryPurple
import com.smartpick.mysp.ui.theme.SurfaceDark
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onOnboardingComplete: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

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
            )
    ) {
        // Animated background
        OnboardingBackgroundAnimation()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingPage(page = page)
            }

            // Page indicators and buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Page indicators
                PageIndicators(
                    currentPage = pagerState.currentPage,
                    totalPages = 3
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (pagerState.currentPage > 0) {
                        Button(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceDark
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Back",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (pagerState.currentPage < 2) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                onOnboardingComplete()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (pagerState.currentPage == 2) "Get Started" else "Next",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(page: Int) {
    val (icon, title, description) = when (page) {
        0 -> Triple(
            "📱",
            "Personalized Recommendations",
            "Get smart recommendations based on your budget, brand, and favorite features."
        )
        1 -> Triple(
            "📈",
            "Live Price Tracking",
            "Track real-time prices, offers, and discounts on your favorite phones."
        )
        else -> Triple(
            "🤖",
            "AI Chat Support",
            "Ask our Smart Assistant anything — find phones, compare, and track prices easily."
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "page_$page")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "iconScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon with glow effect
        Box(
            modifier = Modifier
                .size(140.dp)
                .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            // Glow background
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                PrimaryBlue.copy(alpha = 0.2f),
                                PrimaryPurple.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
                    .blur(20.dp)
            )

            // Icon container
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                PrimaryBlue.copy(alpha = 0.15f),
                                PrimaryPurple.copy(alpha = 0.15f)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 60.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = description,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun PageIndicators(currentPage: Int, totalPages: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentPage) 32.dp else 8.dp, 8.dp)
                    .background(
                        color = if (index == currentPage) PrimaryBlue else Color.White.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun OnboardingBackgroundAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "onboarding_bg")
    
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bgOffsetX"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.05f)
    ) {
        val width = size.width
        val height = size.height

        // Draw gradient lines
        for (i in 0..8) {
            val x = (width / 8) * i + offsetX
            drawLine(
                color = PrimaryBlue,
                start = Offset(x, 0f),
                end = Offset(x, height),
                strokeWidth = 2f
            )
        }
    }
}
