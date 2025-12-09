package com.smartpick.mysp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun AdminPanelScreen(
    adminAnalytics: AdminAnalytics,
    onBackClick: () -> Unit
) {
    // User is already authenticated via AdminLoginDialog in MainHomeScreen
    // Just show the dashboard directly
    AdminDashboard(
        adminAnalytics = adminAnalytics,
        onBackClick = onBackClick
    )
}

@Composable
private fun AdminDashboard(
    adminAnalytics: AdminAnalytics,
    onBackClick: () -> Unit
) {
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
        Column(
            modifier = Modifier.fillMaxSize()
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
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryBlue
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Admin Dashboard",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "System Analytics & Insights",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Key Metrics
                Text(
                    text = "📊 Key Metrics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                MetricCard(
                    title = "Total Registered Users",
                    value = "${adminAnalytics.totalRegisteredUsers}",
                    icon = "👥",
                    color = Color(0xFF4CAF50),
                    trend = "+12.5%"
                )

                MetricCard(
                    title = "Today's Searches",
                    value = "${adminAnalytics.todaysSearches}",
                    icon = "🔍",
                    color = Color(0xFF2196F3),
                    trend = "+8.2%"
                )

                MetricCard(
                    title = "Total Recommendations",
                    value = "${adminAnalytics.totalRecommendationsGenerated}",
                    icon = "⭐",
                    color = Color(0xFFFF9800),
                    trend = "+15.3%"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Brand Analytics
                Text(
                    text = "🏆 Brand Search Analytics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                adminAnalytics.mostSearchedBrands.forEach { brand ->
                    BrandAnalyticsCard(
                        brandName = brand.brandName,
                        searchCount = brand.searchCount,
                        percentage = brand.percentage
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    icon: String,
    color: Color,
    trend: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 24.sp)
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
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .background(color.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = trend,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
        }
    }
}

@Composable
private fun BrandAnalyticsCard(
    brandName: String,
    searchCount: Int,
    percentage: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = brandName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$searchCount searches",
                    fontSize = 12.sp,
                    color = PrimaryBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(3.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentage / 100f)
                        .fillMaxHeight()
                        .background(PrimaryBlue, RoundedCornerShape(3.dp))
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${"%.1f".format(percentage)}% of total searches",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}
