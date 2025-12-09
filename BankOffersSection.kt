package com.smartpick.mysp.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.BankOffer
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark

@Composable
fun BankOffersSection(bankOffers: List<BankOffer>) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Bank Offers",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Bank Offers Grid
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            bankOffers.forEach { offer ->
                BankOfferCard(
                    offer = offer,
                    onClick = {
                        // Open bank URL in browser
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.bankUrl))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Handle error silently
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BankOfferCard(
    offer: BankOffer,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(alpha = 0.05f),
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bank Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = offer.bankName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = offer.discount,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )

                    Text(
                        text = offer.offerDetails,
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            // Open Icon
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Open ${offer.bankName}",
                tint = PrimaryBlue,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
