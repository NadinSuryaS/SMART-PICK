package com.smartpick.mysp.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Enums for offer types
enum class OfferStatus {
    LIVE, UPCOMING, EXPIRED
}

enum class OfferType {
    INSTANT_DISCOUNT, CASHBACK, EMI_OFFER
}

enum class CardType {
    CREDIT, DEBIT, EMI, ALL
}

enum class BankName {
    HDFC, ICICI, SBI, AXIS, KOTAK, AU, YES
}

// Data class for brand-wise bank offers
data class BrandBankOffer(
    val id: String,
    val phoneModel: String,
    val marketPrice: Int,
    val bankName: BankName,
    val offerStatus: OfferStatus,
    val offerType: OfferType,
    val discountAmount: Int,
    val minimumTransactionValue: Int,
    val cardType: CardType,
    val noCostEMI: Boolean,
    val validUntil: LocalDate,
    val bankWebsite: String
)

// Bank website URLs
object BankUrls {
    const val HDFC = "https://www.hdfcbank.com/offers"
    const val ICICI = "https://www.icicibank.com/offers"
    const val SBI = "https://www.sbi.co.in/web/personal/personal-banking/cards/credit-cards/sbi-credit-card-offers"
    const val AXIS = "https://www.axisbank.com/retail/cards/credit-cards/offers"
    const val KOTAK = "https://www.kotak.com/en/personal/offers"
    const val AU = "https://www.aubank.in/offers"
    const val YES = "https://www.yesbank.in/offers"
}

// Get bank website URL
fun BankName.getWebsiteUrl(): String = when (this) {
    BankName.HDFC -> BankUrls.HDFC
    BankName.ICICI -> BankUrls.ICICI
    BankName.SBI -> BankUrls.SBI
    BankName.AXIS -> BankUrls.AXIS
    BankName.KOTAK -> BankUrls.KOTAK
    BankName.AU -> BankUrls.AU
    BankName.YES -> BankUrls.YES
}

// Get bank display name
fun BankName.getDisplayName(): String = when (this) {
    BankName.HDFC -> "HDFC Bank"
    BankName.ICICI -> "ICICI Bank"
    BankName.SBI -> "SBI Card"
    BankName.AXIS -> "Axis Bank"
    BankName.KOTAK -> "Kotak Bank"
    BankName.AU -> "AU Bank"
    BankName.YES -> "Yes Bank"
}

// Get offer type display name
fun OfferType.getDisplayName(): String = when (this) {
    OfferType.INSTANT_DISCOUNT -> "instant discount"
    OfferType.CASHBACK -> "cashback"
    OfferType.EMI_OFFER -> "EMI offer"
}

// Get card type display name
fun CardType.getDisplayName(): String = when (this) {
    CardType.CREDIT -> "Credit Card"
    CardType.DEBIT -> "Debit Card"
    CardType.EMI -> "EMI"
    CardType.ALL -> "All Cards"
}

// Get status display name
fun OfferStatus.getDisplayName(): String = when (this) {
    OfferStatus.LIVE -> "LIVE OFFER"
    OfferStatus.UPCOMING -> "UPCOMING"
    OfferStatus.EXPIRED -> "EXPIRED OFFER"
}

// Get status badge text
fun OfferStatus.getBadgeText(): String = when (this) {
    OfferStatus.LIVE -> "LIVE NOW"
    OfferStatus.UPCOMING -> "UPCOMING"
    OfferStatus.EXPIRED -> "EXPIRED"
}

// Brand Offers Engine
object BrandOffersEngine {
    
    private val today = LocalDate.now()
    
    fun getOffersForBrand(brand: String): List<BrandBankOffer> {
        return when (brand.lowercase()) {
            "apple" -> getAppleOffers()
            "samsung" -> getSamsungOffers()
            "oneplus" -> getOnePlusOffers()
            "xiaomi" -> getXiaomiOffers()
            "vivo" -> getVivoOffers()
            "realme" -> getRealmeOffers()
            "oppo" -> getOppoOffers()
            "iqoo" -> getIQOOOffers()
            "motorola" -> getMotorolaOffers()
            else -> emptyList()
        }
    }
    
