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
import com.smartpick.mysp.data.*
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.PrimaryPurple
import com.smartpick.mysp.ui.theme.SurfaceDark
import kotlinx.coroutines.delay

@Composable
fun RecommendationScreen(
    onBackClick: () -> Unit,
    onResultsReady: (RecommendationResult) -> Unit,
    onAddFavorite: (Phone) -> Unit = {},
    onRemoveFavorite: (String) -> Unit = {},
    onTrackPrice: (Phone, Int) -> Unit = { _, _ -> },
    onCompare: (Phone) -> Unit = {}
) {
    var budgetMin by remember { mutableStateOf(2000) }
    var budgetMax by remember { mutableStateOf(200000) }
    var selectedBrands by remember { mutableStateOf(setOf<String>()) }
    var selectedPriority by remember { mutableStateOf(Priority.CAMERA) }
    var selectedFeatures by remember { mutableStateOf(setOf<String>()) }
    var showResults by remember { mutableStateOf(false) }
    var recommendations by remember { mutableStateOf<RecommendationResult?>(null) }
    var selectedPhone by remember { mutableStateOf<Phone?>(null) }

    if (selectedPhone != null) {
        PhoneDetailsScreen(
            phone = selectedPhone!!,
            onBackClick = { selectedPhone = null },
            onTrackPrice = { phone, targetPrice ->
                onTrackPrice(phone, targetPrice)
            },
            onAddFavorite = { phone ->
                onAddFavorite(phone)
            },
            onRemoveFavorite = { phoneId ->
                onRemoveFavorite(phoneId)
            },
            isFavorite = false,
            onCompare = { phone ->
                onCompare(phone)
            }
        )
    } else if (showResults && recommendations != null) {
        ResultsScreen(
            recommendations = recommendations!!,
            onBackClick = { showResults = false },
            onPhoneSelected = { selectedPhone = it },
            onTrackPrice = { phone ->
                onTrackPrice(phone, phone.price)
            },
            onCompare = { phone ->
                onCompare(phone)
            }
        )
    } else {
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
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryBlue
                        )
                    }
                    Text(
                        text = "Find Your Phone",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Budget Section
                BudgetSection(
                    budgetMin = budgetMin,
                    budgetMax = budgetMax,
                    onBudgetMinChange = { budgetMin = it },
                    onBudgetMaxChange = { budgetMax = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Brand Section
                BrandSection(
                    selectedBrands = selectedBrands,
                    onBrandToggle = { brand ->
                        selectedBrands = if (selectedBrands.contains(brand)) {
                            selectedBrands - brand
                        } else {
                            selectedBrands + brand
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Priority Section
                PrioritySection(
                    selectedPriority = selectedPriority,
                    onPriorityChange = { selectedPriority = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Features Section
                FeaturesSection(
                    selectedFeatures = selectedFeatures,
                    onFeatureToggle = { feature ->
                        selectedFeatures = if (selectedFeatures.contains(feature)) {
                            selectedFeatures - feature
                        } else {
                            selectedFeatures + feature
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Get Recommendations Button
                Button(
                    onClick = {
                        val prefs = UserPreferences(
                            budgetMin = budgetMin,
                            budgetMax = budgetMax,
                            selectedBrands = selectedBrands.toList(),
                            priority = selectedPriority,
                            selectedFeatures = selectedFeatures.toList()
                        )
                        recommendations = RecommendationEngine.getRecommendations(prefs)
                        showResults = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Get Recommendations",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun BudgetSection(
    budgetMin: Int,
    budgetMax: Int,
    onBudgetMinChange: (Int) -> Unit,
    onBudgetMaxChange: (Int) -> Unit
) {
    Column {
        Text(
            text = "Budget Range",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "₹${budgetMin/1000}K - ₹${budgetMax/1000}K",
            fontSize = 14.sp,
            color = PrimaryBlue,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Quick select buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickBudgetButton("10K", 10000, { onBudgetMaxChange(10000) }, Modifier.weight(1f))
            QuickBudgetButton("30K", 30000, { onBudgetMaxChange(30000) }, Modifier.weight(1f))
            QuickBudgetButton("50K", 50000, { onBudgetMaxChange(50000) }, Modifier.weight(1f))
            QuickBudgetButton("1L+", 200000, { onBudgetMaxChange(200000) }, Modifier.weight(1f))
        }
    }
}

@Composable
private fun QuickBudgetButton(label: String, value: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(label, fontSize = 12.sp, color = Color.White)
    }
}

@Composable
private fun BrandSection(
    selectedBrands: Set<String>,
    onBrandToggle: (String) -> Unit
) {
    Column {
        Text(
            text = "Select Brands",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        val brands = listOf(
            "Samsung", "Apple", "Xiaomi", "Realme", "Oppo", "Vivo", "OnePlus",
            "Google Pixel", "Motorola", "Nokia", "Nothing", "iQOO", "Tecno", "Infinix",
            "Asus", "Sony", "Huawei", "Honor", "Lenovo", "ZTE", "Meizu", "Nubia",
            "Black Shark", "RedMagic", "Poco"
        )
        
        brands.chunked(2).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { brand ->
                    BrandChip(
                        brand = brand,
                        isSelected = selectedBrands.contains(brand),
                        onToggle = { onBrandToggle(brand) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun BrandChip(
    brand: String,
    isSelected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onToggle,
        modifier = modifier.height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PrimaryBlue else SurfaceDark
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(brand, fontSize = 12.sp, color = Color.White)
    }
}

@Composable
private fun PrioritySection(
    selectedPriority: Priority,
    onPriorityChange: (Priority) -> Unit
) {
    Column {
        Text(
            text = "What Matters Most?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        Priority.values().forEach { priority ->
            PriorityOption(
                priority = priority,
                isSelected = selectedPriority == priority,
                onSelect = { onPriorityChange(priority) }
            )
        }
    }
}

@Composable
private fun PriorityOption(
    priority: Priority,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Button(
        onClick = onSelect,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PrimaryBlue else SurfaceDark
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = priority.name.replace("_", " "),
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun FeaturesSection(
    selectedFeatures: Set<String>,
    onFeatureToggle: (String) -> Unit
) {
    Column {
        Text(
            text = "Select Features",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        val features = listOf(
            "5G", "AMOLED", "Fast Charging", "Wireless Charging", "NFC",
            "Water Resistant", "Stereo Speakers", "High Refresh Rate",
            "Big Battery", "Slim Design", "Foldable", "Stylus Support",
            "Large Storage"
        )
        
        features.chunked(2).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { feature ->
                    FeatureChip(
                        feature = feature,
                        isSelected = selectedFeatures.contains(feature),
                        onToggle = { onFeatureToggle(feature) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FeatureChip(
    feature: String,
    isSelected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onToggle,
        modifier = modifier.height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PrimaryPurple else SurfaceDark
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(feature, fontSize = 11.sp, color = Color.White)
    }
}

@Composable
private fun ResultsScreen(
    recommendations: RecommendationResult,
    onBackClick: () -> Unit,
    onPhoneSelected: (Phone) -> Unit = {},
    onTrackPrice: (Phone) -> Unit = {},
    onCompare: (Phone) -> Unit = {}
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
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryBlue
                    )
                }
                Text(
                    text = "Recommendations",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            // Summary
            Text(
                text = recommendations.summary,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Top Matches
            Text(
                text = "Top Matches",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))

            recommendations.topMatches.forEachIndexed { index, phoneWithScore ->
                PhoneCard(
                    phoneWithScore = phoneWithScore,
                    rank = index + 1,
                    onViewDetails = { onPhoneSelected(it) },
                    onTrackPrice = { onTrackPrice(it) },
                    onCompare = { onCompare(it) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Best Overall
            Text(
                text = "Best Overall Choice",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            PhoneCard(phoneWithScore = recommendations.bestOverall, onViewDetails = { onPhoneSelected(it) }, onTrackPrice = { onTrackPrice(it) }, onCompare = { onCompare(it) })

            Spacer(modifier = Modifier.height(24.dp))

            // Best Value
            Text(
                text = "Best Value for Money",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple
            )
            Spacer(modifier = Modifier.height(12.dp))
            PhoneCard(phoneWithScore = recommendations.bestValue, onViewDetails = { onPhoneSelected(it) }, onTrackPrice = { onTrackPrice(it) }, onCompare = { onCompare(it) })

            // Best Premium
            if (recommendations.bestPremium != null) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Best Premium Option",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.height(12.dp))
                PhoneCard(phoneWithScore = recommendations.bestPremium!!, onViewDetails = { onPhoneSelected(it) }, onTrackPrice = { onTrackPrice(it) }, onCompare = { onCompare(it) })
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PhoneCard(
    phoneWithScore: PhoneWithScore,
    rank: Int = 0,
    onViewDetails: (Phone) -> Unit = {},
    onTrackPrice: (Phone) -> Unit = {},
    onCompare: (Phone) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            // Header with rank and score
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (rank > 0) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(PrimaryBlue, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "#$rank",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = phoneWithScore.phone.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = phoneWithScore.phone.brand,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "₹${phoneWithScore.phone.price}",
                        fontSize = 14.sp,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Text(
                    text = "${phoneWithScore.matchScore.toInt()}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Highlight
            Text(
                text = phoneWithScore.matchReason,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Key specs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SpecBadge("${phoneWithScore.phone.display.size}\" Display")
                SpecBadge("${phoneWithScore.phone.battery.capacity}mAh")
                SpecBadge("${phoneWithScore.phone.storage.ram}GB RAM")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = { onTrackPrice(phoneWithScore.phone) },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) {
                    Text("🔔 Track", fontSize = 10.sp, color = Color.White)
                }
                Button(
                    onClick = { onCompare(phoneWithScore.phone) },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Compare", fontSize = 10.sp, color = Color.White)
                }
                Button(
                    onClick = { onViewDetails(phoneWithScore.phone) },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Details", fontSize = 10.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun SpecBadge(text: String) {
    Box(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}
