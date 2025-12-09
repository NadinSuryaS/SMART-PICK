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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.getSampleAdminAnalytics
import com.smartpick.mysp.ui.components.AdminLoginDialog
import com.smartpick.mysp.ui.components.FestivalBannerCarousel
import com.smartpick.mysp.ui.screens.AdminPanelScreen
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.PrimaryPurple
import com.smartpick.mysp.ui.theme.SurfaceDark

enum class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("Home", Icons.Default.Home, "home"),
    FAVORITES("Favorites", Icons.Default.Favorite, "favorites"),
    TRACK("Track", Icons.Default.Settings, "track"),
    OFFERS("Offers", Icons.Default.ShoppingCart, "offers"),
    CHAT("Chat", Icons.Default.Info, "chat")
}

@Composable
fun MainHomeScreen(
    onRecommendationClick: () -> Unit,
    onLogout: () -> Unit,
    favorites: List<com.smartpick.mysp.data.FavoritePhone> = emptyList(),
    trackedPhones: List<com.smartpick.mysp.data.PriceTrack> = emptyList(),
    comparePhones: List<com.smartpick.mysp.data.Phone> = emptyList(),
    onRemoveFavorite: (String) -> Unit = {},
    onRemoveTrack: (String) -> Unit = {},
    onCompare: (com.smartpick.mysp.data.Phone) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(BottomNavItem.HOME) }
    var showAdminLoginDialog by remember { mutableStateOf(false) }
    var showAdminPanel by remember { mutableStateOf(false) }
    val adminAnalytics = getSampleAdminAnalytics()

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
            // Header with Hamburger Menu
            if (selectedTab == BottomNavItem.HOME) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceDark)
                        .padding(12.dp)
                ) {
                    IconButton(
                        onClick = { showAdminLoginDialog = true },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Admin Login",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            // Content Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (selectedTab) {
                    BottomNavItem.HOME -> HomeTabContent(onRecommendationClick, comparePhones)
                    BottomNavItem.FAVORITES -> FavoritesTabContent(favorites, onRemoveFavorite)
                    BottomNavItem.TRACK -> TrackTabContent(trackedPhones, onRemoveTrack)
                    BottomNavItem.OFFERS -> OffersTabContent()
                    BottomNavItem.CHAT -> ChatTabContent()
                }
            }

            // Bottom Navigation
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }

        // Admin Login Dialog - Shows when hamburger menu is tapped
        if (showAdminLoginDialog) {
            AdminLoginDialog(
                onLoginSuccess = {
                    showAdminLoginDialog = false
                    showAdminPanel = true
                },
                onCancel = {
                    showAdminLoginDialog = false
                }
            )
        }

        // Admin Panel - Only visible after successful authentication
        if (showAdminPanel) {
            AdminPanelScreen(
                adminAnalytics = adminAnalytics,
                onBackClick = {
                    showAdminPanel = false
                }
            )
        }
    }
}

