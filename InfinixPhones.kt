package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object InfinixPhones {
    val phones = listOf(
        Phone(id = "infinix_note_12", name = "Infinix Note 12", brand = "Infinix", price = 11999,
            camera = CameraSpec("50MP f/1.6 + 2MP", "16MP f/2.0", "1080p@30fps"),
            processor = "MediaTek Helio G88", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.7, "1600x720", 90, "IPS"),
            charging = ChargingSpec(33, false, false), storage = StorageSpec(4, 64),
            features = listOf("5G", "Fast Charging", "High Refresh Rate"), rating = 3.8, releaseYear = 2022),
        
        Phone(id = "infinix_zero_ultra", name = "Infinix Zero Ultra", brand = "Infinix", price = 29999,
            camera = CameraSpec("108MP f/1.6 + 50MP + 13MP", "32MP f/2.0", "8K@24fps"),
            processor = "MediaTek Dimensity 8100", battery = BatterySpec(4500, "Li-Po"),
            display = DisplaySpec(6.7, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(160, true, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.1, releaseYear = 2022)
    )
}
