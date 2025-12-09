package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object NubiaPhones {
    val phones = listOf(
        Phone(id = "nubia_z40", name = "Nubia Z40", brand = "Nubia", price = 54999,
            camera = CameraSpec("50MP f/1.6 + 50MP + 8MP", "16MP f/2.4", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.67, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(80, false, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.2, releaseYear = 2022)
    )
}
