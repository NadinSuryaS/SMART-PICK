package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object IQOOPhones {
    val phones = listOf(
        Phone(id = "iqoo_z7", name = "iQOO Z7", brand = "iQOO", price = 24999,
            camera = CameraSpec("50MP f/1.8 + 2MP", "16MP f/2.45", "1080p@30fps"),
            processor = "Snapdragon 782G", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.64, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(44, false, false), storage = StorageSpec(6, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate"), rating = 4.1, releaseYear = 2023),
        
        Phone(id = "iqoo_11", name = "iQOO 11", brand = "iQOO", price = 54999,
            camera = CameraSpec("50MP f/1.75 + 50MP + 12MP", "16MP f/2.45", "8K@30fps"),
            processor = "Snapdragon 8 Gen 2", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.78, "2400x1080", 144, "AMOLED"),
            charging = ChargingSpec(120, false, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.4, releaseYear = 2023)
    )
}
