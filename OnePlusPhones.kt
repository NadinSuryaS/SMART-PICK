package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object OnePlusPhones {
    val phones = listOf(
        Phone(id = "oneplus_nord_ce", name = "OnePlus Nord CE", brand = "OnePlus", price = 24999,
            camera = CameraSpec("64MP f/1.6 + 8MP + 2MP", "16MP f/2.4", "4K@60fps"),
            processor = "Snapdragon 750G", battery = BatterySpec(4500, "Li-Po"),
            display = DisplaySpec(6.43, "2400x1080", 90, "AMOLED"),
            charging = ChargingSpec(65, false, false), storage = StorageSpec(8, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate"), rating = 4.1, releaseYear = 2021),
        
        Phone(id = "oneplus_10", name = "OnePlus 10", brand = "OnePlus", price = 49999,
            camera = CameraSpec("48MP f/1.7 + 50MP + 8MP", "32MP f/2.4", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.7, "2412x1080", 120, "AMOLED"),
            charging = ChargingSpec(150, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.3, releaseYear = 2022),
        
        Phone(id = "oneplus_10_pro", name = "OnePlus 10 Pro", brand = "OnePlus", price = 66999,
            camera = CameraSpec("48MP f/1.7 + 50MP + 8MP + 2MP", "32MP f/2.4", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.7, "2796x1224", 120, "AMOLED"),
            charging = ChargingSpec(150, true, false), storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Large Storage"),
            rating = 4.5, releaseYear = 2022)
    )
}
