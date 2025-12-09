package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object XiaomiPhones {
    val phones = listOf(
        Phone(id = "xiaomi_redmi_9a", name = "Redmi 9A", brand = "Xiaomi", price = 7999,
            camera = CameraSpec("13MP f/2.2", "5MP f/2.2", "1080p@30fps"),
            processor = "MediaTek Helio G25", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.53, "1600x720", 60, "IPS"),
            charging = ChargingSpec(10, false, false), storage = StorageSpec(3, 32),
            features = listOf("Big Battery"), rating = 3.7, releaseYear = 2020),
        
        Phone(id = "xiaomi_redmi_note_12", name = "Redmi Note 12", brand = "Xiaomi", price = 15999,
            camera = CameraSpec("50MP f/1.8 + 8MP", "13MP f/2.4", "4K@30fps"),
            processor = "MediaTek Helio G99", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.67, "2400x1080", 120, "IPS"),
            charging = ChargingSpec(33, false, false), storage = StorageSpec(6, 128),
            features = listOf("5G", "Fast Charging", "High Refresh Rate"), rating = 4.1, releaseYear = 2023),
        
        Phone(id = "xiaomi_12", name = "Xiaomi 12", brand = "Xiaomi", price = 45999,
            camera = CameraSpec("50MP f/1.9 + 13MP + 10MP", "32MP f/2.45", "8K@24fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(4300, "Li-Po"),
            display = DisplaySpec(6.28, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(120, true, true), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.3, releaseYear = 2022),
        
        Phone(id = "xiaomi_13_ultra", name = "Xiaomi 13 Ultra", brand = "Xiaomi", price = 89999,
            camera = CameraSpec("50MP f/1.6 + 50MP + 50MP + 50MP", "32MP f/2.45", "8K@30fps"),
            processor = "Snapdragon 8 Gen 2", battery = BatterySpec(5300, "Li-Po"),
            display = DisplaySpec(6.73, "3200x1440", 120, "AMOLED"),
            charging = ChargingSpec(120, true, true), storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Large Storage"),
            rating = 4.6, releaseYear = 2023)
    )
}
