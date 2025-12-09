package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object PocoPhones {
    val phones = listOf(
        Phone(id = "poco_x4", name = "Poco X4", brand = "Poco", price = 16999,
            camera = CameraSpec("64MP f/1.79 + 8MP + 2MP + 2MP", "20MP f/2.25", "4K@30fps"),
            processor = "Snapdragon 695", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.67, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(67, false, false), storage = StorageSpec(6, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate"), rating = 4.0, releaseYear = 2022),
        
        Phone(id = "poco_f4", name = "Poco F4", brand = "Poco", price = 27999,
            camera = CameraSpec("64MP f/1.6 + 8MP + 2MP + 2MP", "20MP f/2.45", "4K@60fps"),
            processor = "Snapdragon 870", battery = BatterySpec(4500, "Li-Po"),
            display = DisplaySpec(6.67, "2400x1080", 144, "AMOLED"),
            charging = ChargingSpec(67, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.2, releaseYear = 2022),
        
        Phone(id = "poco_f5", name = "Poco F5", brand = "Poco", price = 34999,
            camera = CameraSpec("108MP f/1.8 + 8MP + 2MP", "16MP f/2.45", "8K@30fps"),
            processor = "Snapdragon 7 Gen 1", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.67, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(67, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.3, releaseYear = 2023)
    )
}
