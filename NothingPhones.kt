package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object NothingPhones {
    val phones = listOf(
        Phone(id = "nothing_phone1", name = "Nothing Phone (1)", brand = "Nothing", price = 32999,
            camera = CameraSpec("50MP f/1.88 + 50MP f/2.2", "16MP f/2.5", "4K@60fps"),
            processor = "Snapdragon 778+ Gen 1", battery = BatterySpec(4500, "Li-Ion"),
            display = DisplaySpec(6.55, "2400x1080", 120, "OLED"),
            charging = ChargingSpec(33, false, false), storage = StorageSpec(8, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate"), rating = 4.0, releaseYear = 2022),
        
        Phone(id = "nothing_phone2", name = "Nothing Phone (2)", brand = "Nothing", price = 49999,
            camera = CameraSpec("50MP f/1.88 + 50MP f/2.2", "32MP f/2.4", "4K@60fps"),
            processor = "Snapdragon 8+ Gen 1", battery = BatterySpec(4700, "Li-Ion"),
            display = DisplaySpec(6.7, "2412x1084", 120, "OLED"),
            charging = ChargingSpec(45, false, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.3, releaseYear = 2023)
    )
}
