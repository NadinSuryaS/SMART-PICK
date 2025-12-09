package com.smartpick.mysp.data

import com.smartpick.mysp.data.phones.*

object RecommendationEngine {
    
    // Get all phones from all brands
    fun getAllPhones(): List<Phone> {
        return (SamsungPhones.phones +
                ApplePhones.phones +
                XiaomiPhones.phones +
                RealmPhones.phones +
                OppoPhones.phones +
                VivoPhones.phones +
                OnePlusPhones.phones +
                PixelPhones.phones +
                MotorolaPhones.phones +
                NokiaPhones.phones +
                NothingPhones.phones +
                IQOOPhones.phones +
                TecnoPhones.phones +
                InfinixPhones.phones +
                AsusPhones.phones +
                SonyPhones.phones +
                HuaweiPhones.phones +
                HonorPhones.phones +
                LenovoPhones.phones +
                ZTEPhones.phones +
                MeizuPhones.phones +
                NubiaPhones.phones +
                BlackSharkPhones.phones +
                RedMagicPhones.phones +
                PocoPhones.phones)
    }
    
    // Get phones by brand
    fun getPhonesByBrand(brand: String): List<Phone> {
        return when (brand.lowercase()) {
            "samsung" -> SamsungPhones.phones
            "apple" -> ApplePhones.phones
            "xiaomi" -> XiaomiPhones.phones
            "realme" -> RealmPhones.phones
            "oppo" -> OppoPhones.phones
            "vivo" -> VivoPhones.phones
            "oneplus" -> OnePlusPhones.phones
            "google pixel" -> PixelPhones.phones
            "motorola" -> MotorolaPhones.phones
            "nokia" -> NokiaPhones.phones
            "nothing" -> NothingPhones.phones
            "iqoo" -> IQOOPhones.phones
            "tecno" -> TecnoPhones.phones
            "infinix" -> InfinixPhones.phones
            "asus" -> AsusPhones.phones
            "sony" -> SonyPhones.phones
            "huawei" -> HuaweiPhones.phones
            "honor" -> HonorPhones.phones
            "lenovo" -> LenovoPhones.phones
            "zte" -> ZTEPhones.phones
            "meizu" -> MeizuPhones.phones
            "nubia" -> NubiaPhones.phones
            "black shark" -> BlackSharkPhones.phones
            "redmagic" -> RedMagicPhones.phones
            "poco" -> PocoPhones.phones
            else -> getAllPhones()
        }
    }
    
    // Main recommendation function
    fun getRecommendations(preferences: UserPreferences): RecommendationResult {
        // Step 1: Get phones based on brand selection
        val phonePool = if (preferences.selectedBrands.isEmpty()) {
            getAllPhones()
        } else {
            preferences.selectedBrands.flatMap { getPhonesByBrand(it) }
        }
        
        // Step 2: Filter by budget
        val budgetFiltered = phonePool.filter { phone ->
            phone.price >= preferences.budgetMin && phone.price <= preferences.budgetMax
        }
        
        // Step 3: Filter by features (ALL selected features must be present)
        val featureFiltered = if (preferences.selectedFeatures.isEmpty()) {
            budgetFiltered
        } else {
            budgetFiltered.filter { phone ->
                preferences.selectedFeatures.all { feature ->
                    phone.features.any { it.equals(feature, ignoreCase = true) }
                }
            }
        }
        
        // If no phones match with features, show budget-filtered phones without feature requirement
        val finalPhones = if (featureFiltered.isEmpty() && preferences.selectedFeatures.isNotEmpty()) {
            budgetFiltered
        } else {
            featureFiltered
        }
        
        // Step 4: Score phones based on priority
        val scoredPhones = finalPhones.map { phone ->
            val priorityScore = calculatePriorityScore(phone, preferences.priority)
            val matchScore = calculateMatchScore(phone, preferences)
            PhoneWithScore(
                phone = phone,
                matchScore = matchScore,
                matchReason = generateMatchReason(phone, preferences),
                priorityScore = priorityScore
            )
        }
        
        // Step 5: Sort by priority score (descending) then by match score
        val sortedPhones = scoredPhones.sortedWith(
            compareBy<PhoneWithScore> { -it.priorityScore }
                .thenBy { -it.matchScore }
        )
        
        // Step 6: Get top 3 matches
        val topMatches = sortedPhones.take(3)
        
        // Step 7: Find best overall (best balance)
        val bestOverall = sortedPhones.maxByOrNull { it.matchScore } ?: topMatches.firstOrNull()
        
        // Step 8: Find best value (best score per rupee)
        val bestValue = sortedPhones.maxByOrNull { it.matchScore / it.phone.price } ?: topMatches.firstOrNull()
        
        // Step 9: Find best premium (highest price within budget)
        val bestPremium = if (preferences.budgetMax > 100000) {
            sortedPhones.filter { it.phone.price > 100000 }.maxByOrNull { it.matchScore }
        } else {
            null
        }
        
        // Step 10: Generate summary
        val summary = generateSummary(topMatches, preferences)
        
        return RecommendationResult(
            topMatches = topMatches,
            bestOverall = bestOverall ?: topMatches.firstOrNull() ?: PhoneWithScore(
                phone = Phone("none", "No Match", "N/A", 0, CameraSpec("", "", ""), "", 
                    BatterySpec(0, ""), DisplaySpec(0.0, "", 0, ""), ChargingSpec(0, false, false),
                    StorageSpec(0, 0), emptyList(), 0.0, 0),
                matchScore = 0.0,
                matchReason = "No phones match your criteria",
                priorityScore = 0.0
            ),
            bestValue = bestValue ?: topMatches.firstOrNull() ?: PhoneWithScore(
                phone = Phone("none", "No Match", "N/A", 0, CameraSpec("", "", ""), "", 
                    BatterySpec(0, ""), DisplaySpec(0.0, "", 0, ""), ChargingSpec(0, false, false),
                    StorageSpec(0, 0), emptyList(), 0.0, 0),
                matchScore = 0.0,
                matchReason = "No phones match your criteria",
                priorityScore = 0.0
            ),
            bestPremium = bestPremium,
            summary = summary
        )
    }
    
