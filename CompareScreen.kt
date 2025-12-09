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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.smartpick.mysp.data.Phone
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark

@Composable
fun CompareScreen(
    phones: List<Phone>,
    onBackClick: () -> Unit,
    onRemovePhone: (String) -> Unit
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
                            text = "Compare Phones",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${phones.size}/3 phones selected",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (phones.isEmpty()) {
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
                            text = "📊",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Phones to Compare",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Select up to 3 phones to compare side by side",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                // Comparison table
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Phone headers
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceDark, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Spec column
                        Box(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Specs",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                        // Phone columns
                        phones.forEach { phone ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        model = phone.imageUrl,
                                        contentDescription = phone.name,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(6.dp)),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = phone.name,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    IconButton(
                                        onClick = { onRemovePhone(phone.id) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Remove",
                                            tint = Color(0xFFFF6B6B),
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Comparison rows
                    val specs = listOf(
                        "Price" to { phone: Phone -> "₹${phone.price}" },
                        "Display" to { phone: Phone -> "${phone.display.size}\" ${phone.display.type}" },
                        "Refresh Rate" to { phone: Phone -> "${phone.display.refreshRate}Hz" },
                        "Processor" to { phone: Phone -> phone.processor },
                        "RAM" to { phone: Phone -> "${phone.storage.ram}GB" },
                        "Storage" to { phone: Phone -> "${phone.storage.storage}GB" },
                        "Rear Camera" to { phone: Phone -> phone.camera.rear },
                        "Front Camera" to { phone: Phone -> phone.camera.front },
                        "Battery" to { phone: Phone -> "${phone.battery.capacity}mAh" },
                        "Charging" to { phone: Phone -> "${phone.charging.wattage}W" },
                        "Rating" to { phone: Phone -> "⭐ ${phone.rating}" }
                    )

                    specs.forEach { (specName, getValue) ->
                        ComparisonRow(
                            specName = specName,
                            phones = phones,
                            getValue = getValue
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ComparisonRow(
    specName: String,
    phones: List<Phone>,
    getValue: (Phone) -> String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Spec name
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = specName,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        // Phone values
        phones.forEach { phone ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = getValue(phone),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue
                )
            }
        }
    }
}