@Composable
private fun HomeTabContent(onRecommendationClick: () -> Unit, comparePhones: List<com.smartpick.mysp.data.Phone>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Greeting
        Text(
            text = "Find your perfect",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "smartphone.",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subtitle
        Text(
            text = "Discover phones tailored to your needs",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Festival Banner Carousel
        FestivalBannerCarousel(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Recommendation Button
        Button(
            onClick = onRecommendationClick,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Start Recommendation",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Compare Section
        if (comparePhones.isNotEmpty()) {
            Text(
                text = "📊 Compare Phones (${comparePhones.size}/3)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            
            androidx.compose.foundation.lazy.LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(comparePhones.size) { index ->
                    val phone = comparePhones[index]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceDark, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = phone.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = phone.brand,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "₹${phone.price}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryBlue
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Featured Section - Trending Phones
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            PrimaryBlue.copy(alpha = 0.1f),
                            PrimaryPurple.copy(alpha = 0.1f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🔥",
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Trending Phones",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "Check out the latest releases",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Best Value Phones Card
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1A0F2E),
                            Color(0xFF2D1B4E)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "⭐",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Best Value Phones",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Check out the best phones under your budget",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )
                }
                
                // Arrow Icon
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "View Best Value Phones",
                    tint = PrimaryBlue,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(180f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Smart Pick Assistant Chat Card
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF2D1B4E),
                            Color(0xFF1A0F2E)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "🤖",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Smart Pick Assistant",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Get AI-powered phone recommendations",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )
                }
                
                // Arrow Icon
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Open Chat",
                    tint = PrimaryBlue,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(180f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun FavoritesTabContent(
    favorites: List<com.smartpick.mysp.data.FavoritePhone>,
    onRemoveFavorite: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Header
        Text(
            text = "❤️ Your Favorites",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        
        if (favorites.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No favorites yet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Start adding phones to your favorites!",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    count = favorites.size,
                    key = { index -> 
                        if (index < favorites.size) favorites[index].phoneId else "unknown_$index"
                    }
                ) { index ->
                    if (index >= favorites.size) return@items
                    val favorite = favorites[index]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceDark, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Header Row: Product name and delete button
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = favorite.phone.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = favorite.phone.brand,
                                        fontSize = 13.sp,
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                
                                // Delete button - perfectly aligned
                                IconButton(
                                    onClick = { onRemoveFavorite(favorite.phoneId) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(0.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = Color(0xFFFF6B6B),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            
                            // Divider
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.White.copy(alpha = 0.1f))
                                    .padding(vertical = 8.dp)
                            )
                            
                            // Price section - prominent display
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Price display
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            Color.White.copy(alpha = 0.05f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Price",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "₹${favorite.phone.price}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue
                                    )
                                }
                                
                                // Rating
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            Color.White.copy(alpha = 0.05f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Rating",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "⭐ ${favorite.phone.rating}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFC107)
                                    )
                                }
                            }
                            
                            // Specs row
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color.White.copy(alpha = 0.05f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "${favorite.phone.storage.ram}GB RAM",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "•",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.3f)
                                )
                                Text(
                                    text = "${favorite.phone.storage.storage}GB Storage",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TrackTabContent(
    trackedPhones: List<com.smartpick.mysp.data.PriceTrack>,
    onRemoveTrack: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Header
        Text(
            text = "📊 Price Tracking",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        
        if (trackedPhones.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No tracked phones yet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Start tracking prices on your favorite phones!",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    count = trackedPhones.size,
                    key = { index -> 
                        if (index < trackedPhones.size) trackedPhones[index].phoneId else "unknown_$index"
                    }
                ) { index ->
                    if (index >= trackedPhones.size) return@items
                    val track = trackedPhones[index]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceDark, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Header Row: Product name and delete button
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
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
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = track.phone.brand,
                                        fontSize = 13.sp,
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                
                                // Delete button - perfectly aligned
                                IconButton(
                                    onClick = { onRemoveTrack(track.phoneId) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(0.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = Color(0xFFFF6B6B),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            
                            // Divider
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.White.copy(alpha = 0.1f))
                                    .padding(vertical = 8.dp)
                            )
                            
                            // Price comparison section
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Current Price
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            Color.White.copy(alpha = 0.05f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Current",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "₹${track.currentPrice}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue
                                    )
                                }
                                
                                // Target Price
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            Color.White.copy(alpha = 0.05f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Target",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "₹${track.targetPrice}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF4CAF50)
                                    )
                                }
                                
                                // Difference
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            Color.White.copy(alpha = 0.05f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Difference",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    val diff = track.currentPrice - track.targetPrice
                                    Text(
                                        text = "₹${kotlin.math.abs(diff)}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (diff <= 0) Color(0xFF4CAF50) else Color(0xFFFF9800)
                                    )
                                }
                            }
                            
                            // Status badge
                            Spacer(modifier = Modifier.height(12.dp))
                            val diff = track.currentPrice - track.targetPrice
                            val isBelowTarget = diff <= 0
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (isBelowTarget) Color(0xFF4CAF50).copy(alpha = 0.2f) else Color(0xFFFF9800).copy(alpha = 0.2f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isBelowTarget) "✓ Target reached! Save ₹${kotlin.math.abs(diff)}" else "⏳ Waiting for price drop",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isBelowTarget) Color(0xFF4CAF50) else Color(0xFFFF9800)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OffersTabContent() {
    var selectedBrand by remember { mutableStateOf<String?>(null) }
    
    if (selectedBrand != null) {
        com.smartpick.mysp.ui.components.BrandOffersScreen(
            brand = selectedBrand!!,
            onBackClick = { selectedBrand = null },
            onViewOffer = { url ->
                // URL is already opened in BrandOffersScreen via openUrl helper
            },
            onViewTerms = { url ->
                // URL is already opened in BrandOffersScreen via openUrl helper
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Header
            Text(
                text = "💳 Bank Offers",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Text(
                text = "Get exclusive bank and card offers on premium smartphones",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Brand Selector
            com.smartpick.mysp.ui.components.BrandSelectorForOffers(
                onBrandSelected = { brand ->
                    selectedBrand = brand
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Info Cards
            InfoCard(
                icon = "🏦",
                title = "7 Premium Banks",
                description = "HDFC, ICICI, SBI, Axis, Kotak, AU, Yes Bank"
            )
            
            InfoCard(
                icon = "💰",
                title = "Up to ₹5,000 Discount",
                description = "Instant discounts, cashback & EMI offers"
            )
            
            InfoCard(
                icon = "📅",
                title = "Live & Upcoming Offers",
                description = "Track active and upcoming bank offers"
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun InfoCard(
    icon: String,
    title: String,
    description: String
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
            Text(
                text = icon,
                fontSize = 32.sp
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun ChatTabContent() {
    ChatScreen(
        onBackClick = {}
    )
}

@Composable
private fun BottomNavigationBar(
    selectedTab: BottomNavItem,
    onTabSelected: (BottomNavItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        containerColor = SurfaceDark,
        contentColor = PrimaryBlue
    ) {
        BottomNavItem.values().forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                selected = selectedTab == item,
                onClick = { onTabSelected(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryBlue,
                    selectedTextColor = PrimaryBlue,
                    indicatorColor = PrimaryBlue.copy(alpha = 0.1f),
                    unselectedIconColor = Color.White.copy(alpha = 0.5f),
                    unselectedTextColor = Color.White.copy(alpha = 0.5f)
                )
            )
        }
    }
}