    private fun getAppleOffers(): List<BrandBankOffer> {
        return listOf(
            // iPhone 15 Pro Max - LIVE OFFERS
            BrandBankOffer(
                id = "apple_iphone15promax_hdfc_1",
                phoneModel = "iPhone 15 Pro Max",
                marketPrice = 139999,
                bankName = BankName.HDFC,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 5000,
                minimumTransactionValue = 100000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(30),
                bankWebsite = BankName.HDFC.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "apple_iphone15promax_icici_1",
                phoneModel = "iPhone 15 Pro Max",
                marketPrice = 139999,
                bankName = BankName.ICICI,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 4000,
                minimumTransactionValue = 100000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(25),
                bankWebsite = BankName.ICICI.getWebsiteUrl()
            ),
            // iPhone 15 - UPCOMING OFFER
            BrandBankOffer(
                id = "apple_iphone15_sbi_1",
                phoneModel = "iPhone 15",
                marketPrice = 79999,
                bankName = BankName.SBI,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 3000,
                minimumTransactionValue = 50000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(45),
                bankWebsite = BankName.SBI.getWebsiteUrl()
            ),
            // iPhone 14 - EXPIRED OFFER
            BrandBankOffer(
                id = "apple_iphone14_axis_1",
                phoneModel = "iPhone 14",
                marketPrice = 69999,
                bankName = BankName.AXIS,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 2500,
                minimumTransactionValue = 50000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(5),
                bankWebsite = BankName.AXIS.getWebsiteUrl()
            )
        )
    }
    
    private fun getSamsungOffers(): List<BrandBankOffer> {
        return listOf(
            // Galaxy S24 Ultra - LIVE OFFERS
            BrandBankOffer(
                id = "samsung_s24ultra_hdfc_1",
                phoneModel = "Galaxy S24 Ultra",
                marketPrice = 129999,
                bankName = BankName.HDFC,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 4500,
                minimumTransactionValue = 100000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(28),
                bankWebsite = BankName.HDFC.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "samsung_s24ultra_kotak_1",
                phoneModel = "Galaxy S24 Ultra",
                marketPrice = 129999,
                bankName = BankName.KOTAK,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 3500,
                minimumTransactionValue = 100000,
                cardType = CardType.ALL,
                noCostEMI = true,
                validUntil = today.plusDays(20),
                bankWebsite = BankName.KOTAK.getWebsiteUrl()
            ),
            // Galaxy S24 - UPCOMING OFFER
            BrandBankOffer(
                id = "samsung_s24_au_1",
                phoneModel = "Galaxy S24",
                marketPrice = 79999,
                bankName = BankName.AU,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 2500,
                minimumTransactionValue = 50000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(40),
                bankWebsite = BankName.AU.getWebsiteUrl()
            ),
            // Galaxy A54 - EXPIRED OFFER
            BrandBankOffer(
                id = "samsung_a54_yes_1",
                phoneModel = "Galaxy A54",
                marketPrice = 34999,
                bankName = BankName.YES,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 1500,
                minimumTransactionValue = 20000,
                cardType = CardType.DEBIT,
                noCostEMI = false,
                validUntil = today.minusDays(3),
                bankWebsite = BankName.YES.getWebsiteUrl()
            )
        )
    }
    
    private fun getOnePlusOffers(): List<BrandBankOffer> {
        return listOf(
            // OnePlus 12 - LIVE OFFERS
            BrandBankOffer(
                id = "oneplus_12_icici_1",
                phoneModel = "OnePlus 12",
                marketPrice = 64999,
                bankName = BankName.ICICI,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 3000,
                minimumTransactionValue = 50000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(32),
                bankWebsite = BankName.ICICI.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "oneplus_12_sbi_1",
                phoneModel = "OnePlus 12",
                marketPrice = 64999,
                bankName = BankName.SBI,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 2500,
                minimumTransactionValue = 50000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(22),
                bankWebsite = BankName.SBI.getWebsiteUrl()
            ),
            // OnePlus 11 - UPCOMING OFFER
            BrandBankOffer(
                id = "oneplus_11_axis_1",
                phoneModel = "OnePlus 11",
                marketPrice = 49999,
                bankName = BankName.AXIS,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 2000,
                minimumTransactionValue = 40000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(35),
                bankWebsite = BankName.AXIS.getWebsiteUrl()
            ),
            // OnePlus Nord - EXPIRED OFFER
            BrandBankOffer(
                id = "oneplus_nord_hdfc_1",
                phoneModel = "OnePlus Nord",
                marketPrice = 29999,
                bankName = BankName.HDFC,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 1000,
                minimumTransactionValue = 20000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(7),
                bankWebsite = BankName.HDFC.getWebsiteUrl()
            )
        )
    }
    
