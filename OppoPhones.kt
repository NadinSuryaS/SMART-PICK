package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object OppoPhones {
    val phones = listOf(
        Phone(id = "oppo_a16", name = "Oppo A16", brand = "Oppo", price = 11999,
            camera = CameraSpec("13MP f/2.2 + 2MP", "5MP f/2.4", "1080p@30fps"),
            processor = "MediaTek Helio G35", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.52, "1600x720", 60, "IPS"),
            charging = ChargingSpec(10, false, false), storage = StorageSpec(3, 32),
            features = listOf("Big Battery"), rating = 3.7, releaseYear = 2021),
        
        Phone(id = "oppo_a77", name = "Oppo A77", brand = "Oppo", price = 21999,
            camera = CameraSpec("50MP f/1.8 + 2MP", "16MP f/2.4", "1080p@30fps"),
            processor = "MediaTek Helio G99", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.43, "2400x1080", 90, "IPS"),
            charging = ChargingSpec(33, false, false), storage = StorageSpec(6, 128),
            features = listOf("5G", "Fast Charging", "High Refresh Rate"), rating = 4.0, releaseYear = 2022),
        
        Phone(id = "oppo_find_x5", name = "Oppo Find X5", brand = "Oppo", price = 59999,
            camera = CameraSpec("50MP f/1.7 + 50MP + 13MP", "32MP f/2.4", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(4800, "Li-Po"),
            display = DisplaySpec(6.55, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(80, true, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.3, releaseYear = 2022),
        
        Phone(id = "oppo_find_x5_pro", name = "Oppo Find X5 Pro", brand = "Oppo", price = 79999,
            camera = CameraSpec("50MP f/1.7 + 50MP + 13MP", "32MP f/2.4", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.7, "2796x1224", 120, "AMOLED"),
            charging = ChargingSpec(80, true, false), storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Large Storage"),
            rating = 4.5, releaseYear = 2022)
    )
}
