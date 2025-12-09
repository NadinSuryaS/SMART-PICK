package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object RealmPhones {
    val phones = listOf(
        Phone(id = "realme_c35", name = "Realme C35", brand = "Realme", price = 9999,
            camera = CameraSpec("50MP f/1.8 + 2MP", "8MP f/2.1", "1080p@30fps"),
            processor = "Unisoc T612", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.5, "1600x720", 60, "IPS"),
            charging = ChargingSpec(10, false, false), storage = StorageSpec(3, 32),
            features = listOf("Big Battery"), rating = 3.8, releaseYear = 2022),
        
        Phone(id = "realme_9", name = "Realme 9", brand = "Realme", price = 19999,
            camera = CameraSpec("50MP f/1.8 + 8MP + 2MP", "16MP f/2.5", "1080p@30fps"),
            processor = "Snapdragon 680", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.4, "2400x1080", 90, "IPS"),
            charging = ChargingSpec(33, false, false), storage = StorageSpec(4, 64),
            features = listOf("5G", "Fast Charging", "High Refresh Rate"), rating = 4.0, releaseYear = 2022),
        
        Phone(id = "realme_gt_neo", name = "Realme GT Neo 3", brand = "Realme", price = 42999,
            camera = CameraSpec("50MP f/1.8 + 8MP + 2MP", "16MP f/2.5", "4K@60fps"),
            processor = "MediaTek Dimensity 8100", battery = BatterySpec(4500, "Li-Po"),
            display = DisplaySpec(6.62, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(150, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.2, releaseYear = 2022),
        
        Phone(id = "realme_gt3", name = "Realme GT 3", brand = "Realme", price = 54999,
            camera = CameraSpec("50MP f/1.8 + 8MP + 2MP", "16MP f/2.5", "8K@24fps"),
            processor = "Snapdragon 8+ Gen 1", battery = BatterySpec(4600, "Li-Po"),
            display = DisplaySpec(6.7, "2412x1080", 120, "AMOLED"),
            charging = ChargingSpec(240, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.4, releaseYear = 2023)
    )
}
