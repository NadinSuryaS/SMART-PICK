package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object BlackSharkPhones {
    val phones = listOf(
        Phone(id = "blackshark_5", name = "Black Shark 5", brand = "Black Shark", price = 49999,
            camera = CameraSpec("50MP f/1.8 + 13MP", "20MP f/2.2", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(4650, "Li-Po"),
            display = DisplaySpec(6.67, "2400x1080", 144, "AMOLED"),
            charging = ChargingSpec(120, false, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.2, releaseYear = 2022)
    )
}
