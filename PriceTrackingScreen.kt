package com.smartpick.mysp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.PriceTrack
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark

@Composable
fun PriceTrackingScreen(
    trackedPhones: List<PriceTrack>,
    onBackClick: () -> Unit,
    onRemoveTrack: (String) -> Unit
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
                            text = "Price Tracking",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${trackedPhones.size} phones tracked",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (trackedPhones.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔔",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Phones Tracked",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Start tracking phone prices to get alerts",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                // Tracked phones list
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    androidx.compose.foundation.lazy.LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(trackedPhones.size) { index ->
                            val track = trackedPhones[index]
                            PriceTrackCard(
                                track = track,
                                onRemove = { onRemoveTrack(track.phoneId) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PriceTrackCard(
    track: PriceTrack,
    onRemove: () -> Unit
) {
    val priceDifference = track.currentPrice - track.targetPrice
    val isBelowTarget = priceDifference <= 0
    val savingsPercentage = ((track.currentPrice - track.targetPrice).toFloat() / track.currentPrice * 100).toInt()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.phone.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = track.phone.brand,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
                IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = Color(0xFFFF6B6B),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Price comparison
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Current",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "₹${track.currentPrice}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Target",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "₹${track.targetPrice}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isBelowTarget) "Savings" else "Difference",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "₹${kotlin.math.abs(priceDifference)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isBelowTarget) Color(0xFF4CAF50) else Color(0xFFFF9800)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isBelowTarget) Color(0xFF4CAF50).copy(alpha = 0.2f) else Color(0xFFFF9800).copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = if (isBelowTarget) "✓ Price target reached! Save ₹${kotlin.math.abs(priceDifference)}" else "⏳ Waiting for price drop",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isBelowTarget) Color(0xFF4CAF50) else Color(0xFFFF9800)
                )
            }
        }
    }
}

@Composable
fun PriceAlertDialog(
    phoneId: String,
    phoneName: String,
    currentPrice: Int,
    onDismiss: () -> Unit,
    onSetAlert: (Int) -> Unit
) {
    var targetPrice by remember { mutableStateOf(currentPrice.toString()) }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Set Target Price",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = phoneName,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Current Price: ₹$currentPrice",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                // Text input field
                TextField(
                    value = targetPrice,
                    onValueChange = { newValue ->
                        targetPrice = newValue
                        showError = false
                    },
                    placeholder = {
                        Text(
                            text = "Enter target price",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                        focusedIndicatorColor = PrimaryBlue,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = Color.White
                    )
                )
                
                if (showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Please enter a valid price",
                        fontSize = 11.sp,
                        color = Color(0xFFFF6B6B)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val price = targetPrice.toIntOrNull()
                    if (price != null && price > 0) {
                        onSetAlert(price)
                        onDismiss()
                    } else {
                        showError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Set Alert", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Text("Cancel", color = Color.White)
            }
        },
        containerColor = SurfaceDark,
        shape = RoundedCornerShape(12.dp)
    )
}
