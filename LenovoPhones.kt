package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object LenovoPhones {
    val phones = listOf(
        Phone(id = "lenovo_legion_y90", name = "Lenovo Legion Y90", brand = "Lenovo", price = 69999,
            camera = CameraSpec("50MP f/1.6 + 13MP", "20MP f/2.2", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(5600, "Li-Po"),
            display = DisplaySpec(6.92, "2460x1080", 144, "AMOLED"),
            charging = ChargingSpec(80, false, false), storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Big Battery", "Large Storage"),
            rating = 4.3, releaseYear = 2022)
    )
}