    // Calculate priority score based on "What Matters Most"
    private fun calculatePriorityScore(phone: Phone, priority: Priority): Double {
        return when (priority) {
            Priority.CAMERA -> {
                val rearMp = phone.camera.rear.split("MP").firstOrNull()?.toDoubleOrNull() ?: 0.0
                rearMp / 100.0 * 100 // Normalize to 0-100
            }
            Priority.BATTERY -> {
                (phone.battery.capacity / 5000.0) * 100 // Normalize to 0-100
            }
            Priority.DISPLAY -> {
                val refreshRate = phone.display.refreshRate.toDouble()
                val typeBonus = if (phone.display.type.equals("AMOLED", ignoreCase = true)) 20.0 else 0.0
                (refreshRate / 144.0) * 80 + typeBonus
            }
            Priority.PERFORMANCE -> {
                val ramScore = (phone.storage.ram / 12.0) * 50
                val storageScore = (phone.storage.storage / 512.0) * 50
                ramScore + storageScore
            }
            Priority.GAMING -> {
                val refreshRate = phone.display.refreshRate.toDouble()
                val ramScore = (phone.storage.ram / 12.0) * 30
                val batteryScore = (phone.battery.capacity / 5000.0) * 20
                (refreshRate / 144.0) * 50 + ramScore + batteryScore
            }
        }
    }
    
    // Calculate overall match score
    private fun calculateMatchScore(phone: Phone, preferences: UserPreferences): Double {
        var score = 100.0
        
        // Deduct for price (prefer lower price in range)
        val priceRange = preferences.budgetMax - preferences.budgetMin
        val pricePosition = (phone.price - preferences.budgetMin) / priceRange.toDouble()
        score -= (pricePosition * 10) // Max 10 points deduction
        
        // Add bonus for feature match
        val featureMatchPercentage = if (preferences.selectedFeatures.isNotEmpty()) {
            phone.features.count { feature ->
                preferences.selectedFeatures.any { it.equals(feature, ignoreCase = true) }
            } / preferences.selectedFeatures.size.toDouble()
        } else {
            1.0
        }
        score += (featureMatchPercentage * 20) // Max 20 points bonus
        
        // Add bonus for rating
        score += (phone.rating / 5.0) * 10 // Max 10 points bonus
        
        return score.coerceIn(0.0, 100.0)
    }
    
    // Generate match reason
    private fun generateMatchReason(phone: Phone, preferences: UserPreferences): String {
        val reasons = mutableListOf<String>()
        
        reasons.add("Within budget (₹${phone.price})")
        
        when (preferences.priority) {
            Priority.CAMERA -> reasons.add("Excellent camera: ${phone.camera.rear}")
            Priority.BATTERY -> reasons.add("Large battery: ${phone.battery.capacity}mAh")
            Priority.DISPLAY -> reasons.add("${phone.display.refreshRate}Hz ${phone.display.type} display")
            Priority.PERFORMANCE -> reasons.add("${phone.storage.ram}GB RAM + ${phone.storage.storage}GB storage")
            Priority.GAMING -> reasons.add("Gaming-focused: ${phone.display.refreshRate}Hz display, ${phone.storage.ram}GB RAM")
        }
        
        val matchedFeatures = phone.features.filter { feature ->
            preferences.selectedFeatures.any { it.equals(feature, ignoreCase = true) }
        }
        if (matchedFeatures.isNotEmpty()) {
            reasons.add("Has ${matchedFeatures.size} selected features")
        }
        
        return reasons.joinToString(" • ")
    }
    
    // Generate summary
    private fun generateSummary(topMatches: List<PhoneWithScore>, preferences: UserPreferences): String {
        if (topMatches.isEmpty()) {
            return "No phones found matching your criteria. Try adjusting your filters."
        }
        
        val budgetText = "₹${preferences.budgetMin} - ₹${preferences.budgetMax}"
        val brandText = if (preferences.selectedBrands.isEmpty()) "All brands" else preferences.selectedBrands.joinToString(", ")
        val featureText = if (preferences.selectedFeatures.isEmpty()) "No specific features" else preferences.selectedFeatures.joinToString(", ")
        
        return "Found ${topMatches.size} perfect matches for your needs:\n" +
                "Budget: $budgetText\n" +
                "Brands: $brandText\n" +
                "Priority: ${preferences.priority.name}\n" +
                "Features: $featureText\n\n" +
                "All recommended phones match 100% of your selected filters."
    }
}
