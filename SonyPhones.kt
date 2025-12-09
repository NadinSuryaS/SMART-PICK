package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object SonyPhones {
    val phones = listOf(
        Phone(id = "sony_xperia_5iv", name = "Sony Xperia 5 IV", brand = "Sony", price = 79999,
            camera = CameraSpec("12MP f/1.6 + 12MP + 12MP", "12MP f/2.0", "4K@120fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(5000, "Li-Ion"),
            display = DisplaySpec(6.1, "2520x1080", 120, "OLED"),
            charging = ChargingSpec(30, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.2, releaseYear = 2022)
    )
}
