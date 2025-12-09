package com.smartpick.mysp.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.*
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark
import java.time.format.DateTimeFormatter

@Composable
fun BrandOffersScreen(
    brand: String,
    onBackClick: () -> Unit = {},
    onViewOffer: (String) -> Unit = {},
    onViewTerms: (String) -> Unit = {}
) {
    val offers = BrandOffersEngine.getOffersForBrand(brand)
    val context = LocalContext.current
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                androidx.compose.ui.graphics.Brush.linearGradient(
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
                    Button(
                        onClick = onBackClick,
                        modifier = Modifier.size(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("←", fontSize = 24.sp, color = PrimaryBlue)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "💳 $brand Offers",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Bank & Card Offers",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Content
            if (offers.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "⚠️",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No active or upcoming bank offers available for this brand.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(offers) { offer ->
                        BankOfferCard(
                            offer = offer,
                            onViewOffer = { 
                                openUrl(context, offer.bankWebsite)
                                onViewOffer(offer.bankWebsite)
                            },
                            onViewTerms = { 
                                openUrl(context, offer.bankWebsite)
                                onViewTerms(offer.bankWebsite)
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BankOfferCard(
    offer: BrandBankOffer,
    onViewOffer: () -> Unit = {},
    onViewTerms: () -> Unit = {}
) {
    val statusColor = when (offer.offerStatus) {
        OfferStatus.LIVE -> Color(0xFF4CAF50)
        OfferStatus.UPCOMING -> Color(0xFFFFC107)
        OfferStatus.EXPIRED -> Color(0xFFFF6B6B)
    }
    
    val statusBackgroundColor = when (offer.offerStatus) {
        OfferStatus.LIVE -> Color(0xFF4CAF50).copy(alpha = 0.15f)
        OfferStatus.UPCOMING -> Color(0xFFFFC107).copy(alpha = 0.15f)
        OfferStatus.EXPIRED -> Color(0xFFFF6B6B).copy(alpha = 0.15f)
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Phone Model and Price
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = offer.phoneModel,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹${offer.marketPrice}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bank and Status Badge
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Bank Badge
            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "🏦 ${offer.bankName.getDisplayName()}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            // Status Badge
            Box(
                modifier = Modifier
                    .background(statusBackgroundColor, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = offer.offerStatus.getDisplayName(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Divider
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White.copy(alpha = 0.1f))
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Offer Details
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Discount Amount
            OfferDetailRow(
                icon = "🔖",
                label = "Discount",
                value = "₹${offer.discountAmount} ${offer.offerType.getDisplayName()}"
            )

            // Minimum Purchase
            OfferDetailRow(
                icon = "💳",
                label = "Minimum Purchase",
                value = "₹${offer.minimumTransactionValue}"
            )

            // Card Type
            OfferDetailRow(
                icon = "🎫",
                label = "Card Type",
                value = offer.cardType.getDisplayName()
            )

            // No-Cost EMI
            OfferDetailRow(
                icon = "📅",
                label = "No-Cost EMI",
                value = if (offer.noCostEMI) "Yes" else "No"
            )

            // Valid Until
            OfferDetailRow(
                icon = "⏰",
                label = "Valid Until",
                value = offer.validUntil.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            )

            // Status
            OfferDetailRow(
                icon = "✓",
                label = "Status",
                value = offer.offerStatus.getBadgeText()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onViewOffer,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("View Offer", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onViewTerms,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Terms", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun OfferDetailRow(
    icon: String,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = icon,
                fontSize = 14.sp
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun BrandSelectorForOffers(
    onBrandSelected: (String) -> Unit
) {
    val brands = listOf("Apple", "Samsung", "OnePlus", "Xiaomi", "Vivo", "Realme", "Oppo", "iQOO", "Motorola")
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "💳 Select Brand for Offers",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            brands.chunked(3).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { brand ->
                        Button(
                            onClick = { onBrandSelected(brand) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue.copy(alpha = 0.2f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = brand,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    // Spacer for incomplete rows
                    repeat(3 - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// Helper function to open URLs
fun openUrl(context: android.content.Context, url: String) {
    try {
        // Ensure URL has proper scheme
        val urlToOpen = if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }
        
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlToOpen))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    } catch (e: Exception) {
        // Silently fail if no browser is available
        android.util.Log.e("BrandOffers", "Failed to open URL: $url", e)
    }
}
