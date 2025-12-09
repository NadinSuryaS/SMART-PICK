package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object AsusPhones {
    val phones = listOf(
        Phone(id = "asus_rog_phone_6", name = "Asus ROG Phone 6", brand = "Asus", price = 79999,
            camera = CameraSpec("50MP f/1.9 + 13MP", "32MP f/2.45", "8K@30fps"),
            processor = "Snapdragon 8+ Gen 1", battery = BatterySpec(6000, "Li-Po"),
            display = DisplaySpec(6.78, "2448x1080", 165, "AMOLED"),
            charging = ChargingSpec(65, false, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Big Battery"),
            rating = 4.4, releaseYear = 2022)
    )
}
