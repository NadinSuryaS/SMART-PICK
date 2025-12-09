package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object RedMagicPhones {
    val phones = listOf(
        Phone(id = "redmagic_7s", name = "RedMagic 7s", brand = "RedMagic", price = 59999,
            camera = CameraSpec("50MP f/1.6 + 13MP", "20MP f/2.2", "8K@30fps"),
            processor = "Snapdragon 8+ Gen 1", battery = BatterySpec(4500, "Li-Po"),
            display = DisplaySpec(6.8, "2400x1080", 165, "AMOLED"),
            charging = ChargingSpec(135, false, false), storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Large Storage"),
            rating = 4.3, releaseYear = 2022)
    )
}
