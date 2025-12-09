package com.smartpick.mysp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
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
import com.smartpick.mysp.data.FavoritePhone
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark

@Composable
fun FavoritesScreen(
    favorites: List<FavoritePhone>,
    onBackClick: () -> Unit,
    onRemoveFavorite: (String) -> Unit,
    onPhoneSelected: (com.smartpick.mysp.data.Phone) -> Unit = {}
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
                            text = "Favorites",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${favorites.size} phones saved",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (favorites.isEmpty()) {
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
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color(0xFFFF6B6B),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Favorites Yet",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Add phones to your favorites to see them here",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                // Favorites grid
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    favorites.forEach { favorite ->
                        FavoritePhoneCard(
                            favorite = favorite,
                            onRemove = { onRemoveFavorite(favorite.phoneId) },
                            onSelect = { onPhoneSelected(favorite.phone) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FavoritePhoneCard(
    favorite: FavoritePhone,
    onRemove: () -> Unit,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Phone image
            AsyncImage(
                model = favorite.phone.imageUrl,
                contentDescription = favorite.phone.name,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )

            // Phone details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = favorite.phone.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = favorite.phone.brand,
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹${favorite.phone.price}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "⭐ ${favorite.phone.rating}",
                        fontSize = 10.sp,
                        color = Color(0xFFFFC107)
                    )
                    Text(
                        text = "• ${favorite.phone.storage.ram}GB RAM",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            // Remove button
            IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color(0xFFFF6B6B),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
