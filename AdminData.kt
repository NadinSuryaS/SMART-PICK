package com.smartpick.mysp.data

// Admin analytics data
data class AdminAnalytics(
    val totalRegisteredUsers: Int = 15420,
    val todaysSearches: Int = 3847,
    val totalRecommendationsGenerated: Int = 28956,
    val mostSearchedBrands: List<BrandSearchData> = emptyList()
)

data class BrandSearchData(
    val brandName: String,
    val searchCount: Int,
    val percentage: Float
)

// Get sample admin analytics
fun getSampleAdminAnalytics(): AdminAnalytics {
    val brands = listOf(
        BrandSearchData("Samsung", 4520, 22.5f),
        BrandSearchData("Apple", 3890, 19.3f),
        BrandSearchData("Xiaomi", 3456, 17.2f),
        BrandSearchData("OnePlus", 2890, 14.4f),
        BrandSearchData("Realme", 2345, 11.7f),
        BrandSearchData("Others", 2899, 14.9f)
    )
    
    return AdminAnalytics(
        totalRegisteredUsers = 15420,
        todaysSearches = 3847,
        totalRecommendationsGenerated = 28956,
        mostSearchedBrands = brands
    )
}
