package com.smartpick.mysp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.Phone
import com.smartpick.mysp.data.PricePoint
import com.smartpick.mysp.data.getSampleBankOffers
import com.smartpick.mysp.ui.components.BankOffersSection
import com.smartpick.mysp.ui.components.PhoneImageViewer
import com.smartpick.mysp.ui.components.PriceHistoryChart
import com.smartpick.mysp.ui.components.PriceVsTimeChart
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.PrimaryPurple
import com.smartpick.mysp.ui.theme.SurfaceDark

@Composable
fun PhoneDetailsScreen(
    phone: Phone,
    onBackClick: () -> Unit,
    onTrackPrice: (Phone, Int) -> Unit = { _, _ -> },
    onAddFavorite: (Phone) -> Unit = {},
    onRemoveFavorite: (String) -> Unit = {},
    isFavorite: Boolean = false,
    onCompare: (Phone) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showPriceDialog by remember { mutableStateOf(false) }
    var isFav by remember { mutableStateOf(isFavorite) }

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
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryBlue
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = phone.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = phone.brand,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
                            tint = PrimaryBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Image Viewer with 360° rotation
            PhoneImageViewer(
                imageUrl = phone.imageUrl,
                phoneName = phone.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Score Badge
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp)
                    .background(Color(0xFF4CAF50), RoundedCornerShape(40.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "4.5",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "/5.0",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Favorite button
                Button(
                    onClick = {
                        try {
                            isFav = !isFav
                            if (isFav) onAddFavorite(phone) else onRemoveFavorite(phone.id)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            isFav = !isFav
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFav) Color(0xFFFF6B6B) else Color.White.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                // Track Price button
                Button(
                    onClick = { showPriceDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("🔔 Track", fontSize = 11.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Price Section
            PriceSection(phone, onCompare)

            Spacer(modifier = Modifier.height(24.dp))

            // Price History
            PriceHistorySection(phone, selectedTab) { selectedTab = it }

            Spacer(modifier = Modifier.height(24.dp))

            // Price vs Time Analysis
            if (phone.priceHistory.isNotEmpty()) {
                PriceVsTimeChart(priceHistory = phone.priceHistory)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Performance Ratings
            PerformanceRatingsSection(phone)

            Spacer(modifier = Modifier.height(24.dp))

            // Bank Offers
            BankOffersSection(bankOffers = getSampleBankOffers())

            Spacer(modifier = Modifier.height(24.dp))

            // Key Specifications
            SpecificationsSection(phone)

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Price Alert Dialog
        if (showPriceDialog) {
            PriceAlertDialog(
                phoneId = phone.id,
                phoneName = phone.name,
                currentPrice = phone.price,
                onDismiss = { showPriceDialog = false },
                onSetAlert = { targetPrice ->
                    onTrackPrice(phone, targetPrice)
                    showPriceDialog = false
                }
            )
        }
    }
}

@Composable
private fun PriceSection(phone: Phone, onCompare: (Phone) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Price",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "₹${phone.price}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
                Text(
                    text = "₹${(phone.price * 1.15).toInt()}",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    textDecoration = TextDecoration.LineThrough
                )
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = "15% OFF",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

    }
}

@Composable
private fun PriceHistorySection(phone: Phone, selectedTab: Int, onTabChange: (Int) -> Unit) {
    // Use phone's price history or generate default if not available
    val priceHistory = if (phone.priceHistory.isNotEmpty()) {
        phone.priceHistory
    } else {
        // Generate default price history based on phone price
        val basePrice = phone.price
        listOf(
            PricePoint(price = basePrice + 5000, timestamp = System.currentTimeMillis() - (90 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 4500, timestamp = System.currentTimeMillis() - (85 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 4000, timestamp = System.currentTimeMillis() - (80 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 3500, timestamp = System.currentTimeMillis() - (75 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 3000, timestamp = System.currentTimeMillis() - (70 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 2500, timestamp = System.currentTimeMillis() - (65 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 2000, timestamp = System.currentTimeMillis() - (60 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 1500, timestamp = System.currentTimeMillis() - (55 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 1000, timestamp = System.currentTimeMillis() - (50 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice + 500, timestamp = System.currentTimeMillis() - (45 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice, timestamp = System.currentTimeMillis() - (40 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice - 500, timestamp = System.currentTimeMillis() - (35 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice - 1000, timestamp = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice - 1500, timestamp = System.currentTimeMillis() - (25 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice - 2000, timestamp = System.currentTimeMillis() - (20 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice - 1500, timestamp = System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice - 1000, timestamp = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice - 500, timestamp = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000)),
            PricePoint(price = basePrice, timestamp = System.currentTimeMillis())
        )
    }

    // Calculate stats
    val lowestPrice = priceHistory.minOfOrNull { it.price } ?: phone.price
    val currentPrice = phone.price
    val savings = currentPrice - lowestPrice

    // Map selected tab to days
    val timePeriods = listOf(7, 30, 90)
    val selectedPeriod = timePeriods[selectedTab]

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("7 Days", "30 Days", "90 Days").forEachIndexed { index, label ->
                Button(
                    onClick = { onTabChange(index) },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == index) PrimaryBlue else Color.White.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(label, fontSize = 11.sp, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Price History Chart with dynamic time period
        PriceHistoryChart(
            priceHistory = priceHistory,
            currentPrice = currentPrice,
            lowestPrice = lowestPrice,
            savings = savings,
            timePeriod = selectedPeriod,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun PriceStatCard(label: String, price: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            Text(
                text = price,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }
    }
}

@Composable
private fun PerformanceRatingsSection(phone: Phone) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Performance Ratings",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        val ratings = listOf(
            "Gaming" to 8.5f,
            "Camera" to 9.0f,
            "Battery" to 8.0f,
            "Display" to 8.5f,
            "Performance" to 9.0f
        )

        ratings.forEach { (label, score) ->
            RatingBar(label, score)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun RatingBar(label: String, score: Float) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.width(80.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(3.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(score / 10f)
                        .background(
                            when {
                                score >= 8.5f -> Color(0xFF4CAF50)
                                score >= 7.5f -> Color(0xFFFFC107)
                                else -> Color(0xFFFF6B6B)
                            },
                            RoundedCornerShape(3.dp)
                        )
                )
            }
            Text(
                text = "${score}/10",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.width(50.dp)
            )
        }
    }
}


@Composable
private fun SpecificationsSection(phone: Phone) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Key Specifications",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        val specs = listOf(
            "📱 Display" to "${phone.display.size}\" ${phone.display.type} @ ${phone.display.refreshRate}Hz",
            "📷 Rear Camera" to phone.camera.rear,
            "🤳 Front Camera" to phone.camera.front,
            "🔋 Battery" to "${phone.battery.capacity}mAh ${phone.battery.type}",
            "⚡ Processor" to phone.processor,
            "💾 RAM" to "${phone.storage.ram}GB",
            "🗂️ Storage" to "${phone.storage.storage}GB",
            "⚙️ Charging" to "${phone.charging.wattage}W"
        )

        specs.forEach { (label, value) ->
            SpecRow(label, value)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
    }
}
