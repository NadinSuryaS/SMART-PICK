package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object HonorPhones {
    val phones = listOf(
        Phone(id = "honor_70", name = "Honor 70", brand = "Honor", price = 34999,
            camera = CameraSpec("50MP f/1.9 + 8MP", "32MP f/2.4", "4K@60fps"),
            processor = "Snapdragon 778G+ Gen 1", battery = BatterySpec(4800, "Li-Po"),
            display = DisplaySpec(6.67, "2412x1084", 120, "AMOLED"),
            charging = ChargingSpec(66, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.1, releaseYear = 2022)
    )
}
