package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object MeizuPhones {
    val phones = listOf(
        Phone(id = "meizu_18s", name = "Meizu 18s", brand = "Meizu", price = 39999,
            camera = CameraSpec("50MP f/1.6 + 12MP", "20MP f/2.2", "4K@60fps"),
            processor = "Snapdragon 888", battery = BatterySpec(4000, "Li-Po"),
            display = DisplaySpec(6.2, "2400x1080", 90, "AMOLED"),
            charging = ChargingSpec(55, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.0, releaseYear = 2021)
    )
}
