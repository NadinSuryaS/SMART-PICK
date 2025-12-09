package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object MotorolaPhones {
    val phones = listOf(
        Phone(id = "moto_g32", name = "Moto G32", brand = "Motorola", price = 13999,
            camera = CameraSpec("50MP f/1.8 + 8MP + 2MP", "16MP f/2.4", "1080p@30fps"),
            processor = "MediaTek Helio G37", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.5, "1600x720", 90, "IPS"),
            charging = ChargingSpec(25, false, false), storage = StorageSpec(4, 64),
            features = listOf("5G", "Fast Charging", "High Refresh Rate"), rating = 3.9, releaseYear = 2022),
        
        Phone(id = "moto_edge_40", name = "Moto Edge 40", brand = "Motorola", price = 39999,
            camera = CameraSpec("50MP f/1.4 + 50MP", "32MP f/2.4", "8K@30fps"),
            processor = "Snapdragon 7 Gen 1", battery = BatterySpec(4500, "Li-Po"),
            display = DisplaySpec(6.55, "2400x1080", 144, "OLED"),
            charging = ChargingSpec(68, false, false), storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.2, releaseYear = 2023)
    )
}
