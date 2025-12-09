package com.smartpick.mysp.data

// Phone data model
data class Phone(
    val id: String,
    val name: String,
    val brand: String,
    val price: Int, // in rupees
    val camera: CameraSpec,
    val processor: String,
    val battery: BatterySpec,
    val display: DisplaySpec,
    val charging: ChargingSpec,
    val storage: StorageSpec,
    val features: List<String>, // 5G, AMOLED, Fast Charging, etc.
    val rating: Double,
    val releaseYear: Int,
    val imageUrl: String = "https://via.placeholder.com/300x400?text=${name.replace(" ", "+")}", // Placeholder image
    val priceHistory: List<PricePoint> = emptyList() // Price history for this phone
)

data class CameraSpec(
    val rear: String, // e.g., "108MP f/1.8"
    val front: String, // e.g., "32MP f/2.4"
    val videoRecording: String // e.g., "8K@30fps"
)

data class BatterySpec(
    val capacity: Int, // in mAh
    val type: String // Li-Po, Li-Ion
)

data class DisplaySpec(
    val size: Double, // in inches
    val resolution: String, // e.g., "2400x1080"
    val refreshRate: Int, // in Hz
    val type: String // AMOLED, IPS, LCD
)

data class ChargingSpec(
    val wattage: Int, // in watts
    val wireless: Boolean,
    val reverseCharging: Boolean
)

data class StorageSpec(
    val ram: Int, // in GB
    val storage: Int // in GB
)

// Priority enum for "What Matters Most"
enum class Priority {
    CAMERA,
    BATTERY,
    DISPLAY,
    PERFORMANCE,
    GAMING
}

// Budget ranges
enum class BudgetRange(val min: Int, val max: Int, val label: String) {
    UNDER_10K(2000, 10000, "Under ₹10K"),
    TEN_TO_20K(10001, 20000, "₹10K - ₹20K"),
    TWENTY_TO_30K(20001, 30000, "₹20K - ₹30K"),
    THIRTY_TO_50K(30001, 50000, "₹30K - ₹50K"),
    FIFTY_TO_75K(50001, 75000, "₹50K - ₹75K"),
    SEVENTY_FIVE_TO_100K(75001, 100000, "₹75K - ₹1L"),
    HUNDRED_TO_150K(100001, 150000, "₹1L - ₹1.5L"),
    ABOVE_150K(150001, 200000, "₹1.5L - ₹2L"),
    ABOVE_200K(200001, Int.MAX_VALUE, "Above ₹2L")
}

// User preferences for filtering
data class UserPreferences(
    val budgetMin: Int,
    val budgetMax: Int,
    val selectedBrands: List<String>, // Empty = All brands
    val priority: Priority,
    val selectedFeatures: List<String> // Empty = No feature filter
)

// Recommendation result
data class RecommendationResult(
    val topMatches: List<PhoneWithScore>,
    val bestOverall: PhoneWithScore,
    val bestValue: PhoneWithScore,
    val bestPremium: PhoneWithScore?,
    val summary: String
)

data class PhoneWithScore(
    val phone: Phone,
    val matchScore: Double, // 0-100
    val matchReason: String,
    val priorityScore: Double // Based on "What Matters Most"
)

// Favorites management
data class FavoritePhone(
    val phoneId: String,
    val phone: Phone,
    val addedAt: Long = System.currentTimeMillis()
)

// Price tracking
data class PriceTrack(
    val id: String = System.currentTimeMillis().toString(),
    val phoneId: String,
    val phone: Phone,
    val targetPrice: Int,
    val currentPrice: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val priceHistory: List<PricePoint> = emptyList()
)

data class PricePoint(
    val price: Int,
    val timestamp: Long = System.currentTimeMillis()
)

// Compare phones
data class ComparePhones(
    val phones: List<Phone> = emptyList() // Max 3 phones
)

// Bank Offer
data class BankOffer(
    val bankName: String,
    val discount: String,  // e.g., "10% OFF"
    val offerDetails: String,  // e.g., "on Credit Card"
    val bankUrl: String,  // URL to bank page
    val bankLogo: String = ""  // Bank logo URL
)

// Sample bank offers
fun getSampleBankOffers(): List<BankOffer> = listOf(
    BankOffer(
        bankName = "HDFC Bank",
        discount = "10% OFF",
        offerDetails = "on Credit Card",
        bankUrl = "https://www.hdfcbank.com"
    ),
    BankOffer(
        bankName = "ICICI Bank",
        discount = "8% OFF",
        offerDetails = "on Debit Card",
        bankUrl = "https://www.icicibank.com"
    ),
    BankOffer(
        bankName = "Axis Bank",
        discount = "12% OFF",
        offerDetails = "on Credit Card",
        bankUrl = "https://www.axisbank.com"
    ),
    BankOffer(
        bankName = "SBI Bank",
        discount = "5% OFF",
        offerDetails = "on All Cards",
        bankUrl = "https://www.sbi.co.in"
    )
)
