package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object NokiaPhones {
    val phones = listOf(
        Phone(id = "nokia_g50", name = "Nokia G50", brand = "Nokia", price = 19999,
            camera = CameraSpec("48MP f/1.79 + 5MP + 2MP", "13MP f/2.2", "4K@30fps"),
            processor = "Snapdragon 480 5G", battery = BatterySpec(5050, "Li-Ion"),
            display = DisplaySpec(6.82, "1600x720", 90, "IPS"),
            charging = ChargingSpec(20, false, false), storage = StorageSpec(4, 128),
            features = listOf("5G", "Big Battery", "Fast Charging", "High Refresh Rate"), rating = 3.9, releaseYear = 2022),
        
        Phone(id = "nokia_x30", name = "Nokia X30", brand = "Nokia", price = 32999,
            camera = CameraSpec("50MP f/1.8 + 13MP", "25MP f/2.0", "4K@60fps"),
            processor = "Snapdragon 695", battery = BatterySpec(4200, "Li-Ion"),
            display = DisplaySpec(6.43, "2400x1080", 90, "OLED"),
            charging = ChargingSpec(33, false, false), storage = StorageSpec(8, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.1, releaseYear = 2023)
    )
}
