package com.smartpick.mysp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.AdminAnalytics
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark

@Composable
fun SlideMenu(
    isOpen: Boolean,
    onClose: () -> Unit,
    adminAnalytics: AdminAnalytics,
    onAdminPanelClick: () -> Unit,
    onShowAdminLogin: () -> Unit
) {
    if (isOpen) {
        // Semi-transparent overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = true) { onClose() }
        )
    }

    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it })
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.85f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0F0F1E),
                            Color(0xFF1A0F2E),
                            Color(0xFF0F0F1E)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceDark)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Smart Pick",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryBlue,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = onClose) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = PrimaryBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Analytics Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "📊 Analytics",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Total Users Card
                    AnalyticsCard(
                        icon = "👥",
                        title = "Total Users",
                        value = "${adminAnalytics.totalRegisteredUsers}",
                        color = Color(0xFF4CAF50)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Today's Searches Card
                    AnalyticsCard(
                        icon = "🔍",
                        title = "Today's Searches",
                        value = "${adminAnalytics.todaysSearches}",
                        color = Color(0xFF2196F3)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Recommendations Card
                    AnalyticsCard(
                        icon = "⭐",
                        title = "Recommendations",
                        value = "${adminAnalytics.totalRecommendationsGenerated}",
                        color = Color(0xFFFF9800)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Most Searched Brands
                    Text(
                        text = "🏆 Most Searched Brands",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    adminAnalytics.mostSearchedBrands.forEach { brand ->
                        BrandSearchItem(
                            brandName = brand.brandName,
                            searchCount = brand.searchCount,
                            percentage = brand.percentage
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Admin Panel Button
                    Button(
                        onClick = {
                            onClose() // Close menu first
                            onShowAdminLogin() // Show login dialog
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "🔐 Admin Panel",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun AnalyticsCard(
    icon: String,
    title: String,
    value: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 20.sp)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
        }
    }
}

@Composable
private fun BrandSearchItem(
    brandName: String,
    searchCount: Int,
    percentage: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = brandName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$searchCount",
                    fontSize = 12.sp,
                    color = PrimaryBlue,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentage / 100f)
                        .fillMaxHeight()
                        .background(PrimaryBlue, RoundedCornerShape(2.dp))
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${"%.1f".format(percentage)}%",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}