    private fun getXiaomiOffers(): List<BrandBankOffer> {
        return listOf(
            // Xiaomi 14 Ultra - LIVE OFFERS
            BrandBankOffer(
                id = "xiaomi_14ultra_kotak_1",
                phoneModel = "Xiaomi 14 Ultra",
                marketPrice = 69999,
                bankName = BankName.KOTAK,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 3500,
                minimumTransactionValue = 50000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(29),
                bankWebsite = BankName.KOTAK.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "xiaomi_14ultra_au_1",
                phoneModel = "Xiaomi 14 Ultra",
                marketPrice = 69999,
                bankName = BankName.AU,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 2500,
                minimumTransactionValue = 50000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(18),
                bankWebsite = BankName.AU.getWebsiteUrl()
            ),
            // Xiaomi 13 - UPCOMING OFFER
            BrandBankOffer(
                id = "xiaomi_13_yes_1",
                phoneModel = "Xiaomi 13",
                marketPrice = 54999,
                bankName = BankName.YES,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 2000,
                minimumTransactionValue = 40000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(38),
                bankWebsite = BankName.YES.getWebsiteUrl()
            ),
            // Xiaomi Redmi Note 13 - EXPIRED OFFER
            BrandBankOffer(
                id = "xiaomi_redminote13_icici_1",
                phoneModel = "Xiaomi Redmi Note 13",
                marketPrice = 19999,
                bankName = BankName.ICICI,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 1000,
                minimumTransactionValue = 15000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(4),
                bankWebsite = BankName.ICICI.getWebsiteUrl()
            )
        )
    }
    
    private fun getVivoOffers(): List<BrandBankOffer> {
        return listOf(
            // Vivo X100 - LIVE OFFERS
            BrandBankOffer(
                id = "vivo_x100_hdfc_1",
                phoneModel = "Vivo X100",
                marketPrice = 84999,
                bankName = BankName.HDFC,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 4000,
                minimumTransactionValue = 70000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(26),
                bankWebsite = BankName.HDFC.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "vivo_x100_sbi_1",
                phoneModel = "Vivo X100",
                marketPrice = 84999,
                bankName = BankName.SBI,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 3000,
                minimumTransactionValue = 70000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(21),
                bankWebsite = BankName.SBI.getWebsiteUrl()
            ),
            // Vivo V29 - UPCOMING OFFER
            BrandBankOffer(
                id = "vivo_v29_axis_1",
                phoneModel = "Vivo V29",
                marketPrice = 54999,
                bankName = BankName.AXIS,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 2500,
                minimumTransactionValue = 40000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(42),
                bankWebsite = BankName.AXIS.getWebsiteUrl()
            ),
            // Vivo Y27 - EXPIRED OFFER
            BrandBankOffer(
                id = "vivo_y27_kotak_1",
                phoneModel = "Vivo Y27",
                marketPrice = 16999,
                bankName = BankName.KOTAK,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 800,
                minimumTransactionValue = 12000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(6),
                bankWebsite = BankName.KOTAK.getWebsiteUrl()
            )
        )
    }
    
    private fun getRealmeOffers(): List<BrandBankOffer> {
        return listOf(
            // Realme GT 6 - LIVE OFFERS
            BrandBankOffer(
                id = "realme_gt6_au_1",
                phoneModel = "Realme GT 6",
                marketPrice = 59999,
                bankName = BankName.AU,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 3000,
                minimumTransactionValue = 50000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(27),
                bankWebsite = BankName.AU.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "realme_gt6_yes_1",
                phoneModel = "Realme GT 6",
                marketPrice = 59999,
                bankName = BankName.YES,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 2500,
                minimumTransactionValue = 50000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(19),
                bankWebsite = BankName.YES.getWebsiteUrl()
            ),
            // Realme 12 - UPCOMING OFFER
            BrandBankOffer(
                id = "realme_12_hdfc_1",
                phoneModel = "Realme 12",
                marketPrice = 34999,
                bankName = BankName.HDFC,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 1500,
                minimumTransactionValue = 25000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(37),
                bankWebsite = BankName.HDFC.getWebsiteUrl()
            ),
            // Realme Narzo 70 - EXPIRED OFFER
            BrandBankOffer(
                id = "realme_narzo70_icici_1",
                phoneModel = "Realme Narzo 70",
                marketPrice = 14999,
                bankName = BankName.ICICI,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 700,
                minimumTransactionValue = 10000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(8),
                bankWebsite = BankName.ICICI.getWebsiteUrl()
            )
        )
    }
    
