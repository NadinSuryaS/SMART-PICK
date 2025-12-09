package com.smartpick.mysp.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class FestivalBanner(
    val id: Int,
    val title: String,
    val discount: String,
    val emoji: String,
    val gradientStart: Color,
    val gradientEnd: Color,
    val textColor: Color
)

val festivalBanners = listOf(
    FestivalBanner(
        id = 1,
        title = "Diwali Sale",
        discount = "🔥 Mega Sale Week – Up to 40% OFF",
        emoji = "🪔",
        gradientStart = Color(0xFFFF6B35),
        gradientEnd = Color(0xFFFFD700),
        textColor = Color.White
    ),
    FestivalBanner(
        id = 2,
        title = "New Year Sale",
        discount = "🎉 New Year Bonanza – Up to 50% OFF",
        emoji = "🎆",
        gradientStart = Color(0xFF6B5FFF),
        gradientEnd = Color(0xFF00D4FF),
        textColor = Color.White
    ),
    FestivalBanner(
        id = 3,
        title = "Republic Day Sale",
        discount = "🇮🇳 Patriotic Deals – Up to 35% OFF",
        emoji = "🏳️",
        gradientStart = Color(0xFF1E3A8A),
        gradientEnd = Color(0xFFFF6B35),
        textColor = Color.White
    ),
    FestivalBanner(
        id = 4,
        title = "Big Billion Days",
        discount = "💰 Biggest Sale Ever – Up to 60% OFF",
        emoji = "💎",
        gradientStart = Color(0xFFFF1493),
        gradientEnd = Color(0xFFFFD700),
        textColor = Color.White
    )
)

@Composable
fun FestivalBannerCarousel(modifier: Modifier = Modifier) {
    var currentPage by remember { mutableStateOf(0) }

    // Auto-scroll effect with proper state management
    LaunchedEffect(key1 = Unit) {
        while (true) {
            try {
                delay(3500) // 3.5 seconds
                currentPage = (currentPage + 1) % festivalBanners.size
            } catch (e: Exception) {
                break
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Banner carousel - using Box instead of LazyRow for stability
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp)
        ) {
            FestivalBannerItem(
                banner = festivalBanners[currentPage],
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dots indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(festivalBanners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == currentPage) 10.dp else 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (index == currentPage)
                                Color(0xFF2196F3)
                            else
                                Color.Gray.copy(alpha = 0.5f)
                        )
                        .clickable {
                            currentPage = index
                        }
                )
                if (index < festivalBanners.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun FestivalBannerItem(
    banner: FestivalBanner,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(banner.gradientStart, banner.gradientEnd)
                )
            )
            .padding(16.dp)
    ) {
        // Content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            // Top section with emoji and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = banner.emoji,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(
                        text = banner.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = banner.textColor
                    )
                }
                // Decorative fireworks on right
                Text(
                    text = "✨",
                    fontSize = 24.sp
                )
            }

            // Bottom section with discount text
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = banner.discount,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = banner.textColor,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                // Shopping icon
                Text(
                    text = "🛍️ Shop Now",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = banner.textColor.copy(alpha = 0.9f)
                )
            }
        }

        // Corner decorations
        Text(
            text = "🎁",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
        Text(
            text = "⭐",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        )
    }
}