    private fun getOppoOffers(): List<BrandBankOffer> {
        return listOf(
            // Oppo Find X7 - LIVE OFFERS
            BrandBankOffer(
                id = "oppo_findx7_sbi_1",
                phoneModel = "Oppo Find X7",
                marketPrice = 79999,
                bankName = BankName.SBI,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 3500,
                minimumTransactionValue = 60000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(24),
                bankWebsite = BankName.SBI.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "oppo_findx7_axis_1",
                phoneModel = "Oppo Find X7",
                marketPrice = 79999,
                bankName = BankName.AXIS,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 2500,
                minimumTransactionValue = 60000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(17),
                bankWebsite = BankName.AXIS.getWebsiteUrl()
            ),
            // Oppo A78 - UPCOMING OFFER
            BrandBankOffer(
                id = "oppo_a78_kotak_1",
                phoneModel = "Oppo A78",
                marketPrice = 24999,
                bankName = BankName.KOTAK,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 1200,
                minimumTransactionValue = 18000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(41),
                bankWebsite = BankName.KOTAK.getWebsiteUrl()
            ),
            // Oppo A17k - EXPIRED OFFER
            BrandBankOffer(
                id = "oppo_a17k_au_1",
                phoneModel = "Oppo A17k",
                marketPrice = 12999,
                bankName = BankName.AU,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 600,
                minimumTransactionValue = 10000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(2),
                bankWebsite = BankName.AU.getWebsiteUrl()
            )
        )
    }
    
    private fun getIQOOOffers(): List<BrandBankOffer> {
        return listOf(
            // iQOO 12 - LIVE OFFERS
            BrandBankOffer(
                id = "iqoo_12_yes_1",
                phoneModel = "iQOO 12",
                marketPrice = 54999,
                bankName = BankName.YES,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 2500,
                minimumTransactionValue = 40000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(30),
                bankWebsite = BankName.YES.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "iqoo_12_hdfc_1",
                phoneModel = "iQOO 12",
                marketPrice = 54999,
                bankName = BankName.HDFC,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 2000,
                minimumTransactionValue = 40000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(23),
                bankWebsite = BankName.HDFC.getWebsiteUrl()
            ),
            // iQOO 11 - UPCOMING OFFER
            BrandBankOffer(
                id = "iqoo_11_icici_1",
                phoneModel = "iQOO 11",
                marketPrice = 44999,
                bankName = BankName.ICICI,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 1800,
                minimumTransactionValue = 35000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(39),
                bankWebsite = BankName.ICICI.getWebsiteUrl()
            ),
            // iQOO Z9 - EXPIRED OFFER
            BrandBankOffer(
                id = "iqoo_z9_sbi_1",
                phoneModel = "iQOO Z9",
                marketPrice = 24999,
                bankName = BankName.SBI,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 1000,
                minimumTransactionValue = 18000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(9),
                bankWebsite = BankName.SBI.getWebsiteUrl()
            )
        )
    }
    
    private fun getMotorolaOffers(): List<BrandBankOffer> {
        return listOf(
            // Motorola Edge 50 Pro - LIVE OFFERS
            BrandBankOffer(
                id = "motorola_edge50pro_axis_1",
                phoneModel = "Motorola Edge 50 Pro",
                marketPrice = 59999,
                bankName = BankName.AXIS,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 3000,
                minimumTransactionValue = 50000,
                cardType = CardType.CREDIT,
                noCostEMI = true,
                validUntil = today.plusDays(28),
                bankWebsite = BankName.AXIS.getWebsiteUrl()
            ),
            BrandBankOffer(
                id = "motorola_edge50pro_kotak_1",
                phoneModel = "Motorola Edge 50 Pro",
                marketPrice = 59999,
                bankName = BankName.KOTAK,
                offerStatus = OfferStatus.LIVE,
                offerType = OfferType.CASHBACK,
                discountAmount = 2500,
                minimumTransactionValue = 50000,
                cardType = CardType.DEBIT,
                noCostEMI = true,
                validUntil = today.plusDays(20),
                bankWebsite = BankName.KOTAK.getWebsiteUrl()
            ),
            // Motorola G54 - UPCOMING OFFER
            BrandBankOffer(
                id = "motorola_g54_au_1",
                phoneModel = "Motorola G54",
                marketPrice = 19999,
                bankName = BankName.AU,
                offerStatus = OfferStatus.UPCOMING,
                offerType = OfferType.EMI_OFFER,
                discountAmount = 1000,
                minimumTransactionValue = 15000,
                cardType = CardType.EMI,
                noCostEMI = true,
                validUntil = today.plusDays(36),
                bankWebsite = BankName.AU.getWebsiteUrl()
            ),
            // Motorola G13 - EXPIRED OFFER
            BrandBankOffer(
                id = "motorola_g13_yes_1",
                phoneModel = "Motorola G13",
                marketPrice = 11999,
                bankName = BankName.YES,
                offerStatus = OfferStatus.EXPIRED,
                offerType = OfferType.INSTANT_DISCOUNT,
                discountAmount = 500,
                minimumTransactionValue = 10000,
                cardType = CardType.CREDIT,
                noCostEMI = false,
                validUntil = today.minusDays(10),
                bankWebsite = BankName.YES.getWebsiteUrl()
            )
        )
    }
}
